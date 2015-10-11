package xyz.iou.app.iou.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akhilacharya on 10/10/15.
 */
public class Debt {
    @SerializedName("debt_id")
    public String debtId;

    @SerializedName("bill_id")
    public String billId;
    @SerializedName("creditor_acct_id")
    public String creditor;
    @SerializedName("debtor_acct_id")
    public String debtor;
    public long amountOwed;
}
