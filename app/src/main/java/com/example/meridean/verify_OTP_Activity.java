package com.example.meridean;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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


public class verify_OTP_Activity extends AppCompatActivity {

    PinView pinView;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        pinView=findViewById(R.id.pinview);
        Button verifybutton = findViewById(R.id.verifybuttonotp);
        TextView showmessage = findViewById(R.id.textView11);
        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        String sendotp = intent.getStringExtra("otp");

        showmessage.setText("We will send you an One Time Password on this number +91- "+number);


        generatenotification(sendotp);
        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp=pinView.getText().toString();

                if (sendotp.equals(otp))
                {

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

    void generatenotification(String otp)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("mych","My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"mych")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("Otp Verify")
                .setContentText(""+otp);
        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,notification);
    }


}