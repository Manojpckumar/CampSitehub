package com.example.campsitehub.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomMainHeading extends androidx.appcompat.widget.AppCompatTextView {

    public CustomMainHeading(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Medium.ttf");
        this.setTypeface(face);
    }

    public CustomMainHeading(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Medium.ttf");
        this.setTypeface(face);
    }

    public CustomMainHeading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Medium.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}
