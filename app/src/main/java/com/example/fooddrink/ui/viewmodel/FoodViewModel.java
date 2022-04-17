package com.example.fooddrink.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.fooddrink.Model.Category;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.service.ApiServer;
import com.example.fooddrink.service.RetrofitMain;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodViewModel extends BaseViewModel{

    public FoodViewModel(@NonNull Application application) {
        super(application);
    }
    ApiServer server = RetrofitMain.createService(ApiServer.class);

    public MutableLiveData<List<Food>> getItemDataFood() {
        return itemDataFood;
    }

    public final MutableLiveData<List<Food>> itemDataFood = new MutableLiveData<>();

    public void getListFood(String categoryID){
        server.getListFood(categoryID).enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()){
                    itemDataFood.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                itemDataFood.setValue(null);
                System.out.println(t.getMessage());
            }
        });
    }
}
