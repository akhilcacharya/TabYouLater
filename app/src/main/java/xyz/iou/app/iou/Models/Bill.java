package xyz.iou.app.iou.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akhilacharya on 10/11/15.
 */
public class Bill {
    @SerializedName("bill_id")
    public String billId;

    @SerializedName("customer_id")
    public String customerId;

    public long amount;
}
