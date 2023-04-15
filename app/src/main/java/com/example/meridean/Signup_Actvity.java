package com.example.meridean;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class Signup_Actvity extends AppCompatActivity {

    TextInputEditText emailid;
    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText mobilenumber;


    String url = "https://demo.merideanoverseas.in/registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);


        username = findViewById(R.id.username);
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        mobilenumber = findViewById(R.id.mobilenumber);
        TextView loginlink = findViewById(R.id.loginlink);
        Button createbutton = findViewById(R.id.verifybutton);
        TextView continuemobilenumber = findViewById(R.id.continuemobilenumber);

        TextInputLayout namelayout = findViewById(R.id.yournamelayout);
        TextInputLayout emaillayout = findViewById(R.id.emaillayoutsp);
        TextInputLayout passlayout = findViewById(R.id.passlayoutsp);
        TextInputLayout mobilenumberlayout = findViewById(R.id.mobilenumberlayout);


        continuemobilenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Actvity.this, phone_Activity.class));
                overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);

            }
        });

        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = username.getText().toString().trim();
                String email = emailid.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String mobileno = mobilenumber.getText().toString().trim();



                if (!name.isEmpty()&&
                        !(email.isEmpty()) && !(pass.isEmpty()) && (pass.length() <= 16) &&
                        !mobileno.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    {
                        if (isConnected()) {



                            RegistrationAPI(name, email,mobileno, pass);



                        } else {
                            Intent intent = new Intent(Signup_Actvity.this, offline_Activity.class);
                            overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else
                    {
                        int id = 2;
                        signupcheckvalidation(emailid, emaillayout, "Please Enter valid Email id ", id);
                    }




                } else if (name.isEmpty()) {

                    int id = 2;
                    signupcheckvalidation(username, namelayout, "Required", id);

                } else if (email.isEmpty()) {
                    int id = 2;
                    signupcheckvalidation(emailid, emaillayout, "Required", id);

                } else if (mobileno.isEmpty()) {
                    int id = 2;
                    signupcheckvalidation(mobilenumber, mobilenumberlayout, "Required", id);

                } else if (pass.length() > 16) {
                    int id = 1;
                    signupcheckvalidation(password, passlayout, "Password Length is very long", id);
                } else if (pass.isEmpty()) {
                    int id = 1;
                    signupcheckvalidation(password, passlayout, "Required", id);

                }
            }
        });


        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Actvity.this, login_Activity.class));
                overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);
                finish();

            }
        });

    }


    void signupcheckvalidation(TextInputEditText text, TextInputLayout emaillayout, String helpertext, int id) {
        switch (id) {
            case 1:
                emaillayout.setHelperText(helpertext);
                emaillayout.setCounterEnabled(true);
                emaillayout.setCounterMaxLength(16);
                break;
            case 2:
                emaillayout.setHelperText(helpertext);
                break;

        }

        emaillayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
        emaillayout.setBoxStrokeColor(Color.RED);
        emaillayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
        text.requestFocus();
    }


    void RegistrationAPI(String name, String email,String mobile, String pass) {


        String addurlsignup = url + "?name=" + name + "&email=" + email + "&phone=" +mobile  + "&password=" + pass;


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
                    if (status==0)
                    {
                        Toast.makeText(Signup_Actvity.this, "User Successfully Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup_Actvity.this, verify_OTP_Activity.class));

                    }
                    else
                    {
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
        obj.execute(addurlsignup);

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