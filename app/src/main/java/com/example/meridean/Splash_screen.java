package com.example.meridean;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
        InternetConnection nt = new InternetConnection(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (nt.isConnected())
                {
                    startActivity(new Intent(Splash_screen.this,MainActivity.class));

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
}