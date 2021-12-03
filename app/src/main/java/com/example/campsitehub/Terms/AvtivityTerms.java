package com.example.campsitehub.Terms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.campsitehub.R;
import com.example.campsitehub.databinding.ActivityAvtivityTermsBinding;

public class AvtivityTerms extends AppCompatActivity {

    ActivityAvtivityTermsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide(); //hide the title bar.
        binding=ActivityAvtivityTermsBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
       binding.tbCommon.AddView.setVisibility(View.GONE);
       binding.tbCommon.toolbarHead.setText("Terms and Policies");
       binding.tbCommon.backFinish.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });

    }
}