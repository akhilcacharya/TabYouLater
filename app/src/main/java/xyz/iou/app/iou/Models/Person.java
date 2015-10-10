package xyz.iou.app.iou.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akhilacharya on 10/10/15.
 */
public class Person {
    private String name;
    @SerializedName("_id")
    private String customerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
