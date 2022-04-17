package com.example.fooddrink.ui.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.Category;
import com.example.fooddrink.R;
import com.example.fooddrink.databinding.ItemCategoryBinding;
import com.example.fooddrink.ui.base.BaseLMAdapter;

public class CategoryAdapter extends BaseLMAdapter<Category, ItemCategoryBinding> {
    Context mContext;

    public CategoryAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void setupViews(ItemCategoryBinding binding, Category item, int position) {
        binding.foodName.setText(item.getName());
        Glide.with(mContext)
                .load(item.getImage())
                .error(R.drawable.img_no_image)
                .into(binding.foodImage);

        binding.getRoot().setOnClickListener(view -> {
            if (onClick != null) {
                onClick.onClick(view, position);
            }
        });
    }

    @Override
    public ItemCategoryBinding getViewBinding(ViewGroup parent, int viewType) {
        return ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
