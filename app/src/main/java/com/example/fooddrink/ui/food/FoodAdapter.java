package com.example.fooddrink.ui.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.R;
import com.example.fooddrink.databinding.ItemFoodBinding;
import com.example.fooddrink.ui.base.BaseLMAdapter;

public class FoodAdapter extends BaseLMAdapter<Food, ItemFoodBinding> {
    Context mContext;

    public FoodAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void setupViews(ItemFoodBinding binding, Food food, int position) {
        binding.foodName.setText(food.getName());
        binding.textPrice.setText(food.getPrice().toString());
        Glide.with(mContext)
                .load(food.getImage())
                .error(R.drawable.img_no_image)
                .override(300, 300)
                .into(binding.foodImage);
        binding.getRoot().setOnClickListener(view -> {
            if (onClick!=null){
                onClick.onClick(view,position);
            }
        });
    }

    @Override
    public ItemFoodBinding getViewBinding(ViewGroup parent, int viewType) {
        return ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
