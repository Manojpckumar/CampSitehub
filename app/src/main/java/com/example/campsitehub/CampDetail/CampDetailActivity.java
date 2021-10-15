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
import com.example.campsitehub.Retrofit.Api;
import com.example.campsitehub.Retrofit.RetrofitHelper;
import com.example.campsitehub.databinding.ActivityCampDetailBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;

import java.util.*;

public class CampDetailActivity extends AppCompatActivity {

    // creating object of ViewPager
    ViewPager mViewPager;
    ActivityCampDetailBinding binding;
    int camp_id;
    String panoramic;
    List<ResultData> resultData = new ArrayList<>();
    List<CampDetail> campDetails = new ArrayList<>();
    List<Amenity> amenities = new ArrayList<>();

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


       // camp_id = getIntent().getIntExtra("camp_id",0);
        Intent intent = getIntent();
        DataParcer book = intent.getParcelableExtra("Book");

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api =retrofit.create(Api.class);

        postDataUsingVolley(String.valueOf(book.getId()));


//        api.GetCampDetailById(Integer.parseInt(String.valueOf(book.getId()))).enqueue(new Callback<Example>() {
//            @Override
//            public void onResponse(Call<Example> call, Response<Example> response) {
//
//                resultData = new ArrayList<>();
//                campDetails = (List<CampDetail>) response.body().getResultData().getCampDetails();
//                amenities = (List<Amenity>) response.body().getResultData().getAmenities();
//
//
//                for(CampDetail detail : campDetails)
//                {
//                    binding.tvCampname.setText(detail.getCampsiteName());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Example> call, Throwable t) {
//
//            }
//        });


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
    private void postDataUsingVolley(String id) {
        // url to post our data
        String url = "https://campsitehub.000webhostapp.com/phase1/Api.php?apicall=getCampDetailsbyid";
        String BASE = "https://campsitehub.000webhostapp.com/";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(CampDetailActivity.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty
                // on below line we are displaying a success toast message.

                Toast.makeText(CampDetailActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    JSONObject object = respObj.getJSONObject("ResultData");
                    JSONArray array = object.getJSONArray("Camp_details");
                    JSONObject campObject = array.getJSONObject(0);

                    binding.tvCampname.setText(campObject.getString("campsite_name"));
                    binding.tvStrikeoff.setText(campObject.getString("old_price"));
                    binding.tvStrikeoff.setPaintFlags(binding.tvStrikeoff.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    binding.tvAmount.setText(campObject.getString("offer_price"));
                    binding.tvLikes.setText(campObject.getString("rating_number"));

                    if(campObject.getString("status").equals("1"))
                    {
                        binding.tvStatus.setText("AVAILABLE");
                        binding.tvStatus.setBackgroundColor(getResources().getColor(R.color.green));
                    }else
                    {
                        binding.tvStatus.setText("NOT AVAILABLE");
                        binding.tvStatus.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    LoadBanners(campObject.getString("campsite_banner")+","+campObject.getString("related_images"));
                    binding.tvDesc.setText(campObject.getString("description"));
                    panoramic = campObject.getString("panoramic");
                    // on below line we are setting this string s to our text view.

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(CampDetailActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("id", id);


                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void LoadBanners(String s) {

        List<String> imageList = Arrays.asList(s.split(","));

        mViewPagerAdapter = new ViewPagerAdapter(CampDetailActivity.this, imageList);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

    }
}
