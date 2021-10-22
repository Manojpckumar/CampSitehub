package com.example.campsitehub.AddCampSite;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.campsitehub.R;
import com.example.campsitehub.databinding.ActivityAddCampSiteBinding;
import com.example.campsitehub.databinding.ActivityMyBookingsBinding;

public class AddCampSite extends AppCompatActivity {

    ActivityAddCampSiteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_camp_site);

        binding = ActivityAddCampSiteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}