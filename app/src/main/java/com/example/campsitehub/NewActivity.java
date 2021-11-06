package com.example.campsitehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewActivity extends AppCompatActivity {

    EditText name,phone,email,password;
    TextView tvdis;
    Button print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        name=findViewById(R.id.edtxt_Name);
        phone=findViewById(R.id.edtxt_Phone);
        email=findViewById(R.id.edtxt_Email);
        password=findViewById(R.id.edtxt_Password);

        tvdis=findViewById(R.id.tv_dis);

        print=findViewById(R.id.btn_print);

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               tvdis.setText("Name :"+name.getText().toString()+"\n"+"Mobile :" +phone.getText().toString()+"\n"+"Email :"+email.getText().toString()+"\n"+"Password :"+password.getText().toString());

            }
        });


    }
}