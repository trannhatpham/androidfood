package com.example.fooddrink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.CartInfo;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivityFoodDetailBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.cart.CartActivity;
import com.example.fooddrink.ui.viewmodel.CartViewModel;
import com.example.fooddrink.utils.AppUtils;
import com.example.fooddrink.utils.ViewImageActivity;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends BaseTestActivity<ActivityFoodDetailBinding> {
    Food food;
    String key = "";
    List<Food> listData = new ArrayList<>();
    Integer count = 1;
    CartViewModel viewModel;
    Boolean isBuyNow = false;

    @Override
    public ActivityFoodDetailBinding getViewBinding() {
        return ActivityFoodDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding.imageAdd.setOnClickListener(view12 -> {
            count = count + 1;
            binding.textCountBuy.setText(count.toString());
            binding.textPrice.setText(String.valueOf(food.getPrice() * count));
            binding.textDiscount.setText(AppUtils.formatNumber("NO").format(food.getDiscount() * count));

        });
        binding.imageMinus.setOnClickListener(view13 -> {
            if (count != 1) {
                count = count - 1;
            }
            binding.textCountBuy.setText(count.toString());
            binding.textPrice.setText(String.valueOf(food.getPrice() * count));
//            binding.textDiscount.setText(AppUtils.formatNumber("NO").format(food.getDiscount() * count));

        });
        binding.btnAddCart.setOnClickListener(view -> {
            isBuyNow = false;
            viewModel.insertCart(getItem());
        });
        binding.btnBuyNow.setOnClickListener(view -> {
            isBuyNow = true;
            viewModel.insertCart(getItem());
        });

        binding.layoutHeader.imageCart.setOnClickListener(view -> {
            Intent intent = new Intent(this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        binding.layoutHeader.imageDrawer.setOnClickListener(view -> finish());

        binding.imageMain.setOnClickListener(view -> {
            if (food == null) return;
            Intent intent = new Intent(this, ViewImageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("PATHIMAGE", food.getImage());
            startActivity(intent);
        });
    }

    private CartInfo getItem() {
        CartInfo info = new CartInfo();
        info.setCount(count);
        info.setCategoryID(food.getCategoryID());
        info.setUserID(PublicData.currentUser.getUserID());
        info.setFoodID(food.getFoodID());
        info.setPrice(Integer.parseInt(binding.textPrice.getText().toString()));
        return info;
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        food = (Food) bundle.getSerializable("ITEM");
        if (food != null) {
            Glide.with(this)
                    .load(food.getImage())
                    .error(R.drawable.img_no_image)
                    .into(binding.imageMain);
            binding.textDescription.setText(food.getDescription());
            binding.textDiscount.setText(AppUtils.formatNumber("NO").format(food.getDiscount()));
            binding.textPrice.setText(String.valueOf(food.getPrice()));
            binding.textName.setText(food.getName());
        }
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.DataInsertSuccess.observe(this, info -> {
            if (info != null) {
                if (isBuyNow) {
                    Intent intent = new Intent(FoodDetailActivity.this, CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                showToast("Thêm vào giỏ hàng thành công");

            } else {
                showToast("Error");
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}