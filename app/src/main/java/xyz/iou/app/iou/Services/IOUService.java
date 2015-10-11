package xyz.iou.app.iou.Services;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import xyz.iou.app.iou.Models.Responses.DebtCollectionResponse;
import xyz.iou.app.iou.Models.Responses.LoginResponse;

/**
 * Created by akhilacharya on 10/10/15.
 */
public interface IOUService {
    @POST("/login")
    void login(Callback<LoginResponse> cb);
    @GET("/debts/payable")
    void getPayable(Callback<DebtCollectionResponse> cb);
    @GET("/debts/owed")
    void getOwed(Callback<DebtCollectionResponse> cb);
}
