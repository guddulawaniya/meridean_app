package com.example.meridean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;



public class verify_OTP_Activity extends AppCompatActivity {

    PinView pinView;
    TextView resendotp;

    String sendotp;
    static final String SHARE_PREFE = "share_prefs";
    final String sms = "Hello ! The One Time Password " +
            "to login for Staff panel is "+sendotp+" This OTP will expire in 10 minutes Regards, Meridean Overseas Edu Con Pvt Ltd";

    String url = "https://api.datagenit.com/sms?auth=D!~7113Zz8MHFw1mQ&senderid=MOECOE&msisdn=";
    boolean rdcheck = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        pinView=findViewById(R.id.pinview);
        Button verifybutton = findViewById(R.id.verifybuttonotp);
        TextView showmessage = findViewById(R.id.textView11);
        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        String email = intent.getStringExtra("email");
        String pass = intent.getStringExtra("pass");
        String name = intent.getStringExtra("name");
        sendotp = intent.getStringExtra("otp");
        resendotp = findViewById(R.id.resendotp);

        InternetConnection nt = new InternetConnection(getApplicationContext());
        timecounter();
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rdcheck)
                {
                    rdcheck=false;
                    sendotp= new DecimalFormat("0000").format(new Random().nextInt(9999));
                    String s = url + number + "&message=" + sms;
                    timecounter();
                    sendotpnumbers sm = new sendotpnumbers(getApplication());
                    sm.execute(s);

                }




            }
        });


        showmessage.setText("We will send you an One Time Password on this number +91- "+number);

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enterotpinboxs=pinView.getText().toString();

                if (sendotp.equals(enterotpinboxs) && nt.isConnected())
                {
                    SharedPreferences.Editor editor = getSharedPreferences(SHARE_PREFE,MODE_PRIVATE).edit();
                    editor.putString("your_name",name);
                    editor.putString("email_key",email);
                    editor.putString("contactnumber",number);
                    editor.putString("password_key",pass);
                    editor.commit();
                    editor.apply();


                    Toast.makeText(verify_OTP_Activity.this, "Verified", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(verify_OTP_Activity.this, Profile_Activity.class));
                    overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                    finish();
                } else if (!nt.isConnected()) {
                    startActivity(new Intent(verify_OTP_Activity.this,offline_Activity.class));

                    overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                    finish();

                } else {
                    pinView.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    pinView.setLineColor(Color.RED);
                    Toast toast=   Toast.makeText(verify_OTP_Activity.this, "Please Enter the valid OTP ", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();

                        }
                    },1000);

                }

            }
        });



    }



    void timecounter()
    {

        new CountDownTimer(30000,1000)
        {

            @Override
            public void onTick(long l) {


                NumberFormat format = new DecimalFormat("00");
                long sec = (l/1000) % 60;
                resendotp.setText("00 : "+format.format(sec));



            }

            @Override
            public void onFinish() {
                resendotp.setText("Resend");
                rdcheck = true;


            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        super.onBackPressed();
    }


}