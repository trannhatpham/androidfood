package com.example.fooddrink.ui.splash;

import static com.example.fooddrink.utils.AppConstant.DelayData;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;

import com.example.fooddrink.HomeActivity;
import com.example.fooddrink.MainActivity;
import com.example.fooddrink.SignInActivity;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivitySplashBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.viewmodel.LoginViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends BaseTestActivity<ActivitySplashBinding> {
    FirebaseDatabase database;
    DatabaseReference table_user;
    String numberPhone = "";
    String pass = "";
    LoginViewModel viewModel;

    @Override
    public ActivitySplashBinding getViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        database = PublicData.database;
        table_user = database.getReference("User");

        new Handler().postDelayed(() -> {
            if (AppPreference.isLogin()) {
                numberPhone = AppPreference.getUserPhone();
                pass = AppPreference.getUserPass();
                viewModel.CheckLogin(numberPhone,pass);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DelayData);
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.getItemUser().observe(this, user -> {
            Intent homeIntent;
            if (user!=null){
                homeIntent = new Intent(this, HomeActivity.class);
                PublicData.currentUser = user;
                AppPreference.saveUserMain(user);
            } else {
                homeIntent = new Intent(this, SignInActivity.class);
            }
            startActivity(homeIntent);
            finish();
        });
    }

}