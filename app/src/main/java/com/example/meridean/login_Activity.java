package com.example.meridean;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
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
    SharedPreferences sharedPreferences;
    static final String SHARE_PREFS = "share_prefs";
    static final String EMAIL_KEY = "email_key";
    static  final String PASSWORD_KEY = "password_key";

    String email,password,name;
    TextView errorshowtext;


    TextInputEditText  emailidlg,passwordlg;
    TextInputLayout emaillayoutlg,passlayoutlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        emailidlg = findViewById(R.id.emailidlg);
        passwordlg = findViewById(R.id.passwordlg);
        Button loginbutton = findViewById(R.id.forgetbutton);
        TextView forgetlink = findViewById(R.id.forgetpasswordlg);
        TextView registrainlink = findViewById(R.id.registraionlink);
        emaillayoutlg = findViewById(R.id.emaillayoutlg);
        passlayoutlg = findViewById(R.id.passwordlayoutlg);

        errorshowtext = findViewById(R.id.errorshowtext);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Login details");
        sharedPreferences = getSharedPreferences(SHARE_PREFS,Context.MODE_PRIVATE);

        email = sharedPreferences.getString(EMAIL_KEY,null);
        password = sharedPreferences.getString(PASSWORD_KEY,null);
        name = sharedPreferences.getString("your_name",null);

        textwatherError();
        InternetConnection nt = new InternetConnection(getApplicationContext());



        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext = emailidlg.getText().toString().trim();
                String passtext = passwordlg.getText().toString().trim();

                if (!emailtext.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailtext).matches() && !(passtext.isEmpty()) && (passtext.length() <= 16) && nt.isConnected() ) {

                        logincode(emailtext, passtext);

                }
                else if(!nt.isConnected())
                {
                    Intent intent = new Intent(login_Activity.this, offline_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);
                    finish();
                }
                else if (emailtext.isEmpty())
                {
                    emaillayoutlg.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    emaillayoutlg.setError("Required*");
                    emailidlg.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailtext).matches())
                {
                    emaillayoutlg.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    emaillayoutlg.setError("Invalid Email Address");
                    emailidlg.requestFocus();
                }
                else if (passtext.isEmpty()) {
                    passlayoutlg.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    passlayoutlg.setError("Required*");
                    passwordlg.requestFocus();
                }
                else if (passtext.length()>=16) {
                    passlayoutlg.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));

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

    void textwatherError()

    {
        emailidlg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                errorshowtext.setVisibility(View.INVISIBLE);

                if (Patterns.EMAIL_ADDRESS.matcher(emailidlg.getText().toString()).matches())
                {
                    emaillayoutlg.setErrorEnabled(false);
                } else if (emailidlg.getText().toString().length() > 0) {
                    emaillayoutlg.setError("Invalid Email Address");
                    emailidlg.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordlg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                errorshowtext.setVisibility(View.INVISIBLE);

             if (charSequence.length()>0 && charSequence.length()<=16)
             {
                 passlayoutlg.setErrorEnabled(false);
             }
             else if (charSequence.length()>=16)
             {
                 passlayoutlg.setError("Password Length is Long");
                 passlayoutlg.setCounterEnabled(true);
                 passlayoutlg.setCounterMaxLength(16);
                 passwordlg.requestFocus();

             }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    @Override
    public void onBackPressed() {

        overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStart() {

        if (email!=null && password!=null)
        {
            startActivity(new Intent(login_Activity.this, MainActivity.class));
            Toast.makeText(this, "Welcome Back "+name, Toast.LENGTH_LONG).show();
            finish();
        }
        super.onStart();
    }





    void logincode(String emailtext, String passwordtext)

    {
        String addurl = url+"?email="+emailtext+"&password="+passwordtext;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, addurl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    String status = obj.getString("status");

                    if (status.equals("True"))
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(EMAIL_KEY,emailtext);
                        editor.putString(PASSWORD_KEY,passwordtext);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), Profile_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                        finish();
                    }
                    else if (errorshowtext.getVisibility()==View.VISIBLE)

                          errorshowtext.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                      else
                          errorshowtext.setVisibility(View.VISIBLE);



                }
                catch (Exception e)
                {
                    Toast.makeText(login_Activity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

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