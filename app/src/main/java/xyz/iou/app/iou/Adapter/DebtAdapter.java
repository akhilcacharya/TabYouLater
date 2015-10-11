package xyz.iou.app.iou.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import xyz.iou.app.iou.Models.Debt;
import xyz.iou.app.iou.R;

/**
 * Created by akhilacharya on 10/10/15.
 */
public class DebtAdapter extends BaseAdapter{

    private Context ctx;
    private ArrayList<Debt> debts;

    public DebtAdapter(Context ctx){
        this.ctx = ctx;
        this.debts = new ArrayList<>();
    }

    public void setData(ArrayList<Debt> _debts){
        this.debts.clear();

        for(Debt d: _debts){
            this.debts.add(d);
        }
    }

    @Override
    public int getCount() {
        return debts.size();
    }

    @Override
    public Debt getItem(int i) {
        return debts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Debt debt = this.getItem(i);
        View layout =  LayoutInflater.from(this.ctx).inflate(R.layout.list_debt, null, false);

        TextView name = (TextView) layout.findViewById(R.id.list_debt_name);
        name.setText(debt.getDebtor().getName());
        TextView amt = (TextView) layout.findViewById(R.id.list_debt_amt);
        amt.setText(getFormattedCurrency(debt.getAmountOwed()));

        return layout;
    }


    private String getFormattedCurrency(long cents){
        return NumberFormat.getCurrencyInstance().format(cents/100);
    }
}
