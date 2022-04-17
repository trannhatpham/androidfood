package com.example.fooddrink.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.fooddrink.Model.CartInfo;
import com.example.fooddrink.Model.Category;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.service.ApiServer;
import com.example.fooddrink.service.RetrofitMain;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends BaseViewModel {
    ApiServer server = RetrofitMain.createService(ApiServer.class);

    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    public final MutableLiveData<List<CartInfo>> DataListSuccess = new MutableLiveData<>();

    public void getListCart(String id) {
        server.getListCart(id).enqueue(new Callback<List<CartInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<CartInfo>> call, @NonNull Response<List<CartInfo>> response) {
                if (response.isSuccessful()) {
                    DataListSuccess.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CartInfo>> call, @NonNull Throwable t) {
                DataListSuccess.setValue(null);
            }
        });
    }

    public final MutableLiveData<CartInfo> DataInsertSuccess = new MutableLiveData<>();
    public final MutableLiveData<CartInfo> DataUpdateSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> getDataDelete() {
        return DataDelete;
    }

    public final MutableLiveData<Boolean> DataDelete = new MutableLiveData<>();

    public void insertCart(CartInfo info) {
        server.insertCart(info).enqueue(new Callback<CartInfo>() {
            @Override
            public void onResponse(@NonNull Call<CartInfo> call, @NonNull Response<CartInfo> response) {
                if (response.isSuccessful()) {
                    DataInsertSuccess.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CartInfo> call, @NonNull Throwable t) {
                DataInsertSuccess.setValue(null);
            }
        });
    }

    public void updateCart(CartInfo info) {
        server.updateCart(info).enqueue(new Callback<CartInfo>() {
            @Override
            public void onResponse(@NonNull Call<CartInfo> call, @NonNull Response<CartInfo> response) {
                if (response.isSuccessful()) {
                    DataUpdateSuccess.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CartInfo> call, @NonNull Throwable t) {
                DataUpdateSuccess.setValue(null);
            }
        });
    }

    public void deleteCart(String id) {
        server.deleteCart(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    DataDelete.setValue(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                DataDelete.setValue(false);
            }
        });
    }

}
