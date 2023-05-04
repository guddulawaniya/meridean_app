package com.example.meridean;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class sendemailotp extends AsyncTask<String, String, String> {

    private Context context;

    //Information to send email
    private String email;

    private String message;
    private ProgressDialog progressDialog;

    public sendemailotp(Context context,  String email, String message) {
        this.context = context;
        this.email = email;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        Toast.makeText(context, "please wait email sending..", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {

        //Showing a success message
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();


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
                        return new PasswordAuthentication(config.EMAIL,config.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(config.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject("Verification Email");
            //Adding message
            mm.setText(message);

            //Sending email
           Transport.send(mm);


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;


    }

}
