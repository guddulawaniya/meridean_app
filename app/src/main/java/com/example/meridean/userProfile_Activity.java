package com.example.meridean;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

public class userProfile_Activity extends AppCompatActivity {

    int SELECT_IMAGE = 200;
    ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        DialogFragment dialogFragment = new Edit_profile_Fragment();


        CardView changeimage = findViewById(R.id.changeimage);
        Button editprofilebutton = findViewById(R.id.profileeditbutton);

        ImageView profilebackbutton = findViewById(R.id.profileback_button);
        CardView paymentcard = findViewById(R.id.paymentcard);
        CardView notificationcard = findViewById(R.id.notificationcard);
        CardView settingcard = findViewById(R.id.settingcard);

        paymentcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userProfile_Activity.this,Payment_Activity.class));
            }
        });

        notificationcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userProfile_Activity.this,Notification_Activity.class));

            }
        });
        settingcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userProfile_Activity.this,Setting_Activity.class));
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