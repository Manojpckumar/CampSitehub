package com.example.campsitehub.PaymentGateway;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.campsitehub.CampDetail.BookingConfirmation;
import com.example.campsitehub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class RazorpayActivity extends AppCompatActivity implements PaymentResultListener {
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        String amount;
        user= FirebaseAuth.getInstance().getCurrentUser();
        amount = getIntent().getStringExtra("amount");
        startPayment(String.valueOf(amount));

        Toast.makeText(this, String.valueOf(amount), Toast.LENGTH_SHORT).show();

    }

    public void startPayment(String amount) {
        final Activity activity = this;
        final Checkout co = new Checkout();
        co.setKeyID("rzp_test_04RQzhAUVjVxNV");
        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("currency", "INR");
            double total = Double.parseDouble(amount);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", user.getEmail());
            preFill.put("contact", "+91 9961424428");
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        BookingConfirmation.paymentsucsses=1;
        BookingConfirmation.transactionID=s;
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}