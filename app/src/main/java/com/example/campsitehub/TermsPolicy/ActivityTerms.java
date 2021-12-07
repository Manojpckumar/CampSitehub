package com.example.campsitehub.TermsPolicy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.campsitehub.CampDetail.Example;
import com.example.campsitehub.R;
import com.example.campsitehub.ResponseCommon;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.databinding.ActivityAvtivityTermsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class ActivityTerms extends AppCompatActivity implements GetResult.MyListener, View.OnClickListener {

    ActivityAvtivityTermsBinding binding;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide(); //hide the title bar.
        binding = ActivityAvtivityTermsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        custPrograssbar=new CustPrograssbar();
        binding.tbCommon.AddView.setVisibility(View.GONE);
        binding.update.setOnClickListener(this);
        if (getIntent().getStringExtra("Activity").toString().equals("terms")) {
            binding.tbCommon.toolbarHead.setText("Terms and Policies");

        } else {

            binding.tbCommon.toolbarHead.setText("Privacy and Policies");
        }

        binding.tbCommon.backFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getTermsandPolicy();
    }

    private void getTermsandPolicy() {
        custPrograssbar.progressCreate(this);
        custPrograssbar.setCancel(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("settings_id", "1");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllSettings((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllSettings");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("getAllSettings")) {
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            custPrograssbar.close();
            if (getIntent().getStringExtra("Activity").toString().equals("terms")) {

                binding.terms.setText(example.getResultData().getTermsPolicy().getTerms());
                binding.idtest.setText(example.getResultData().getTermsPolicy().getId());

            } else {
                binding.terms.setText(example.getResultData().getTermsPolicy().getPrivacy());
                binding.idtest.setText(example.getResultData().getTermsPolicy().getId());
            }


        }
        else if(callNo.equalsIgnoreCase("updateSettings")){
            Gson gson = new Gson();
            ResponseCommon responseCommon=gson.fromJson(result.toString(),ResponseCommon.class);
             if(responseCommon.getResult().equals("true")){

                 Toast.makeText(ActivityTerms.this, "UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                 getTermsandPolicy();

             }
             else{


             }

        }
        else{


        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.update:
                if(binding.terms.getText().toString().equals(""))
                {

                    Toast.makeText(ActivityTerms.this, "Fiels can't be empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    if (getIntent().getStringExtra("Activity").toString().equals("terms")) {

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("id",binding.idtest.getText().toString() );
                            jsonObject.put("terms",binding.terms.getText().toString() );
                            jsonObject.put("privacy","1");
                            JsonParser jsonParser = new JsonParser();
                            Call<JsonObject> call = APIClient.getInterface().updateSettings((JsonObject) jsonParser.parse(jsonObject.toString()));
                            GetResult getResult = new GetResult();
                            getResult.setMyListener(this);
                            getResult.onNCHandle(call, "updateSettings");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                    else{

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("id",binding.idtest.getText().toString() );
                            jsonObject.put("terms","1" );
                            jsonObject.put("privacy",binding.terms.getText().toString() );
                            JsonParser jsonParser = new JsonParser();
                            Call<JsonObject> call = APIClient.getInterface().updateSettings((JsonObject) jsonParser.parse(jsonObject.toString()));
                            GetResult getResult = new GetResult();
                            getResult.setMyListener(this);
                            getResult.onNCHandle(call, "updateSettings");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }

                }


                break;
        }

    }
}