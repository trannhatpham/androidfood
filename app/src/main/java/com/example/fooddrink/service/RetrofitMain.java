package com.example.fooddrink.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMain {
    static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }).build();

    static Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    private static final Retrofit.Builder builder
            = new Retrofit.Builder() //http://192.168.1.7:8080/user?phone=0981751114&pass=123456 //
            .baseUrl("http://192.168.1.7:8080/") // gắn link local host vô // chỉ áp dụng cho wifi ô đang xài nha nếu wifi khác phải ping lại rồi gắn lại
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static final Retrofit retrofit = builder.build();

    public static MultipartBody getMultipartBody(Map<String, String> keySet) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
