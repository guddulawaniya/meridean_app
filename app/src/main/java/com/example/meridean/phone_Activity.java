package com.example.meridean;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;

public class phone_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Button nextbutton = findViewById(R.id.nextbuttonforget);
        TextInputEditText mobiletext = findViewById(R.id.mobilenumberforgettext);
        TextInputLayout layout = findViewById(R.id.mobilenumberlayoutforget);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mobiletext.getText().toString().isEmpty())
                {

                    String sendotp= new DecimalFormat("0000").format(new Random().nextInt(9999));

                    sendotpnumbers(mobiletext.getText().toString(),sendotp);
                    Intent intent = new Intent(phone_Activity.this, verify_OTP_Activity.class);

                    intent.putExtra("number",mobiletext.getText().toString());
                    intent.putExtra("otp",sendotp);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                }
                else
                {
                    layout.setBoxStrokeColor(Color.RED);
                    layout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                    layout.setHelperText("please Enter Valid mobile number*");
                    layout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                    mobiletext.requestFocus();

                }
            }
        });
    }

    void sendotpnumbers(String mobile, String sendotp) {

        final String sms = "Hello ! The One Time Password " +
                "to login for Staff panel is " + sendotp + " This OTP will expire in 10 minutes Regards, Meridean Overseas Edu Con Pvt Ltd";

        String addurlsignup = "https://api.datagenit.com/sms?auth=D!~7113Zz8MHFw1mQ&senderid=MOECOE&msisdn=" + mobile + "&message=" + sms;


        class sendotp extends AsyncTask<String, String, String> {
            @Override
            protected void onPostExecute(String s) {

                try {
                    JSONObject obj = new JSONObject(s);
                    int status = obj.getInt("code");
                    if (status == 100) {

                        Toast.makeText(phone_Activity.this, "Please check your inbox", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(phone_Activity.this, "Unable to retrive any data on server", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();
    }
}