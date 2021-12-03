package com.example.campsitehub.PaymentGateway;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.campsitehub.Authentication.LoginActivity;
import com.example.campsitehub.CampDetail.BookingConfirmation;
import com.example.campsitehub.R;
import com.example.campsitehub.SplashScreen.SplashActivity;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.databinding.ActivityRazorpayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RazorpayActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseUser user;
    ActivityRazorpayBinding binding;
    String amount, layout_def;
    CustPrograssbar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRazorpayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        progressBar = new CustPrograssbar();
        user = FirebaseAuth.getInstance().getCurrentUser();
        binding.userName.setText(user.getEmail());
        amount = getIntent().getStringExtra("amount");
        initView(binding);

    }

    private void initView(ActivityRazorpayBinding binding) {
        binding.llCard.setOnClickListener(this);
        binding.llUpi.setOnClickListener(this);
        binding.pay.setOnClickListener(this);
        binding.cardCvv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.llCard:


                binding.llCardDetails.setVisibility(View.VISIBLE);
                binding.llUpiDetails.setVisibility(View.GONE);
                layout_def = "card";


                break;


            case R.id.llUpi:

                binding.llCardDetails.setVisibility(View.GONE);
                binding.llUpiDetails.setVisibility(View.VISIBLE);
                layout_def = "upi";
                break;


            case R.id.card_expiry:


                break;

            case R.id.pay:

                if (layout_def.equalsIgnoreCase("card")) {

                    String card_number = binding.cardNumber.getText().toString();
                    String card_holder = binding.cardHolder.getText().toString();
                    String card_expiry = binding.cardExpiry.getText().toString();
                    String card_cvv = binding.cardCvv.getText().toString();


                    if (card_number.isEmpty()) {

                        binding.cardNumber.setError("Card Number can't be empty");

                    } else if (validateCreditCardNumber(card_number) == false) {

                        binding.cardNumber.setError("Invalid Card format");

                    } else if (card_holder.isEmpty()) {

                        binding.cardHolder.setError("Name can't be empty");
                    } else if (card_expiry.isEmpty()) {

                        binding.cardExpiry.setError("Field can't be empty");
                    } else if (card_cvv.isEmpty()) {

                        binding.cardCvv.setError("Field can't be empty");
                    } else {

                        progressBar.progressCreate(this);
                        progressBar.close();
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                final String s = "";
                                BookingConfirmation.paymentsucsses=1;
                                BookingConfirmation.transactionID = s;
                                finish();

                            }
                        }, 5000);



                    }


                } else {

                    if(binding.upi.getText().toString().isEmpty()){

                        binding.upi.setError("Field can't be empty");

                    }
                    else{

                        progressBar.progressCreate(this);
                        progressBar.close();
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                final String s = "";
                                BookingConfirmation.paymentsucsses=1;
                                BookingConfirmation.transactionID = s;
                                finish();

                            }
                        }, 5000);



                    }



                }


                break;

        }
    }

    private static boolean validateCreditCardNumber(String str) {

        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }


//    @Override
//    public void onPaymentSuccess(String s) {
//        BookingConfirmation.paymentsucsses=1;
//        BookingConfirmation.transactionID=s;
//        finish();
//
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//
//    }


}