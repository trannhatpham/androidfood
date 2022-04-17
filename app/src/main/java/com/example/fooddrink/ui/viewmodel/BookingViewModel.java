package com.example.fooddrink.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.fooddrink.Model.Booking;
import com.example.fooddrink.Model.CartInfo;
import com.example.fooddrink.service.ApiServer;
import com.example.fooddrink.service.RetrofitMain;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingViewModel extends BaseViewModel {

    ApiServer server = RetrofitMain.createService(ApiServer.class);

    public BookingViewModel(@NonNull Application application) {
        super(application);
    }


    public final MutableLiveData<Boolean> DataCancelBooking = new MutableLiveData<>();

    public final MutableLiveData<Booking> DataInsertBooking = new MutableLiveData<>();


    public void cancelOrder(String id) {
        server.cancelOrder(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    DataCancelBooking.setValue(true);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                DataCancelBooking.setValue(false);
            }
        });
    }

    public void insertBooking(Booking booking){
        server.insertBooking(booking).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(@NonNull Call<Booking> call, @NonNull Response<Booking> response) {
                if (response.isSuccessful()){
                    DataInsertBooking.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Booking> call, @NonNull Throwable t) {
                DataInsertBooking.setValue(null);
            }
        });
    }
}
