package com.example.campsitehub.CampDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.databinding.ActivityBookingConfirmationBinding;
import com.example.campsitehub.databinding.ActivityCampDetailBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class BookingConfirmation extends AppCompatActivity implements GetResult.MyListener,DatePickerDialog.OnDateSetListener  {

    ActivityBookingConfirmationBinding binding;
    String am_id, camp_id,campamount;
    CustPrograssbar custPrograssbar;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingConfirmationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        custPrograssbar = new CustPrograssbar();
        am_id = getIntent().getStringExtra("amkey");
        camp_id = getIntent().getStringExtra("campkey");
        campamount = getIntent().getStringExtra("campamount");
        custPrograssbar.progressCreate(this);
        getCampbyid(camp_id);
        getamenbyid(am_id);

binding.datePi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }



    private void getamenbyid(String am_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("am_id", am_id);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAmenitybyid((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "amenitiesbyid");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getCampbyid(String camp_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", camp_id);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getDetails((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "campviewbyid");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callback(JsonObject result, String callNo) {


        if (callNo.equalsIgnoreCase("campviewbyid")) {
            Gson gson = new Gson();
            Example home = gson.fromJson(result.toString(), Example.class);

            binding.campName.setText(home.getResultData().getCampDetails().getCampsiteName() + "\n($ " + home.getResultData().getCampDetails().getOfferPrice() + " )" + "\n" + "+");
            binding.campAmnt.setText(home.getResultData().getCampDetails().getOfferPrice().trim());


        } else if (callNo.equalsIgnoreCase("amenitiesbyid")) {

            Gson gson = new Gson();
            Example am = gson.fromJson(result.toString(), Example.class);
            custPrograssbar.close();
            binding.amName.setText(am.getResultData().getAmenityDetails().getAtName() + "\n($ " + am.getResultData().getAmenityDetails().getAtPrice() + " )");
            binding.amenAmnt.setText(am.getResultData().getAmenityDetails().getAtPrice().trim());
            double d= Double.parseDouble(am.getResultData().getAmenityDetails().getAtPrice().trim());
           double dv= Double.parseDouble(campamount);
           double tot=d+dv;
           binding.total.setText("$ "+String.valueOf(tot));





        }


    }



    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}