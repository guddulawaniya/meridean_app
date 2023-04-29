package com.example.meridean;

import android.app.Notification;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;



public class verify_OTP_Activity extends AppCompatActivity {

    PinView pinView;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;

    TextView resendotp;

    String sendotp;
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
        sendotp = intent.getStringExtra("otp");
        resendotp = findViewById(R.id.resendotp);

        timecounter();
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rdcheck)
                {
                    rdcheck=false;
                    sendotp= new DecimalFormat("0000").format(new Random().nextInt(9999));
                    timecounter();
                    sendotpnumbers(number,sendotp);

                }




            }
        });


        showmessage.setText("We will send you an One Time Password on this number +91- "+number);

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enterotpinboxs=pinView.getText().toString();

                if (sendotp.equals(enterotpinboxs))
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

    void sendotpnumbers(String mobile, String sendotp) {

        final String sms = "Hello ! The One Time Password " +
                "to login for Staff panel is "+sendotp+" This OTP will expire in 10 minutes Regards, Meridean Overseas Edu Con Pvt Ltd";

        String addurlsignup = "https://api.datagenit.com/sms?auth=D!~7113Zz8MHFw1mQ&senderid=MOECOE&msisdn="+mobile+"&message="+sms;


        class sendotp extends AsyncTask<String, String, String> {
            @Override
            protected void onPostExecute(String s) {

                try {
                    JSONObject obj = new JSONObject(s);
                    int status = obj.getInt("code");
                    if (status==100)
                    {

                        Toast.makeText(verify_OTP_Activity.this, " Again Please check your inbox", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(verify_OTP_Activity.this, "Unable to retrive any data on server", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... param) {


                try {
                    URL url = new URL(param[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception ex) {
                    return ex.getMessage();
                }

            }
        }
        sendotp obj = new sendotp();
        obj.execute(addurlsignup);

    }

//    void generatenotification(String otp)
//    {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("mych","My Channel", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"mych")
//                .setSmallIcon(R.drawable.logo_symbol_colour)
//                .setContentTitle("Meridean OTP Verify")
//                .setContentText("One Time Password : "+otp);
//        notification = builder.build();
//        notificationManagerCompat = NotificationManagerCompat.from(this);
//        notificationManagerCompat.notify(1,notification);
//    }


}