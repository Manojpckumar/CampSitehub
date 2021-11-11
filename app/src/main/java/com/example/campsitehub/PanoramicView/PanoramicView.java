package com.example.campsitehub.PanoramicView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import co.gofynd.gravityview.GravityView;
import com.example.campsitehub.R;
import com.example.campsitehub.databinding.ActivityMyBookingsBinding;
import com.example.campsitehub.databinding.ActivityPanoramicViewBinding;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import okio.Options;

public class PanoramicView extends AppCompatActivity {

    VrPanoramaView vrPanoramaView;
    ActivityPanoramicViewBinding binding;
    private GravityView gravityView;
    private boolean supportvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_panoramic_view);

        binding = ActivityPanoramicViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
        if(supportvalue){
            this.gravityView.setImage(binding.ivImage,R.drawable.panoview).center();
        }
        else
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.panoview);
            binding.ivImage.setImageBitmap(bitmap);
        }



//        VrPanoramaView.Options options = new VrPanoramaView.Options();
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.panoview);
//        binding.vrPanoramaView.loadImageFromBitmap(bitmap, options);



    }

    @Override
    protected void onResume() {
        super.onResume();
        gravityView.registerListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gravityView.unRegisterListener();
    }

    public void init()
    {
    this.gravityView = GravityView.getInstance(getBaseContext());
    this.supportvalue = gravityView.deviceSupported();
    }
}