package com.example.fooddrink.ui.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fooddrink.Model.CartInfo;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.R;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivityCartBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.booking.BookingActivity;
import com.example.fooddrink.ui.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends BaseTestActivity<ActivityCartBinding> {
    CartAdapter adapter;
    CartViewModel viewModel;

    @Override
    public ActivityCartBinding getViewBinding() {
        return ActivityCartBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        adapter = new CartAdapter(this);
        binding.rylCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rylCart.setAdapter(adapter);

        adapter.setOnClick((view, position) -> {
            CartInfo info = adapter.getItem(position);
            Integer count = info.getCount();
            switch (view.getId()) {
                case R.id.imageAdd:
                    count = count + 1;
                    info.setCount(count);
                    adapter.remoteItem(position);
                    adapter.add(info, position);
                    updateCart(adapter.getItem(position));
                    showPrice();
                    break;
                case R.id.imageMinus:
                    count = count - 1;
                    info.setCount(count);
                    adapter.remoteItem(position);
                    if (count != 0) {
                        adapter.add(info, position);
                        updateCart(adapter.getItem(position));
                    } else {
                        viewModel.deleteCart(info.getCartID().toString());
                    }
                    showPrice();
                    break;
                default:
                    break;
            }
        });
        binding.layoutHeader.imageDrawer.setOnClickListener(view -> finish());

        binding.textOk.setOnClickListener(view -> {
            if (adapter.getListAllData().size() == 0) return;

            PublicData.listCart =  adapter.getListAllData();
            // này gọi là lưu vào bộ nhớ đệm á dùng 1 class tạm lưu dữ liệu khi ứng dụng chạy, khi thoát app thì nó bị clear
            Intent intent = new Intent(this, BookingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        });
    }

    @Override
    protected void initData() {
        viewModel.getListCart(PublicData.currentUser.getUserID().toString());
    }

    private void showPrice() {
        Integer totalPrice = 0;
        if (adapter.getListAllData() != null) {
            for (CartInfo cartInfo : adapter.getListAllData()) {
                totalPrice += cartInfo.getPrice() * cartInfo.getCount();
            }
        }
        binding.textTotalPrice.setText(totalPrice.toString());
    }

    public void updateCart(CartInfo info){
        viewModel.updateCart(info);
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.DataListSuccess.observe(this, result -> {
            if (result != null) {
                adapter.clearData();
                adapter.addData(result);
                showPrice();
            }
        });

        viewModel.DataDelete.observe(this, result -> {
            if (result){
                showToast("Xóa thành công");
            }
        });

        viewModel.DataUpdateSuccess.observe(this, info -> {
            if (info!=null){
                showToast("Cập nhật thành công");
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

}
