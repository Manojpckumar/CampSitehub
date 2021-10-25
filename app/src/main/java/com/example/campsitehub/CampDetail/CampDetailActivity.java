package com.example.campsitehub.CampDetail;

import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.campsitehub.PanoramicView.PanoramicView;
import com.example.campsitehub.Parcelable.DataParcer;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.Api;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Retrofit.RetrofitHelper;
import com.example.campsitehub.databinding.ActivityCampDetailBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;

import java.util.*;

public class CampDetailActivity extends AppCompatActivity implements GetResult.MyListener {

    // creating object of ViewPager
    ViewPager mViewPager;
    ActivityCampDetailBinding binding;
    String panoramic;
    List <Amenity> amenity;


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

        Intent intent = getIntent();
        DataParcer book = intent.getParcelableExtra("Book");

        Log.d("---", String.valueOf(book.getId()));

        getCampbyid(book.getId());


        binding.btnBooknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CampDetailActivity.this, BookingConfirmation.class));

            }
        });

        binding.ivPanoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CampDetailActivity.this, PanoramicView.class));
            }
        });


    }

    private void getCampbyid(long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getDetails((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "campviewbyid");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    private void LoadBanners(String s) {

        List<String> imageList = Arrays.asList(s.split(","));

        mViewPagerAdapter = new ViewPagerAdapter(CampDetailActivity.this, imageList);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("campviewbyid")){
            Gson gson = new Gson();
            Example home = gson.fromJson(result.toString(), Example.class);

            binding.tvCampname.setText(home.getResultData().getCampDetails().getCampsiteName());
            binding.tvStrikeoff.setText(home.getResultData().getCampDetails().getOldPrice());
            binding.tvStrikeoff.setPaintFlags(binding.tvStrikeoff.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.tvAmount.setText(home.getResultData().getCampDetails().getOfferPrice());
            binding.tvLikes.setText(home.getResultData().getCampDetails().getRatingNumber());
            if(home.getResultData().getCampDetails().getStatus().equals("1"))
            {
                binding.tvStatus.setText("AVAILABLE");
                binding.tvStatus.setBackgroundColor(getResources().getColor(R.color.green));
            }else
            {
                binding.tvStatus.setText("NOT AVAILABLE");
                binding.tvStatus.setBackgroundColor(getResources().getColor(R.color.red));
            }

            LoadBanners(home.getResultData().getCampDetails().getCampsiteBanner()+","+home.getResultData().getCampDetails().getRealtedImages());
            binding.tvDesc.setText(home.getResultData().getCampDetails().getDescription());
            panoramic = home.getResultData().getCampDetails().getPanoramic();

            amenity=new ArrayList<>();

            amenity.addAll(home.getResultData().getAmenities());

            AmenitiesAdapter adapter = new AmenitiesAdapter(this,amenity);
            binding.rcvAmenities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
            binding.rcvAmenities.setAdapter(adapter);


        }



    }
}
