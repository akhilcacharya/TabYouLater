package xyz.iou.app.iou.Services;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import xyz.iou.app.iou.Models.Responses.DebtCollectionResponse;
import xyz.iou.app.iou.Models.Responses.LoginResponse;

/**
 * Created by akhilacharya on 10/10/15.
 */
public interface IOUService {
    @FormUrlEncoded
    @POST("/login")
    void login(@Field("username") String username, @Field("password") String password, Callback<LoginResponse> cb);

    @GET("/debts/payable")
    void getPayable(Callback<DebtCollectionResponse> cb);

    @GET("/debts/owed")
    void getOwed(Callback<DebtCollectionResponse> cb);
}
