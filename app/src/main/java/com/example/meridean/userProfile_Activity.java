package com.example.meridean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import java.util.Timer;
import java.util.TimerTask;

public class userProfile_Activity extends AppCompatActivity {

    int SELECT_IMAGE = 200;
    ImageView profile_image;
    SharedPreferences sharedPreferences;
    static final String SHARE_PREFE = "share_pre";

    static final String YOUR_NAME = "your_name";
    static final String EMAIL_ID = "email_id";
    static final String DOB = "dob";
    static final String GENDER = "gender";
    static final String CONTAXT_NO = "contactnumber";
    static final String PIN_CODE = "pincode";
    static final String ADDRESS = "address";
    static final String CITY = "city";
    static final String STATE = "state";
    static final String COUNTRY = "country";
    Timer mytimer;

    TextView studentname,dob,email,contactno,city,state,country,address,gender,pincode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        DialogFragment dialogFragment = new Edit_profile_Fragment();


        sharedPreferences = getSharedPreferences(SHARE_PREFE,MODE_PRIVATE);


        CardView changeimage = findViewById(R.id.changeimage);
        Button editprofilebutton = findViewById(R.id.profileeditbutton);

        ImageView profilebackbutton = findViewById(R.id.profileback_button);
        CardView paymentcard = findViewById(R.id.paymentcard);
        CardView notificationcard = findViewById(R.id.notificationcard);
        CardView settingcard = findViewById(R.id.settingcard);

         studentname = findViewById(R.id.studentname);
         dob = findViewById(R.id.setdob);
         email = findViewById(R.id.studentemail);
         contactno = findViewById(R.id.setcontactno);
         city = findViewById(R.id.setcity);
         state = findViewById(R.id.setstate);
         country = findViewById(R.id.setcountry);
         address = findViewById(R.id.setaddress);
         gender = findViewById(R.id.gender);
         pincode = findViewById(R.id.setpincode);

        timermethod();
        mytimer = new Timer();
        mytimer.schedule(new TimerTask() {
            @Override
            public void run() {

                timermethod();

            }
        },0,2000);


        paymentcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userProfile_Activity.this,Payment_Activity.class));
                overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
            }
        });

        notificationcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userProfile_Activity.this,Notification_Activity.class));
                overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);

            }
        });
        settingcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userProfile_Activity.this,Setting_Activity.class));
                overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                finish();
            }
        });






        profile_image = findViewById(R.id.profile_image);

        editprofilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialogFragment.show(getSupportFragmentManager(),"My Fragment ");


            }
        });

        profilebackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        changeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagechooser();
            }
        });


    }

    private void timermethod()
    {
        this.runOnUiThread(time_click);
    }

    private Runnable time_click = new Runnable() {

        @Override
        public void run() {

            studentname.setText(sharedPreferences.getString(YOUR_NAME,null));
            dob.setText(sharedPreferences.getString(DOB,null));
            email.setText(sharedPreferences.getString(EMAIL_ID,null));
            contactno.setText(sharedPreferences.getString(CONTAXT_NO,null));
            address.setText(sharedPreferences.getString(ADDRESS,null));
            city.setText(sharedPreferences.getString(CITY,null));
            state.setText(sharedPreferences.getString(STATE,null));
            country.setText(sharedPreferences.getString(COUNTRY,null));
            gender.setText(sharedPreferences.getString(GENDER,null));
            pincode.setText(sharedPreferences.getString(PIN_CODE,null));

            }


        };


    void  imagechooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),SELECT_IMAGE);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(userProfile_Activity.this,MainActivity.class));
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK)
        {
            if (requestCode == SELECT_IMAGE)
            {
                Uri selectimageuri = data.getData();
                if (selectimageuri!=null)
                {
                    profile_image.setImageURI(selectimageuri);
                }
            }
        }
    }
}