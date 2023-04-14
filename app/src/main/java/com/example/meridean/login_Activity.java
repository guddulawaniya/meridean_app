package com.example.meridean;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class login_Activity extends AppCompatActivity {



    String url = "https://demo.merideanoverseas.in/login.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        TextInputEditText emailidlg = findViewById(R.id.emailidlg);
        TextInputEditText passwordlg = findViewById(R.id.passwordlg);
        Button loginbutton = findViewById(R.id.loginbutton);
        TextView forgetlink = findViewById(R.id.forgetpasswordlg);
        TextView registrainlink = findViewById(R.id.registraionlink);
        TextInputLayout emaillayoutlg = findViewById(R.id.emaillayoutlg);
        TextInputLayout passlayoutlg = findViewById(R.id.passwordlayoutlg);


        builder = new AlertDialog.Builder(this);
        builder.setTitle("Login details");



        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext = emailidlg.getText().toString().trim();
                String passtext = passwordlg.getText().toString().trim();

                if (!emailtext.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailtext).matches()) {

                    if ( !(passtext.isEmpty()) && (passtext.length() <= 16)) {

                        if (isConnected()) {


                            logincode(emailtext, passtext);


                        } else {
                            Intent intent = new Intent(login_Activity.this, offline_Activity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);
                            finish();

                        }

                    }else if (passtext.length() > 16) {
                        int id = 1;
                        checkvalidation(passwordlg, passlayoutlg, "Password Length is very long", id);
                    } else if (passtext.isEmpty()) {
                        int id = 1;
                        checkvalidation(passwordlg, passlayoutlg, "Required*", id);
                    }
                }
                else if (emailtext.isEmpty())
                {
                    int id =2;
                    checkvalidation(emailidlg,emaillayoutlg,"Required*", id);
                }
                else {
                    Toast.makeText(login_Activity.this, "Enter valid Email Address !", Toast.LENGTH_SHORT).show();
                    int id=2;
                    checkvalidation(emailidlg,emaillayoutlg,"Please Enter valid Email Address", id);
                }
            }
        });

        forgetlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_Activity.this,forget_password.class));
                overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
            }
        });


        registrainlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_Activity.this,Signup_Actvity.class));
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                finish();

            }
        });

    }
    void checkvalidation(TextInputEditText text,TextInputLayout emaillayout,String helpertext,int id)
    {
        switch (id)
        {
            case 1:  emaillayout.setHelperText(helpertext);
                emaillayout.setCounterEnabled(true);
                emaillayout.setCounterMaxLength(16);
                break;
            case 2:emaillayout.setHelperText(helpertext);
                break;

        }

        emaillayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
        emaillayout.setBoxStrokeColor(Color.RED);
        emaillayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
        text.requestFocus();
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


    void logincode(String email,String password)

    {
        String addurl = url+"?email="+email+"&password="+password;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, addurl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = null;

                try {
                    JSONObject obj = new JSONObject(response);
                    name = obj.getString("status");




                }
                catch (Exception e)
                {
                    Toast.makeText(login_Activity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                intent.putExtra("data",name);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                finish();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login_Activity.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }





}