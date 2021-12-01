package com.example.campsitehub.AddCampSite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.campsitehub.CampDetail.AdminCamp;
import com.example.campsitehub.CampDetail.Example;
import com.example.campsitehub.R;
import com.example.campsitehub.ResponseCommon;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;
import com.example.campsitehub.databinding.ActivityAllCamps2Binding;
import com.example.campsitehub.databinding.ActivityAllCampsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllCampsActivity extends AppCompatActivity implements GetResult.MyListener, RecyclerViewClickInterface {

    ActivityAllCamps2Binding binding;
    List<AdminCamp> adminCampList;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide(); //hide the title bar.
        binding = ActivityAllCamps2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        custPrograssbar=new CustPrograssbar();
        binding.tbCommon.toolbarHead.setText("ALL LISTED CAMPS");
        binding.tbCommon.backFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getAllCamps();
    }

    private void getAllCamps() {
        custPrograssbar.progressCreate(this);
        custPrograssbar.setCancel(false);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", "1");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllCamps((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllCamps");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callback(JsonObject result, String callNo) {


        if (callNo.equalsIgnoreCase("getAllCamps")) {
            custPrograssbar.close();
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            adminCampList = new ArrayList<>();
            adminCampList.addAll(example.getResultData().getAdminCamps());
            AllcampAdapter adapter = new AllcampAdapter(this, adminCampList, this);
            binding.rcvAllcamps.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvAllcamps.setAdapter(adapter);


        } else if (callNo.equalsIgnoreCase("changestatus")) {

            Gson gson = new Gson();

            ResponseCommon responseCommon = gson.fromJson(result.toString(), ResponseCommon.class);
            if (responseCommon.getResult().equals("true")) {

                getAllCamps();

            }


        } else {
            Toast.makeText(this, "Invalid call", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(int position, String chk) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("camp_id", adminCampList.get(position).getId());
            jsonObject.put("status", chk);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().updateCampstatus((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "changestatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}