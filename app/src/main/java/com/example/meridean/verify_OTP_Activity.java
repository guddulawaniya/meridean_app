package com.example.meridean;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.chaos.view.PinView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class verify_OTP_Activity extends AppCompatActivity {

    PinView pinView;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;

    TextView resendotp;
    String sendotp;
    Timer mytimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        pinView=findViewById(R.id.pinview);
        Button verifybutton = findViewById(R.id.verifybuttonotp);
        TextView showmessage = findViewById(R.id.textView11);
        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        resendotp = findViewById(R.id.resendotp);
        sendotp= new DecimalFormat("0000").format(new Random().nextInt(9999));

        mytimer = new Timer();


        mytimer.schedule(new TimerTask() {
            @Override
            public void run() {

                timermethod();

            }
        },0,30000);

        timecounter(sendotp);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timecounter(sendotp);

            }
        });


        showmessage.setText("We will send you an One Time Password on this number +91- "+number);

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myotp=pinView.getText().toString();

                if (sendotp.equals(myotp))
                {
                    mytimer.cancel();

                    Toast.makeText(verify_OTP_Activity.this, "Verified", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(verify_OTP_Activity.this, userProfile_Activity.class));
                    overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                    finish();
                }

                else {
                    Toast.makeText(verify_OTP_Activity.this, "Please Enter the valid OTP ", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void timermethod()
    {
        this.runOnUiThread(time_click);
    }

    private Runnable time_click = new Runnable() {

        @Override
        public void run() {
            sendotp= new DecimalFormat("0000").format(new Random().nextInt(9999));
        }


    };


    void timecounter(String sendotp)
    {

        new CountDownTimer(30000,1000)
        {

            @Override
            public void onTick(long l) {


                NumberFormat format = new DecimalFormat("00");
                long sec = (l/1000) % 60;
                resendotp.setText("00 : "+format.format(sec));

                generatenotification(sendotp);

            }

            @Override
            public void onFinish() {
                resendotp.setText("Resend");

            }
        }.start();
    }

    void generatenotification(String otp)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("mych","My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"mych")
                .setSmallIcon(R.drawable.logo_symbol_colour)
                .setContentTitle("Meridean OTP Verify")
                .setContentText("One Time Password : "+otp);
        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,notification);
    }


}