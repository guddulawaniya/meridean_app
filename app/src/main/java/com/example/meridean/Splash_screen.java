package com.example.meridean;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView image = findViewById(R.id.splashimage);

        Animation ani = AnimationUtils.loadAnimation(this,R.anim.imageanimation);
        image.startAnimation(ani);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isConnected())
                {
                    startActivity(new Intent(Splash_screen.this,login_Activity.class));

                    overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                    finish();
                }
                else
                {
                    startActivity(new Intent(Splash_screen.this,offline_Activity.class));

                    overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                    finish();
                }


            }
        },2000);



    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}