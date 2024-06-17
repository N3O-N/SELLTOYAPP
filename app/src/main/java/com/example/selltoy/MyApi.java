package com.example.selltoy;

import com.example.selltoy.model.Moddel_user;
import com.example.selltoy.model.Model_Order;
import com.example.selltoy.model.Model_Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApi {
    @GET("get_Product.php")
    Call<ArrayList<Model_Product>> model_ProductArray();

    @GET("get_orderDetail.php")
    Call<ArrayList<Model_Order>> model_getOrderDetailArray();

    @GET("get_user.php")
    Call<ArrayList<Moddel_user>> model_UserArray();

    @GET("delete.php")
    Call<Void> deleteData(
            @Query("order_ID") String orderID,
            @Query("Seq") String seq
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<Void> updateQuantity(
            @Field("Seq") int Seq,
            @Field("order_ID") int order_ID,
            @Field("Quantity") int newQuantity
    );

    @FormUrlEncoded
    @POST("post_data.php")
    Call<Void> sendToDatabase(
            @Field("customerId") int customerId,
            @Field("productId") int productId,
            @Field("quantity") int quantity
    );

}
