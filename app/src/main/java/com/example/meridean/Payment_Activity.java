package com.example.meridean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Payment_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Payment_Activity.this, userProfile_Activity.class));
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();
        super.onBackPressed();
    }
}