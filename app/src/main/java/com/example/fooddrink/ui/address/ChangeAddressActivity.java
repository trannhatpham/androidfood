package com.example.fooddrink.ui.address;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fooddrink.Model.User;
import com.example.fooddrink.R;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.databinding.ActivityChangeAddressBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.viewmodel.UserViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class ChangeAddressActivity extends BaseTestActivity<ActivityChangeAddressBinding> implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static int REQUESTMAP = 1;
    User info;
    String phone = "";
    UserViewModel viewModel;

    @Override
    public ActivityChangeAddressBinding getViewBinding() {
        return ActivityChangeAddressBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        phone = AppPreference.getUserPhone();
        info = AppPreference.getUserMain();
        binding.layoutHeader.imageBack.setOnClickListener(v -> finish());
        binding.layoutHeader.textTitle.setText("Địa chỉ");
        binding.textName.setText(info.getUsername());
        binding.textNumberPhone.setText(phone);
        binding.textAddress.setText(info.getAddress());

        binding.layoutMap.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REQUESTMAP);
        });

        binding.textAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REQUESTMAP);
        });
        binding.layoutHeader.textOK.setOnClickListener(v -> {
            showProgressDialog(true);
            info.setAddress(binding.textAddress.getText().toString());
            viewModel.updateUser(info);
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.DataUser.observe(this, user -> {
            if (user != null) {
                AppPreference.saveUserMain(info);
                setResult(RESULT_OK);
                this.finish();
                // result ok for the request activity before.

            }
            showProgressDialog(false);
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void checkLocation(String address) {
        LatLng locationMain = getLocationFromAddress(address);
        MarkerOptions markerOptions = new MarkerOptions();
        if (locationMain == null) return;
        markerOptions.position(locationMain);
        markerOptions.title(address);
        mMap.clear();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                locationMain, 16f);
        mMap.animateCamera(location);
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        String address = info.getAddress();
//        binding.etOrigin.setText(address);
        LatLng locationMain = getLocationFromAddress(address==null?"":address);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        MarkerOptions markerOptions = new MarkerOptions();
        if (locationMain == null) return;
        markerOptions.position(locationMain);
//
        markerOptions.title(address);
        mMap.clear();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                locationMain, 16f);
        mMap.animateCamera(location);
        mMap.addMarker(markerOptions);
        Log.d("status", "success");

    }

    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = null;
            if (address.size() > 0) {
                location = address.get(0);
            }
            if (location == null) return null;
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTMAP) {
                if (data == null) return;
                String gg = data.getExtras().getString("TITLE");
                binding.textAddress.setText(gg);
                checkLocation(gg);
            }
        }
    }
}
