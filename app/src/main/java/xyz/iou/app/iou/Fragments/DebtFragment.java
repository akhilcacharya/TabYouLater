package xyz.iou.app.iou.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import xyz.iou.app.iou.Adapter.DebtAdapter;
import xyz.iou.app.iou.Models.Responses.DebtCollectionResponse;
import xyz.iou.app.iou.R;
import xyz.iou.app.iou.Services.IOUService;


public class DebtFragment extends Fragment {
    public static final int PAYABLE_TYPE = 0;
    public static final int OWED_TYPE = 1;


    private DebtAdapter debtAdapter = null;
    private IOUService service = null;
    private SwipeRefreshLayout refreshLayout = null;

    private int FragmentType = -1;

    public static DebtFragment newInstance(int type) {
        DebtFragment fragment = new DebtFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    public DebtFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            FragmentType = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_debt, container, false);
        debtAdapter = new DebtAdapter(getActivity());
        final ListView debtList = (ListView) root.findViewById(R.id.fragment_debt_list);
        debtList.setAdapter(debtAdapter);
        this.service = buildService();
        this.refreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.fragment_debt_refresh);

        this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateList();
            }
        });

        updateList();

        return root;
    }

    private void updateList(){
        Callback<DebtCollectionResponse> cb = new Callback<DebtCollectionResponse>() {
            @Override
            public void onResponse(Response<DebtCollectionResponse> response, Retrofit retrofit) {
                debtAdapter.setData(response.body().debt);
                debtAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                refreshLayout.setRefreshing(false);
            }
        };

        if(FragmentType == PAYABLE_TYPE){
            service.getPayable(cb);
            return;
        }else if(FragmentType == OWED_TYPE){
            service.getOwed(cb);
            return;
        }
    }

    private IOUService buildService(){
        String endpoint = getString(R.string.endpoint);
        Retrofit adapter = new Retrofit.Builder().baseUrl(endpoint).build();
        IOUService service = adapter.create(IOUService.class);
        return service;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
