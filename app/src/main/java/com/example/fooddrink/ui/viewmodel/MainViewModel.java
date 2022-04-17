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

public class MainViewModel extends BaseViewModel{

    ApiServer server = RetrofitMain.createService(ApiServer.class);
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Food>> getItemDataFood() {
        return itemDataFood;
    }

    public final MutableLiveData<List<Food>> itemDataFood = new MutableLiveData<>();

    public MutableLiveData<List<Category>> getItemDataCategory() {
        return itemDataCategory;
    }

    public final MutableLiveData<List<Category>> itemDataCategory = new MutableLiveData<>();
    public void getListFood(){
        server.getListFood("ff").enqueue(new Callback<List<Food>>() {
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

    public void getListCategory(){
        server.getListCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()){
                    itemDataCategory.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                itemDataCategory.setValue(null);
                System.out.println(t.getMessage());
            }
        });
    }
}
