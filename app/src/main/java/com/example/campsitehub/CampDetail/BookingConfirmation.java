package com.example.campsitehub.CampDetail;

import androidx.appcompat.app.AppCompatActivity;
import com.example.campsitehub.Bookings.MyBookings;
import com.example.campsitehub.PaymentGateway.RazorpayActivity;
import com.example.campsitehub.R;
import com.example.campsitehub.ResponseCommon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.databinding.ActivityBookingConfirmationBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;

public class BookingConfirmation extends AppCompatActivity implements GetResult.MyListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    ActivityBookingConfirmationBinding binding;
    String am_id, camp_id, campamount;
    CustPrograssbar custPrograssbar;
    public static int paymentsucsses = 0;
    public static String transactionID = "0";
    ArrayList<String> mylist = new ArrayList<String>();
    double totInt;
    FirebaseUser user;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingConfirmationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Random_Transaction_String(this);
        custPrograssbar = new CustPrograssbar();
        am_id = getIntent().getStringExtra("amkey");
        camp_id = getIntent().getStringExtra("campkey");
        campamount = getIntent().getStringExtra("campamount");
        custPrograssbar.progressCreate(this);
        getCampbyid(camp_id);
        getamenbyid(am_id);
        binding.btnBook.setOnClickListener(this);
        binding.datePi.setOnClickListener(this);
        binding.trackBookings.setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        binding.rdGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                         radioButton = (RadioButton) findViewById(checkedId);
                                                         Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
        );

    }

    public static String myToString(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(formatter);
    }


    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        if (binding.edtdate.getText().toString().isEmpty()) {
            dpd.setTitle("Choose Start Date");
        } else {
            dpd.setTitle("Choose End Date");
        }
        FragmentManager fm = getFragmentManager();
        dpd.show(fm, "DatePickerDialog");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;

        for (int i = 0; i < mylist.size(); i++) {

            try {
                date = sdf.parse(mylist.get(i));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar = dateToCalendar(date);
            System.out.println(calendar.getTime());

            List<Calendar> dates = new ArrayList<>();
            dates.add(calendar);
            Calendar[] disabledDays1 = dates.toArray(new Calendar[dates.size()]);
            dpd.setSelectableDays(disabledDays1);
        }

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
    protected void onResume() {
        super.onResume();

        if (paymentsucsses == 1) {
            paymentsucsses = 0;
            custPrograssbar.progressCreate(this);
            sendBooking();
        }


    }

    private void sendBooking() {
        Date time = java.util.Calendar.getInstance().getTime();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("transaction_id", Random_Transaction_String(this));
            jsonObject.put("user_id", user.getEmail());
            jsonObject.put("camp_id", camp_id);
            jsonObject.put("amenity_id", am_id);
            jsonObject.put("payment_mode", radioButton.getText());
            jsonObject.put("total", binding.total.getText().toString());
            jsonObject.put("date_obooking", binding.edtdate.getText().toString() + "," + binding.edttodate.getText().toString());
            jsonObject.put("time_obooking", time);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addBooking((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addBooking");
        } catch (JSONException e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            LocalDate start = LocalDate.parse(home.getResultData().getCampDetails().getAvailableDates());
            LocalDate end = LocalDate.parse(home.getResultData().getCampDetails().getAvailable_dates_to());
            List<LocalDate> totalDates = new ArrayList<>();
            while (!start.isAfter(end)) {
                totalDates.add(start);
                start = start.plusDays(1);
            }
            for (LocalDate ld : totalDates) {

                mylist.add(myToString(ld));
            }


        } else if (callNo.equalsIgnoreCase("amenitiesbyid")) {

            Gson gson = new Gson();
            Example am = gson.fromJson(result.toString(), Example.class);
            custPrograssbar.close();
            binding.amName.setText(am.getResultData().getAmenityDetails().getAtName() + "\n($ " + am.getResultData().getAmenityDetails().getAtPrice() + " )");
            double d = Double.parseDouble(am.getResultData().getAmenityDetails().getAtPrice().trim());
            double dv = Double.parseDouble(campamount);
            double tot = d + dv;
            binding.total.setText(String.valueOf(tot));
            SettottoPayment(tot);


        } else if (callNo.equalsIgnoreCase("addBooking")) {
            custPrograssbar.close();
            Gson gson = new Gson();
            ResponseCommon response = gson.fromJson(result.toString(), ResponseCommon.class);
            Toast.makeText(this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();
            binding.lvltwo.setVisibility(View.VISIBLE);
        }


    }

    private void SettottoPayment(double tots) {
        tots = totInt;

    }

    public static String Random_Transaction_String(BookingConfirmation bookingConfirmation) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Toast.makeText(bookingConfirmation, generatedString, Toast.LENGTH_SHORT).show();
       return "txn"+generatedString;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        if (binding.edtdate.getText().toString().isEmpty()) {
            binding.edtdate.setText(date);
            showDatePicker();
        } else {

            binding.edttodate.setText(date);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_book:
                if (binding.edtdate.getText().toString().isEmpty()) {

                    binding.edtdate.setError("Please Choose a date ");
                } else {


                    startActivity(new Intent(this, RazorpayActivity.class).putExtra("amount", binding.total.getText().toString()));

                }
                break;

            case R.id.date_pi:
                showDatePicker();
                break;


            case R.id.track_bookings:
                startActivity(new Intent(this, MyBookings.class));

                break;
        }


    }


}