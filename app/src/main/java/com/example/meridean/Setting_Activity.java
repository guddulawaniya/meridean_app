package com.example.meridean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Setting_Activity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    static final String SHARE_PREFS = "share_prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageView backbutton = findViewById(R.id.settingbackbutton);
        TextView logout = findViewById(R.id.logouttext);

        sharedPreferences = getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(Setting_Activity.this, userProfile_Activity.class));
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
               finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(Setting_Activity.this,login_Activity.class));
                Toast.makeText(Setting_Activity.this, "User Successfully logout", Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
