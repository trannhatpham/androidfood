package com.example.fooddrink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.ViewHolder.FoodViewHolder;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivityFoodListBinding;
import com.example.fooddrink.ui.base.BaseLMAdapter;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.cart.CartActivity;
import com.example.fooddrink.ui.food.FoodAdapter;
import com.example.fooddrink.ui.viewmodel.FoodViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FoodListDetailActivity extends BaseTestActivity<ActivityFoodListBinding> {
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "", categoryName = "";
    //    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    FoodAdapter adapter;
    FoodViewModel viewModel;

    @Override
    public ActivityFoodListBinding getViewBinding() {
        return ActivityFoodListBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        database = PublicData.database;
        foodList = database.getReference("Food");
        binding.recyclerFood.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new FoodAdapter(this);
        binding.recyclerFood.setAdapter(adapter);

        //get intent here
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
            categoryName = getIntent().getStringExtra("CategoryName");
            System.out.println(categoryId + ".............................................");
        }
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFood(categoryId);
            System.out.println(categoryId + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        binding.layoutHeader.imageCart.setOnClickListener(view -> {
            Intent intent = new Intent(FoodListDetailActivity.this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        adapter.setOnClick((view, position) -> {
            Intent intent = new Intent(FoodListDetailActivity.this, FoodDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ITEM", adapter.getItem(position));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        viewModel.getListFood(categoryId);
        binding.layoutHeader.imageDrawer.setOnClickListener(view -> finish());
        binding.layoutHeader.textTitle.setText(categoryName);
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.getItemDataFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (foods != null) {
                    adapter.clearData();
                    adapter.addData(foods);
                }
            }
        });
    }

    private void loadListFood(String categoryId) {
        System.out.println(">.......................");
//        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
//                R.layout.item_food,
//                FoodViewHolder.class,
//                foodList.orderByChild("MenuId").equalTo(categoryId) //like: select * from Foods where MenuId =
//        ) {
//            @Override
//            protected void populateViewHolder(FoodViewHolder holder, Food food, int i) {
//                holder.food_name.setText(food.getName());
//                holder.textPrice.setText(food.getPrice());
////                Picasso.get().load(food.getImage()).into(foodViewHolder.food_image);
//                Glide.with(FoodListDetailActivity.this)
//                        .load(food.getImage())
//                        .error(R.drawable.img_no_image)
//                        .into(holder.food_image);
//                holder.setInternClickListener((view, position, isLongCick) -> {
//
//                        }
//                );
//            }
//        };
        //set adapter
    }

    @Override
    public void onClick(View view) {

    }
}
