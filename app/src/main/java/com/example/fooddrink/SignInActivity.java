package com.example.fooddrink;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fooddrink.Model.User;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivitySignInBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.viewmodel.LoginViewModel;
import com.example.fooddrink.utils.AppUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends BaseTestActivity<ActivitySignInBinding> {
    LoginViewModel viewModel;

    @Override
    public ActivitySignInBinding getViewBinding() {
        return ActivitySignInBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.btnSingIn.setOnClickListener(view -> {
            if (!AppUtils.validateNumberPhone(binding.edtPhone.getText().toString())) {
                showMessage("Bạn chưa nhập đúng sdt");
                return;
            }
            if (!AppUtils.isValidatePassword(binding.edtPassword.getText().toString())) {
                showMessage("Mật khẩu phải có ít nhất 6 kí tự");
                return;
            }
            showProgressDialog(true);
            viewModel.CheckLogin(binding.edtPhone.getText().toString(), binding.edtPassword.getText().toString());
        });
    }

    void validate() {
        if (AppUtils.validateNumberPhone(binding.edtPhone.getText().toString())) {
            showMessage("Bạn chưa nhập đúng sdt");
            return;
        }
        if (AppUtils.isValidatePassword(binding.edtPassword.getText().toString())) {
            showMessage("Mật khẩu phải có ít nhất 6 kí tự");
            return;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.getItemUser().observe(this, user -> {
            showProgressDialog(false);
            if (user != null) {
                showToast("Login success");
                AppPreference.setLogin(true);
                AppPreference.setUserPhone(binding.edtPhone.getText().toString());
                AppPreference.setUserPass(binding.edtPassword.getText().toString());
                // save to sharedPreferences
                Intent homeIntent = new Intent(SignInActivity.this, HomeActivity.class);
                PublicData.currentUser = user;
                AppPreference.saveUserMain(user);
                // save user current in to cache for uses another class
                startActivity(homeIntent);
                finish();
            } else
                Toast.makeText(SignInActivity.this, R.string.title_user_not_exist, Toast.LENGTH_LONG).show();

        });
    }

    @Override
    public void onClick(View view) {

    }
}