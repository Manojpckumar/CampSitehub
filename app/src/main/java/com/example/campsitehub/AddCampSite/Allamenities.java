
package com.example.campsitehub.AddCampSite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.campsitehub.Amenities.AddAmenities;
import com.example.campsitehub.Amenities.AllAmenity;
import com.example.campsitehub.Bookings.MyBookingAdapter;
import com.example.campsitehub.CampDetail.Example;
import com.example.campsitehub.R;
import com.example.campsitehub.ResponseCommon;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;
import com.example.campsitehub.databinding.ActivityAllCampsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Allamenities extends AppCompatActivity implements GetResult.MyListener, View.OnClickListener, RecyclerViewClickInterface {

    ActivityAllCampsBinding binding;
    List<AllAmenity> allamenitiesList;
    AlertDialog.Builder builder;
    AmenityAdapter adapter;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide(); //hide the title bar.
        binding = ActivityAllCampsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        createViews(binding);
        custPrograssbar = new CustPrograssbar();
        builder = new AlertDialog.Builder(this);
        getAllamenities("1");
        binding.tbCommon.AddView.setText("Add Amenity");

    }

    private void createViews(ActivityAllCampsBinding binding) {
        binding.tbCommon.backFinish.setOnClickListener(this);
        binding.tbCommon.AddView.setOnClickListener(this);
    }

    private void getAllamenities(String rcode) {
        if(rcode.equals("1")) {
            custPrograssbar.progressCreate(this);
            custPrograssbar.setCancel(false);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", "1");
                JsonParser jsonParser = new JsonParser();
                Call<JsonObject> call = APIClient.getInterface().getAllAmenities((JsonObject) jsonParser.parse(jsonObject.toString()));
                GetResult getResult = new GetResult();
                getResult.setMyListener(this);
                getResult.onNCHandle(call, "getAllAmenities");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        else{

            custPrograssbar.progressCreate(this);
            custPrograssbar.setCancel(false);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", "1");
                JsonParser jsonParser = new JsonParser();
                Call<JsonObject> call = APIClient.getInterface().getAllAmenities((JsonObject) jsonParser.parse(jsonObject.toString()));
                GetResult getResult = new GetResult();
                getResult.setMyListener(this);
                getResult.onNCHandle(call, "getAllAmenities2");
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {
        if (callNo.equalsIgnoreCase("getAllAmenities")) {
            custPrograssbar.close();
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            allamenitiesList = new ArrayList<>();
            allamenitiesList.addAll(example.getResultData().getAllAmenity());


            adapter = new AmenityAdapter(this, allamenitiesList, this);
            binding.rcvAmenities.setLayoutManager(new LinearLayoutManager(this));

            binding.rcvAmenities.setAdapter(adapter);


        } else if (callNo.equalsIgnoreCase("deleteAmenity")) {
            Gson gson = new Gson();
            ResponseCommon responseCommon = gson.fromJson(result.toString(), ResponseCommon.class);
            if (responseCommon.getResult().equals("true")) {
                getAllamenities("2");

            }
        }
            else if(callNo.equalsIgnoreCase("getAllAmenities2")){
                custPrograssbar.close();
                Gson gson = new Gson();
                Example example = gson.fromJson(result.toString(), Example.class);
                allamenitiesList = new ArrayList<>();
                allamenitiesList.addAll(example.getResultData().getAllAmenity());


                adapter = new AmenityAdapter(this, allamenitiesList, this);
                binding.rcvAmenities.setLayoutManager(new LinearLayoutManager(this));

                binding.rcvAmenities.setAdapter(adapter);
            Toast.makeText(this, "DELETED", Toast.LENGTH_SHORT).show();


            }


        else {
            Toast.makeText(this, "Inavlid call request", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.back_finish:

                finish();
                break;

            case R.id.Add_View:
                startActivity(new Intent(this, AddAmenities.class).putExtra("key","0"));

                break;



        }

    }

    @Override
    public void onItemClick(int position, String chk) {

        if (chk.equalsIgnoreCase("EDIT")) {
            String a_id=allamenitiesList.get(position).getAtId();
            startActivity(new Intent(this, AddAmenities.class).putExtra("key",a_id));
        } else {

            AmenityDialogue(allamenitiesList.get(position).getAtId());

        }

    }

    private void AmenityDialogue(String atId) {

        builder.setMessage("Manage Amenity").setTitle("Amenity Management");
        builder.setMessage("Do you want to Proceed ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        deleteamenity(atId);

                    }


                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "",
                                Toast.LENGTH_SHORT).show();
                    }
                });


        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();


    }

    private void deleteamenity(String atId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("at_id", atId);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().del_amenity((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "deleteAmenity");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}