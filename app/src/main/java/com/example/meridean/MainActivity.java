package com.example.meridean;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    WebView webView;
    TextView offlinetext;
    AlertDialog alertDialog;
    boolean check;
    AlertDialog.Builder builder;
    Timer mytimer;
    SharedPreferences sharedPreferences;
    static final String SHARE_PREFS = "share_prefs";
    static final String EMAIL_KEY = "email_key";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);





        // find by ids

        webView = findViewById(R.id.webview);
        offlinetext = findViewById(R.id.offlinetext);
        progressBar = findViewById(R.id.progressBar);
        TextView loginbuttonontoolbar = findViewById(R.id.loginbuttononToolbar);
        ImageView profilemenuimage = findViewById(R.id.profilemenuimage);
        profilemenuimage.setVisibility(View.GONE);


        email = sharedPreferences.getString(EMAIL_KEY,null);
        if (email!=null)
        {
            loginbuttonontoolbar.setVisibility(View.GONE);
            profilemenuimage.setVisibility(View.VISIBLE);

        }


        WebSettings webSettings = webView.getSettings();
        builder= new AlertDialog.Builder(MainActivity.this);
        mytimer = new Timer();



        // alert box funation call
        alertbox();







        profilemenuimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Profile_Activity.class));
                overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                finish();
            }
        });








        loginbuttonontoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,login_Activity.class));
                overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                finish();


            }
        });


//        mytimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                timermethod();
//
//            }
//        },0,2000);



        //load url on webview


        webView.loadUrl("https://www.meridean.org/");
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.requestFocus();
        webView.getSettings().setLightTouchEnabled(true);


        //webview permissions
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setLoadWithOverviewMode (true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);


    }



    //alert box on mainActivity exit
    void alertbox()
    {
        builder.setMessage("Do you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.logo_symbol_colour);


        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            finish();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        alertDialog = builder.create();
    }





    //timer function automaticall redirect on offline Activity

    private void timermethod()
    {
        this.runOnUiThread(time_click);


    }
    private Runnable time_click = new Runnable() {


        @Override
        public void run() {
            if (!isConnected())
            {
                offlinetext.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mytimer.cancel();
                        check = false;
                        startActivity(new Intent(MainActivity.this,offline_Activity.class));
                        overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                        finish();

                    }
                },3000);
            }

        }
    };



    // check internet permissions

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




    // onback pressed function then click function and exit show alert box


    public void onBackPressed() {
        if(webView!= null && webView.canGoBack())
            webView.goBack();// if there is previous page open it
        else
        {
            alertDialog.show();

        }
    }



    // set progress bar on webview
    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                return true;
            }
            return false;

        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);


        }




        // webview on receiving errors

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            webView.clearView();
            webView.stopLoading();
            mytimer.cancel();

            Intent intent = new Intent(MainActivity.this,offline_Activity.class);
            intent.putExtra("id",3);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
            finish();

            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

}