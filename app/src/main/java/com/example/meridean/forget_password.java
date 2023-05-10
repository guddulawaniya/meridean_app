package com.example.meridean;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class forget_password extends AppCompatActivity {
    String forgetpassworURL = "https://demo.merideanoverseas.in/login.php";
    TextInputLayout forgetemaillayout;
    TextInputEditText forgetemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetemail = findViewById(R.id.forgetemail);
        forgetemaillayout = findViewById(R.id.forgetpasswordlayout);
        Button forgetbutton = findViewById(R.id.forgetbutton);

        forgetemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(forgetemail.getText().toString()).matches())
                {
                    forgetemaillayout.setErrorEnabled(false);

                } else if (forgetemail.getText().toString().length() > 0) {
                    forgetemaillayout.setError("Invalid Email Address");
                    forgetemail.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        InternetConnection nt = new InternetConnection(getApplicationContext());
        forgetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!forgetemail.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(forgetemail.getText().toString()).matches() && nt.isConnected())
                {
                    forgetpssword(forgetemail.getText().toString());
                }
                else
                {
                    forgetemaillayout.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                    forgetemaillayout.setError("Required*");
                    forgetemail.requestFocus();
                }



            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();


    }

    void forgetpssword(String emailtext)
    {

        String addurl = forgetpassworURL+"?email="+emailtext;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, addurl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    String status = obj.getString("status");

                    if (status.equals(true))
                    {
                        Toast.makeText(forget_password.this, "Link successfully send on mail", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {


                        forgetemaillayout.setBoxStrokeColor(Color.RED);
                        forgetemaillayout.setError("NO Record");
                        forgetemaillayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                        forgetemaillayout.startAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.shake_text));
                        forgetemail.setText("");
                        forgetemail.requestFocus();
                        Toast.makeText(forget_password.this, "NO Records", Toast.LENGTH_SHORT).show();
                    }


                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}