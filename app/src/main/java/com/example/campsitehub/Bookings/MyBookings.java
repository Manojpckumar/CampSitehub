package com.example.campsitehub.Bookings;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.campsitehub.CampDetail.Example;
import com.example.campsitehub.ResponseCommon;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;
import com.example.campsitehub.databinding.ActivityMyBookingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MyBookings extends AppCompatActivity implements GetResult.MyListener,RecyclerViewClickInterface {

    ActivityMyBookingsBinding binding;
    FirebaseUser user;
    List<AllBooking> allBookingList;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyBookingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        user = FirebaseAuth.getInstance().getCurrentUser();
        custPrograssbar = new CustPrograssbar();
        getAllBookings();


    }

    private void getAllBookings() {
        custPrograssbar.progressCreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user.getEmail());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().GetBookings((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "Allbookings");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        custPrograssbar.close();
        if (callNo.equalsIgnoreCase("Allbookings")) {
            Gson gson = new Gson();
            Example home = gson.fromJson(result.toString(), Example.class);
            allBookingList = new ArrayList<>();
            allBookingList.addAll(home.getResultData().getAllBookings());
            MyBookingAdapter adapter = new MyBookingAdapter(this,allBookingList,this);
            binding.rcvMybooking.setLayoutManager(new LinearLayoutManager(this));

            binding.rcvMybooking.setAdapter(adapter);

        }

        else if(callNo.equalsIgnoreCase("UpdateBookstatus")){

            Gson gson = new Gson();
            ResponseCommon responseCommon=gson.fromJson(result.toString(),ResponseCommon.class);
            if(responseCommon.getResult().equals("true")){

                getAllBookings();

            }


        }
        else{
            Toast.makeText(this, "Invalid call", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onItemClick(int position, String chk) {

        ChangeStatus(position,chk);

    }

    private void ChangeStatus(int position, String chk) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("booking_id", allBookingList.get(position).getBookingId());
            jsonObject.put("status", chk);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().UpdateBookstatus((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "UpdateBookstatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}