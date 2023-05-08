package com.example.meridean;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;

public class Signup_Actvity extends AppCompatActivity {

    TextInputEditText emailid,username,password,mobilenumber;
    TextInputLayout namelayout,emaillayout,passlayout,mobilenumberlayout;
    Button createbutton;



    //some runtime permissions
    private final int REQ_CODE_PERMISSION_READ_SMS = 122;






    // Registration API url
    String BASE_URL = "https://demo.merideanoverseas.in/registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);


        //globally find by ids

        username = findViewById(R.id.username);
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        mobilenumber = findViewById(R.id.mobilenumber);
        createbutton = findViewById(R.id.verifybutton);

        // layout ids

        namelayout = findViewById(R.id.yournamelayout);
        emaillayout = findViewById(R.id.emaillayoutsp);
        passlayout = findViewById(R.id.passlayoutsp);
        mobilenumberlayout = findViewById(R.id.mobilenumberlayout);



        //local find by ids

        TextView loginlink = findViewById(R.id.loginlink);
        TextView continuemobilenumber = findViewById(R.id.continuemobilenumber);



        // internet instances
        InternetConnection nt = new InternetConnection(getApplicationContext());


        // continue with mobile number  button

        continuemobilenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Actvity.this, phone_Activity.class));
                overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);

            }
        });



        ontextwatchError();


        // Registration button

        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the data in string from edit text fields

                String name = username.getText().toString().trim();
                String email = emailid.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String mobileno = mobilenumber.getText().toString().trim();


                if (!name.isEmpty() && !(email.isEmpty()) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && !(pass.isEmpty())&& (pass.length() <= 16) &&
                        !mobileno.isEmpty() && nt.isConnected()) {

                    // registration function call and pass some paramters

                    RegistrationAPI(name, email, mobileno, pass);


                }
                else if(nt.isConnected()==false) {

                    //check internet connection of false

                    Intent intent = new Intent(Signup_Actvity.this, offline_Activity.class);
                    overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);
                    startActivity(intent);
                    finish();
                }
                else if (name.isEmpty()) {

                    namelayout.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    namelayout.setError("Required");

                    username.requestFocus();


                } else if (email.isEmpty()) {
                    emaillayout.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    emaillayout.setError("Required");
                    emailid.requestFocus();

                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emaillayout.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    emaillayout.setError("Invalid Email Address");
                    emailid.requestFocus();

                } else if (mobileno.isEmpty()) {
                    mobilenumberlayout.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    mobilenumberlayout.setError("Required");
                    mobilenumber.requestFocus();


                } else if (pass.length() > 16) {

                    passlayout.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    passlayout.setError("Password Length is Long");
                    password.requestFocus();
                } else if (pass.isEmpty()) {
                    passlayout.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    passlayout.setError("Required");
                    password.requestFocus();

                }
            }
        });


        // redirect login page

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Actvity.this, login_Activity.class));
                overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);
                finish();

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent  data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {

            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        sendemailotp se = new sendemailotp(getApplicationContext(),"it3@meridean.org","0388");
        se.execute();
    }

    void RegistrationAPI(String name, String email, String mobile, String pass) {

        String sendotp = new DecimalFormat("0000").format(new Random().nextInt(9999));
            final String message = "Hello ! The One Time Password to login for Staff panel is " + sendotp + " This OTP will expire in 10 minutes Regards, Meridean Overseas Edu Con Pvt Ltd";

            String registrationURL = BASE_URL + "?name=" + name + "&email=" + email + "&phone=" + mobile + "&password=" + pass;
            String sendotpurl = "https://api.datagenit.com/sms?auth=D!~7113Zz8MHFw1mQ&senderid=MOECOE&msisdn=" + mobile + "&message=" + message;


            class registration extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {

                try {
                    JSONObject obj = new JSONObject(s);
                    int status = obj.getInt("status");
                    if (status == 0) {


                        sendotpnumbers sm = new sendotpnumbers(getApplicationContext());
                        sm.execute(sendotpurl);

                        sendemailotp se = new sendemailotp(getApplicationContext(),email,sendotp);
                        se.execute();



                        Intent intent = new Intent(Signup_Actvity.this, verify_OTP_Activity.class);
                        intent.putExtra("number", mobile);
                        intent.putExtra("otp", sendotp);

                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(Signup_Actvity.this, "Unable to retrive any data on server", Toast.LENGTH_SHORT).show();
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
        registration obj = new registration();
        obj.execute(registrationURL);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(mobilenumber.getText().toString(), null, "verify otp ", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    // send registration  data on server


    void ontextwatchError()
    {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0)
                {
                    namelayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        emailid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailid.getText().toString()).matches())
                {
                    emaillayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        mobilenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0)
                {
                    mobilenumberlayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0 && charSequence.length()<16)
                {
                    passlayout.setErrorEnabled(false);
                } else if (charSequence.length()>=16) {

                    passlayout.setErrorEnabled(true);
                    passlayout.setError("Password Length is Long");
                    passlayout.setCounterEnabled(true);
                    passlayout.setCounterMaxLength(16);
                    password.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}