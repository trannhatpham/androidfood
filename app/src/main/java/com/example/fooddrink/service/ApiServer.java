package com.example.fooddrink.service;

import com.example.fooddrink.Model.Booking;
import com.example.fooddrink.Model.CartInfo;
import com.example.fooddrink.Model.Category;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.Model.User;
import com.example.fooddrink.ui.cart.CartAdapter;
import com.google.android.gms.common.api.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServer {
    @GET("user")
    Call<User> checkLogin(@Query("phone") String phone, @Query("pass") String pass);

    @GET("food/{id}")
    Call<List<Food>> getListFood(@Path("id") String id);

    @GET("category")
    Call<List<Category>> getListCategory();

    @POST("insertCart")
    Call<CartInfo> insertCart(@Body CartInfo info);

    @POST("updateCart")
    Call<CartInfo> updateCart(@Body CartInfo info);

    @GET("delete/{id}")
    Call<Object> deleteCart(@Path("id") String id);

    @GET("cart/{id}")
    Call<List<CartInfo>> getListCart(@Path("id") String id);

    @GET("cancelOrder/{id}")
    Call<Object> cancelOrder(@Path("id") String id);
    // object as null có thể k trả về giá trị gì á

    @POST("insertBooking")
    Call<Booking> insertBooking(@Body Booking info);

    @POST("updateUser")
    Call<User> updateUser(@Body User info);
}
