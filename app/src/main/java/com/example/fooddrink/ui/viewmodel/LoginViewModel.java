package com.example.fooddrink.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.fooddrink.Model.User;
import com.example.fooddrink.service.ApiServer;
import com.example.fooddrink.service.RetrofitMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {

    ApiServer server = RetrofitMain.createService(ApiServer.class);

    public MutableLiveData<User> getItemUser() {
        return itemUser;
    }

    public final MutableLiveData<User> itemUser = new MutableLiveData<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void CheckLogin(String phone, String pass) {
        server.checkLogin(phone, pass).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    itemUser.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                itemUser.setValue(null);
                System.out.println(t.getMessage());
            }
        });
    }
}
