package com.example.campsitehub.CampDetail;

import android.view.View;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.campsitehub.Homepage.HomePageAdapter;
import com.example.campsitehub.R;
import com.example.campsitehub.databinding.ActivityCampDetailBinding;
import com.example.campsitehub.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.List;

public class CampDetailActivity extends AppCompatActivity {

    // creating object of ViewPager
    ViewPager mViewPager;
    ActivityCampDetailBinding binding;

    // images array
    int[] images = {R.drawable.aa, R.drawable.bb};
    List<AmenitiesModel> list = new ArrayList<>();

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_camp_detail);

        binding = ActivityCampDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.vp_viewpager);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(CampDetailActivity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

        list.add(new AmenitiesModel("Tent",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Bed",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Jacket",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Socks",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Hat",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Hat",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Hat",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Hat",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Hat",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Hat",R.drawable.ic_panoramaview));
        list.add(new AmenitiesModel("Hat",R.drawable.ic_panoramaview));

        AmenitiesAdapter adapter = new AmenitiesAdapter(this,list);
        binding.rcvAmenities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.rcvAmenities.setAdapter(adapter);


    }
}