package com.example.meridean;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class phone_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();
    }
}