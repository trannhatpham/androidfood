package com.example.fooddrink.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Map;

import okhttp3.MultipartBody;

public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public static MultipartBody getMultipartBody(Map<String, String> keySet) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

}
