package com.example.meridean;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class sendemailotp extends AsyncTask<String, String, String> {

    private Context context;

    //Information to send email
    private String email;

    private String sendotp;

    public sendemailotp(Context context,  String email, String sendotp) {
        this.context = context;
        this.email = email;
        this.sendotp = sendotp;
    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        // progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);

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
        props.put("mail.smtp.host", "smtp.hostinger.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        javax.mail.Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.EMAIL, config.PASSWORD);
                    }
                });


        try {
            //Creating MimeMessage object
            MimeMessage msg = new MimeMessage(session);

            //Adding receiver
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // sending templete on email

            InputStream inputStream = context.getAssets().open("templete_file.html");
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            String messages = new String(buffer);


            Multipart mp = new MimeMultipart();
            //Adding subject
            msg.setSubject("Verification Email");
            //Setting sender address
            msg.setFrom(new InternetAddress(config.EMAIL));
            msg.setSentDate(new Date());

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(messages, "text/html");

            // add multiple content data
            mp.addBodyPart(htmlPart);

            msg.setContent(mp);

            //Sending email
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with attachment!!");


        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
