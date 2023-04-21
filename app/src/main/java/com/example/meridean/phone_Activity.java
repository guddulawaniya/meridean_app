package com.example.meridean;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
                    Intent intent = new Intent(phone_Activity.this, verify_OTP_Activity.class);
                    intent.putExtra("number",mobiletext.getText().toString());
                    startActivity(intent);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();
    }
}