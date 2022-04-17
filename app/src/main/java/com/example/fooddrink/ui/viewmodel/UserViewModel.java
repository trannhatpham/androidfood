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

public class UserViewModel extends BaseViewModel{

    ApiServer server = RetrofitMain.createService(ApiServer.class);

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public final MutableLiveData<User> DataUser= new MutableLiveData<>();

    public void updateUser(User user) {
        server.updateUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    DataUser.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                DataUser.setValue(null);
            }
        });
    }
}
