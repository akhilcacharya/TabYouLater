package xyz.iou.app.iou.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.iou.app.iou.Adapter.DebtAdapter;
import xyz.iou.app.iou.Models.Debt;
import xyz.iou.app.iou.Models.Responses.BillResponse;
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

        debtList.setEmptyView(root.findViewById(android.R.id.empty));

        if(FragmentType == OWED_TYPE){
            debtList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Debt d = (Debt) adapterView.getItemAtPosition(i);
                    service.collectDebt(d.debtId, new Callback<BillResponse>(){
                        @Override
                        public void success(BillResponse billResponse, Response response) {
                            Toast.makeText(getActivity(), "Collected debt!", 1).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getActivity(), "Failed to collect debt, check network", 1).show();
                        }
                    });

                }
            });
        }


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
            public void success(DebtCollectionResponse debtCollectionResponse, Response response) {
                debtAdapter.setData(debtCollectionResponse.debt);
                debtAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                refreshLayout.setRefreshing(false);
            }
        };

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String id = sharedPrefs.getString("id", "");

        if(FragmentType == PAYABLE_TYPE){
            service.getPayable(id, cb);
            return;
        }else if(FragmentType == OWED_TYPE){
            service.getOwed(id, cb);
            return;
        }
    }

    private IOUService buildService(){
        String endpoint = getString(R.string.endpoint);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(endpoint).build();
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
