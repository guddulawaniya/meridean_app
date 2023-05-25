package com.example.meridean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Notification_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Notification_Activity.this, Profile_Activity.class));
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();
        super.onBackPressed();
    }
}