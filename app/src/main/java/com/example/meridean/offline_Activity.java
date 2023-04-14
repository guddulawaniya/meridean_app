package com.example.meridean;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Timer;
import java.util.TimerTask;

public class offline_Activity extends AppCompatActivity {


    SwipeRefreshLayout swipeRefreshLayout;
    TextView onlineback;
    Timer mytimer;
    boolean check=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);


        mytimer = new Timer();
        mytimer.schedule(new TimerTask() {
            @Override
            public void run() {

                timermethod();

            }
        },0,2000);


        onlineback = findViewById(R.id.onlinebacktext);

        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                //your code on swipe refresh
                checkinternet();

            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.GRAY);


        Button trybutton = findViewById(R.id.trybutton);

        trybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkinternet();

            }
        });
    }


    void checkinternet()
    {
        if (isConnected()) {
            if (check)
            {
                mytimer.cancel();
                check = false;
               onBackPressed();
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                finish();
            }
            else
            {
                Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
            }




        } else {
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();

            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    private void timermethod()
    {
        this.runOnUiThread(time_click);
    }

    private Runnable time_click = new Runnable() {

        @Override
        public void run() {

            if (isConnected() && check)
            {


                check = false;
                mytimer.cancel();
                onlineback.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                       onBackPressed();
                        overridePendingTransition(R.anim.left_in,R.anim.right_out);
                        finish();

                    }
                },1000);

            }


        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

