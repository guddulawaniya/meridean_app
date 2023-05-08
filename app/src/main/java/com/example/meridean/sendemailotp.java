package com.example.meridean;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
            //Adding subject
            String messages = "<div style=color:black;><h4>Hello ! The One Time Password to login for Staff panel is</h4></div><div style=color:red;><h4>" + sendotp + "</h4></div><div style=color:black;> This OTP will expire in 10 minutes Regards, Meridean Overseas Edu Con Pvt Ltd</div> ";

            messages+="<img src=https://www.meridean.org/frontend/images/about/our-team.webp width=200px/><br>";
            messages+="Email : <a href=https://mail.google.com/mail/u/0/#inbox?compose=new target=_blank>hr@meridean.org</a><br>";
            messages+="Text file : <a href=https://drive.google.com/file/d/1ckbOISSVDeBCaz652JiWVszR2_WE99pC/view?usp=share_link>Open click</a>";
            messages+="<a href=https://drive.google.com/file/d/1ckbOISSVDeBCaz652JiWVszR2_WE99pC/view?usp=share_link></a>";
            Multipart mp = new MimeMultipart();
            msg.setSubject("Verification Email");
            //Setting sender address
            msg.setFrom(new InternetAddress(config.EMAIL));
            msg.setSentDate(new Date());

            // Storing the data in file with name as geeksData.txt


            MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(messages, "text/html");



            mp.addBodyPart(htmlPart);

            msg.setContent(mp);

            //Sending email
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with attachment!!");


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;

    }


}
