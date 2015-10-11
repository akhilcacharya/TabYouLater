package xyz.iou.app.iou.Models.Responses;

import com.google.gson.annotations.SerializedName;

import xyz.iou.app.iou.Models.Person;

/**
 * Created by akhilacharya on 10/10/15.
 */
public class LoginResponse{
    public String message;

    @SerializedName("customer_id")
    public String customerId;

    @SerializedName("name")
    public String name;
}
