package com.example.meridean;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;


public class verify_OTP_Activity extends AppCompatActivity {

    PinView pinView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        pinView=findViewById(R.id.pinview);
        Button verifybutton = findViewById(R.id.verifybuttonotp);
        TextView showmessage = findViewById(R.id.textView11);
        Intent intent = getIntent();
        String number;
        number = intent.getStringExtra("number");

        showmessage.setText("We will send you an One Time Password on this number +91- "+number);


        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp=pinView.getText().toString();

                if (!otp.isEmpty() && otp.length()==4)
                {

                    Toast.makeText(verify_OTP_Activity.this, otp, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(verify_OTP_Activity.this, comlete_profile_Activity.class));
                }

                else {
                    Toast.makeText(verify_OTP_Activity.this, "Please Enter the valid OTP ", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }


}