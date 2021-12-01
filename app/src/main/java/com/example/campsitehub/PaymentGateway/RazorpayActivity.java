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
import com.squareup.square.Environment;
import com.squareup.square.SquareClient;
import com.squareup.square.api.PaymentsApi;
import com.squareup.square.models.CreatePaymentRequest;
import com.squareup.square.models.Money;

import org.json.JSONObject;

public class RazorpayActivity extends AppCompatActivity implements PaymentResultListener {
    FirebaseUser user;
    PaymentsApi paymentsApi;
    SquareClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        String amount;
         client = new SquareClient.Builder()
                .environment(Environment.SANDBOX)
                .accessToken("EAAAEKGqhzStVu2yHZfr1RzHuShjEB1zFDKDe_MsYu3S2DQ9ZdWdXYjXox17xrOi")
                .build();
         paymentsApi = client.getPaymentsApi();
        user= FirebaseAuth.getInstance().getCurrentUser();
        amount = getIntent().getStringExtra("amount");
       // startPayment(String.valueOf(amount));
        paymentinitiate();

        Toast.makeText(this, String.valueOf(amount), Toast.LENGTH_SHORT).show();

    }

    private void paymentinitiate() {

        Money bodyAmountMoney = new Money.Builder()
                .amount(100L)
                .currency("USD")
                .build();
        Money bodyTipMoney = new Money.Builder()
                .amount(198L)
                .currency("CHF")
                .build();
        Money bodyAppFeeMoney = new Money.Builder()
                .amount(10L)
                .currency("USD")
                .build();
        CreatePaymentRequest body = new CreatePaymentRequest.Builder(
                "ccof:GaJGNaZa8x4OgDJn4GB",
                "7b0f3ec5-086a-4871-8f13-3c81b3875218",
                bodyAmountMoney)
                .tipMoney(bodyTipMoney)
                .appFeeMoney(bodyAppFeeMoney)
                .delayDuration("delay_duration6")
                .autocomplete(true)
                .orderId("order_id0")
                .customerId("W92WH6P11H4Z77CTET0RNTGFW8")
                .locationId("L88917AVBK2S5")
                .referenceId("123456")
                .note("Brief description")
                .build();

        paymentsApi.createPaymentAsync(body).thenAccept(result -> {

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            // TODO success callback handler
        }).exceptionally(exception -> {
            // TODO failure callback handler
            return null;
        });
    }

    public void startPayment(String amount) {
        final Activity activity = this;
        final Checkout co = new Checkout();
        co.setKeyID("rzp_test_04RQzhAUVjVxNV");
        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("currency", "USD");
            double total = Double.parseDouble(amount);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", "sreeragm46@gmail.com");
            preFill.put("contact", "+91 999999999");
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