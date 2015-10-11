package xyz.iou.app.iou.Services;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import xyz.iou.app.iou.Models.Bill;
import xyz.iou.app.iou.Models.Responses.DebtCollectionResponse;
import xyz.iou.app.iou.Models.Responses.LoginResponse;

/**
 * Created by akhilacharya on 10/10/15.
 */
public interface IOUService {

    @FormUrlEncoded
    @POST("/login")
    void login(@Field("email") String username, @Field("password") String password, Callback<LoginResponse> cb);

    @FormUrlEncoded
    @POST("/getpayable")
    void getPayable(@Field("customer_id") String customerId, Callback<DebtCollectionResponse> cb);

    @FormUrlEncoded
    @POST("/getowed")
    void getOwed(@Field("customer_id") String customerId, Callback<DebtCollectionResponse> cb);


    @POST("/createbill")
    void createBill(@Body Bill bill, Callback cb);

    @FormUrlEncoded
    @POST("/acceptbill")
    void acceptBill(@Field("bill_id") String billId, @Field("debtor_id") String debtorId, @Field("creditor_id") String creditorId);

    @FormUrlEncoded
    @POST("/collectbill")
    void collectBill(@Field("bill_id") String billid, Callback cb);

    @FormUrlEncoded
    @POST("/collectdebt")
    void collectDebt(@Field("debt_id") String debtId, Callback cb);
}
