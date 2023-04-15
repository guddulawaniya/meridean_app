package com.example.meridean;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class verify_OTP_Activity extends AppCompatActivity {

    EditText num1,num2,num3,num4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        Button verifybutton = findViewById(R.id.verifybuttonotp);




        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!num1.getText().toString().isEmpty()&& !num2.getText().toString().isEmpty()
                        && !num3.getText().toString().isEmpty() && !num4.getText().toString().isEmpty())
                {
                    startActivity(new Intent(verify_OTP_Activity.this,comlete_profile_Activity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(verify_OTP_Activity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
        moveotpnumber();


    }
    private  void moveotpnumber()
    {
        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty())
                {
                    num2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty())
                {
                    num1.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty())
                {
                    num3.requestFocus();
                }
                else if(num2.getText().toString().isEmpty())
                {
                    num1.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().isEmpty())
                num2.requestFocus();


            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().trim().isEmpty())
                {
                    num2.requestFocus();
                    Toast.makeText(verify_OTP_Activity.this, "focus num2 ", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!num3.getText().toString().isEmpty())
                {
                    num4.requestFocus();
                    Toast.makeText(verify_OTP_Activity.this, "focus num4 ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!s.toString().trim().isEmpty())
                {
                    num3.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}