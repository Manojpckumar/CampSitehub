package com.example.campsitehub.Amenities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.campsitehub.R;
import com.example.campsitehub.databinding.ActivityAddAmenitiesBinding;

public class AddAmenities extends AppCompatActivity {

    ActivityAddAmenitiesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddAmenitiesBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

    }
}