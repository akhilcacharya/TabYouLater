package xyz.iou.app.iou.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akhilacharya on 10/11/15.
 */
public class Bill {
    @SerializedName("bill_id")
    String billId;

    @SerializedName("customer_id")
    String customerId;


    long amount;
}
