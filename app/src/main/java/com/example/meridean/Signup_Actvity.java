package com.example.meridean;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Signup_Actvity extends AppCompatActivity {

    TextInputEditText emailid;
    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText mobilenumber;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;


    //some runtime permissions
    private final int REQ_CODE_PERMISSION_SEND_SMS = 121;
    private final int REQ_CODE_PERMISSION_READ_SMS = 122;
    private final int REQ_CODE_PERMISSION_RECEIVE_SMS = 123;


    Button createbutton;


    // Registration API
    String url = "https://demo.merideanoverseas.in/registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);


        //globally find by ids

        username = findViewById(R.id.username);
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        mobilenumber = findViewById(R.id.mobilenumber);
        createbutton = findViewById(R.id.verifybutton);


        //local find by ids

        TextView loginlink = findViewById(R.id.loginlink);
        TextView continuemobilenumber = findViewById(R.id.continuemobilenumber);
        TextInputLayout namelayout = findViewById(R.id.yournamelayout);
        TextInputLayout emaillayout = findViewById(R.id.emaillayoutsp);
        TextInputLayout passlayout = findViewById(R.id.passlayoutsp);
        TextInputLayout mobilenumberlayout = findViewById(R.id.mobilenumberlayout);


        // continue with mobile number  button

        continuemobilenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Actvity.this, phone_Activity.class));
                overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);

            }
        });


        // check run time permissions


        // sending sms permission

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            createbutton.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(Signup_Actvity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQ_CODE_PERMISSION_SEND_SMS);
        }


        // reading sms permission

        if (checkPermission(Manifest.permission.READ_SMS)) {
            createbutton.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(Signup_Actvity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQ_CODE_PERMISSION_SEND_SMS);
        }


        //***** ***** ***** Checking permission - Receive SMS ***** ***** *****

        if (!checkPermission(Manifest.permission.RECEIVE_SMS)) {
            ActivityCompat.requestPermissions(Signup_Actvity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    REQ_CODE_PERMISSION_RECEIVE_SMS);
        }


        // Registration button

        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the data in string from edit text fields

                String name = username.getText().toString().trim();
                String email = emailid.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String mobileno = mobilenumber.getText().toString().trim();


                if (!name.isEmpty() && !(email.isEmpty()) && !(pass.isEmpty()) && (pass.length() <= 16) &&
                        !mobileno.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        // internet connection check
                        if (isConnected()) {


                            // registration function call and pass some paramters

                            RegistrationAPI(name, email, mobileno, pass);


                        } else {

                            //check internet connection of false

                            Intent intent = new Intent(Signup_Actvity.this, offline_Activity.class);
                            overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        int id = 2;
                        signupcheckvalidation(emailid, emaillayout, "Please Enter valid Email id ", id);
                    }


                } else if (name.isEmpty()) {

                    int id = 2;
                    signupcheckvalidation(username, namelayout, "Required", id);

                } else if (email.isEmpty()) {
                    int id = 2;
                    signupcheckvalidation(emailid, emaillayout, "Required", id);

                } else if (mobileno.isEmpty()) {
                    int id = 2;
                    signupcheckvalidation(mobilenumber, mobilenumberlayout, "Required", id);

                } else if (pass.length() > 16) {
                    int id = 1;
                    signupcheckvalidation(password, passlayout, "Password Length is very long", id);
                } else if (pass.isEmpty()) {
                    int id = 1;
                    signupcheckvalidation(password, passlayout, "Required", id);

                }
            }
        });


        // redirect login page

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Actvity.this, login_Activity.class));
                overridePendingTransition(R.anim.right_in_activity, R.anim.left_out_activity);
                finish();

            }
        });

    }

    // check some runtime permissions

    private boolean checkPermission(String permission) {
        int permissionCode = ContextCompat.checkSelfPermission(this, permission);
        return permissionCode == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(mobilenumber.getText().toString(), null, "verify otp ", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    // show error designing

    void signupcheckvalidation(TextInputEditText text, TextInputLayout emaillayout, String helpertext, int id) {
        switch (id) {
            case 1:
                emaillayout.setHelperText(helpertext);
                emaillayout.setCounterEnabled(true);
                emaillayout.setCounterMaxLength(16);
                break;
            case 2:
                emaillayout.setHelperText(helpertext);
                break;

        }

        emaillayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
        emaillayout.setBoxStrokeColor(Color.RED);
        emaillayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
        text.requestFocus();
    }

    // send otp on email
    void sendemailOTP(String email, String sendotp) {

        String emailendurl = "####### email API ######";
        String EMAIL = "your-gmail-username";
        String PASSWORD = "your-gmail-password";

        class sendemailsms extends AsyncTask<String, String, String> {
            @Override
            protected void onPostExecute(String s) {

                Toast.makeText(getApplicationContext(), "Message Sent on your Mail", Toast.LENGTH_LONG).show();


            }

            @Override
            protected String doInBackground(String... param) {


                Properties props = new Properties();

                //Configuring properties for gmail
                //If you are not using gmail you may need to change the values
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                javax.mail.Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                            //Authenticating the password
                            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(EMAIL, PASSWORD);
                            }
                        });

                try {
                    //Creating MimeMessage object
                    MimeMessage mm = new MimeMessage(session);

                    //Setting sender address
                    mm.setFrom(new InternetAddress(EMAIL));
                    //Adding receiver
                    mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                    //Adding subject
                    mm.setSubject("Verification Mail");
                    //Adding message
                    mm.setText("message");

                    //Sending email
                    Transport.send(mm);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return null;


            }
        }
        sendemailsms obj = new sendemailsms();
        obj.execute(emailendurl);

    }


    // send otp on local sms
    void sendotpnumbers(String mobile, String sendotp) {

        final String sms = "Hello ! The One Time Password " +
                "to login for Staff panel is " + sendotp + " This OTP will expire in 10 minutes Regards, Meridean Overseas Edu Con Pvt Ltd";

        String addurlsignup = "https://api.datagenit.com/sms?auth=D!~7113Zz8MHFw1mQ&senderid=MOECOE&msisdn=" + mobile + "&message=" + sms;


        class sendotp extends AsyncTask<String, String, String> {
            @Override
            protected void onPostExecute(String s) {

                try {
                    JSONObject obj = new JSONObject(s);
                    int status = obj.getInt("code");
                    if (status == 100) {

                        Toast.makeText(Signup_Actvity.this, "Please check your inbox", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Signup_Actvity.this, "Unable to retrive any data on server", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... param) {


                try {
                    URL url = new URL(param[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception ex) {
                    return ex.getMessage();
                }

            }
        }
        sendotp obj = new sendotp();
        obj.execute(addurlsignup);

    }

    // send registration  data on server
    void RegistrationAPI(String name, String email, String mobile, String pass) {


        String addurlsignup = url + "?name=" + name + "&email=" + email + "&phone=" + mobile + "&password=" + pass;


        class registration extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {

                try {
                    JSONObject obj = new JSONObject(s);
                    int status = obj.getInt("status");
                    if (status == 0) {
                        String sendotp = new DecimalFormat("0000").format(new Random().nextInt(9999));
                        sendotpnumbers(mobile, sendotp);
//                        sendemailOTP(email,sendotp);


//                        generatenotification(sendotp);

                        Intent intent = new Intent(Signup_Actvity.this, verify_OTP_Activity.class);
                        intent.putExtra("number", mobile);
                        intent.putExtra("otp", sendotp);

                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(Signup_Actvity.this, "Unable to retrive any data on server", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... param) {


                try {
                    URL url = new URL(param[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception ex) {
                    return ex.getMessage();
                }

            }
        }
        registration obj = new registration();
        obj.execute(addurlsignup);
    }


        //send notification box in local box

//    void generatenotification(String otp)
//    {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("mych","My Channel", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"mych")
//                .setSmallIcon(R.drawable.logo_symbol_colour)
//                .setContentTitle("Meridean OTP Verify")
//                .setContentText("One Time Password : "+otp);
//        notification = builder.build();
//        notificationManagerCompat = NotificationManagerCompat.from(this);
//        notificationManagerCompat.notify(1,notification);
//    }


        // check internet connection

        public boolean isConnected () {
            boolean connected = false;
            try {
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nInfo = cm.getActiveNetworkInfo();
                connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                return connected;
            } catch (Exception e) {
                Log.e("Connectivity Exception", e.getMessage());
            }
            return connected;
        }

}