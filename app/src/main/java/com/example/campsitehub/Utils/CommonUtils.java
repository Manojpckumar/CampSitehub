package com.example.campsitehub.Utils;

import androidx.appcompat.app.AppCompatActivity;

public class CommonUtils extends AppCompatActivity {

    String from_date,to_date;

    public CommonUtils(String from_date, String to_date) {
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }
}
