package com.example.fooddrink.ui.booking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fooddrink.Model.Booking;
import com.example.fooddrink.Model.User;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.databinding.ActivityBookingBinding;
import com.example.fooddrink.ui.address.ChangeAddressActivity;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.viewmodel.BookingViewModel;
import com.example.fooddrink.utils.AppUtils;

public class BookingActivity extends BaseTestActivity<ActivityBookingBinding> {
    BookingAdapter adapter;
    public static Integer CHANGEADDRESS = 1;
    User info;
    String numberPhone = "";
    Booking booking;
    BookingViewModel viewModel;

    Boolean isCheckLocation = false;

    @Override
    public ActivityBookingBinding getViewBinding() {
        return ActivityBookingBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        info = AppPreference.getUserMain();
        adapter = new BookingAdapter(this);
        numberPhone = AppPreference.getUserPhone();

        binding.rclMain.setLayoutManager(new LinearLayoutManager(this));
        binding.rclMain.setAdapter(adapter);
        if ( info.getAddress()== null){
            binding.textAddress.setText(info.getUsername() + " \n" + numberPhone);
        } else {
            binding.textAddress.setText(info.getUsername() + " \n" + numberPhone + "\n" +
                    info.getAddress());
        }

        binding.textAddress.setOnClickListener(v -> {
            if (!AppUtils.checkLocation(this)){
                buildAlertMessageNoGps();
                isCheckLocation = true;
                return;
            }
            startChangeAddress();
        });
        binding.buttonOk.setOnClickListener(view -> {
            viewModel.insertBooking(getBooking());
        });
    }

    public void startChangeAddress(){
        Intent intent = new Intent(BookingActivity.this, ChangeAddressActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, CHANGEADDRESS);
    }

    @Override
    protected void initData() {
    }

    private Booking getBooking() {
        booking = new Booking();
        booking.listCart = "";
        booking.stageId = 1;
        booking.address = info.getAddress();
        booking.userId = info.getUserID().toString();
        return booking;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        // activity result
        if (resultCode == RESULT_OK) {
            if (requestCode == CHANGEADDRESS) {
                initView();
                initData();
            }
        }
    }

    private void buildAlertMessageNoGps() {
        alertDialog("VỊ TRÍ", "Để tiếp tục, vui lòng bật chức năng xác định vị trí.",
                "OK", null, (dialog, which) ->
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)));

        // request location from your phone
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCheckLocation){
            startChangeAddress();
        }
    }
}