package com.example.fooddrink.ui.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.fooddrink.FoodListDetailActivity;
import com.example.fooddrink.Model.Category;
import com.example.fooddrink.R;
import com.example.fooddrink.ViewHolder.MenuViewHolder;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.FragmentFoodMainBinding;
import com.example.fooddrink.ui.base.BaseFragment;
import com.example.fooddrink.ui.base.BaseLMAdapter;
import com.example.fooddrink.ui.viewmodel.MainViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FoodMainFragment extends BaseFragment<FragmentFoodMainBinding> {

    MainViewModel viewModel;
    CategoryAdapter adapter;

    @Override
    public FragmentFoodMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentFoodMainBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getListCategory();
        binding.recyclerFood.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new CategoryAdapter(getContext());
        binding.recyclerFood.setAdapter(adapter);
        adapter.setOnClick((view, position) -> {
            Intent foodList = new Intent(getContext(), FoodListDetailActivity.class);
            foodList.putExtra("CategoryId", adapter.getItem(position).getCategoryID());
            foodList.putExtra("CategoryName", adapter.getItem(position).getName());
            startActivity(foodList);
        });

        }

        @Override
        protected void initData () {

        }

        @Override
        protected void onObserver () {
            super.onObserver();
            viewModel.getItemDataCategory().observe(this, result -> {
                if (result != null) {
                    adapter.clearData();
                    adapter.addData(result);
                }
            });
        }

        @Override
        public void onClick (View view){

        }

        private void StartFoodList (String categoryId){
            FoodDetailFragment fragment = new FoodDetailFragment(categoryId);
            getActivity().getSupportFragmentManager().beginTransaction()
//                .add(R.id.frame_container, null, "three")
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack("12")
                    .hide(this)
                    .addToBackStack(null)
                    .commit();
        }
    }
