package com.example.meridean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Setting_Activity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    static final String SHARE_PREFS = "share_prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageView backbutton = findViewById(R.id.settingbackbutton);
        TextView logout = findViewById(R.id.logouttext);
        TextView changepasswordtext = findViewById(R.id.changepassword);
        LinearLayout changepasswordlinear = findViewById(R.id.passwordlinearlayout);
        Button changepasswordbutton = findViewById(R.id.changepasswordbutton);

        TextInputEditText currentpasstext = findViewById(R.id.currentpasstext);
        TextInputEditText newpasstext = findViewById(R.id.newmpasstext);
        TextInputEditText confirmpasstext = findViewById(R.id.confirmpasstext);

        TextInputLayout currentlayout = findViewById(R.id.currentpasstextlayout);
        TextInputLayout newlayout = findViewById(R.id.newmpasstextlayout);
        TextInputLayout confirmlayout = findViewById(R.id.confirmpasstextlayout);
        changepasswordtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changepasswordlinear.getVisibility()== View.VISIBLE)
                {
                    changepasswordlinear.setVisibility(View.GONE);
                }
                else
                {
                    changepasswordlinear.setVisibility(View.VISIBLE);

                }
            }
        });
        changepasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentpasstext.getText().toString().isEmpty() && !newpasstext.getText().toString().isEmpty() && !confirmpasstext.getText().toString().isEmpty())
                {
                    Toast.makeText(Setting_Activity.this, "Password Successfully Upadated", Toast.LENGTH_SHORT).show();

                    currentpasstext.setText("");
                    newpasstext.setText("");
                    confirmpasstext.setText("");
                    currentpasstext.requestFocus();
                }
                else if (currentpasstext.getText().toString().isEmpty())
                {
                    currentlayout.setError("Invalid Password");
                    currentpasstext.requestFocus();
                    currentlayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));


                }
                else if (newpasstext.getText().toString().isEmpty())
                {
                    newlayout.setError("Invalid Password");

                    newpasstext.requestFocus();
                    newlayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));

                }
                else
                {
                    confirmlayout.setError("Invalid Password");

                    confirmpasstext.requestFocus();
                    confirmlayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));

                }
            }
        });



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
        startActivity(new Intent(Setting_Activity.this, userProfile_Activity.class));
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();
        super.onBackPressed();
    }
}
