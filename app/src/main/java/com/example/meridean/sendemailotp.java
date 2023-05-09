package com.example.meridean;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
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
            String messages = context.getString(R.string.templete_data);

          Uri uri = Uri.fromFile(new File("C:/Users/DESKTOP/AndroidStudioProjects/meridean_app/app/src/main/assets/templete_file.html"));

            File file = new File(String.valueOf(uri));


            messages+="<div class=container style=text-align: center; border: 2px solid black; width: 40%; margin: auto; padding: 16px;>" +
                    "    <img src=https://thumbs.dreamstime.com/z/environment-earth-day-hands-trees-growing-seedlings-bokeh-green-background-female-hand-holding-tree-nature-field-gra-130247647.jpg width=200px alt=nature style=max-width: 100px; border-radius: 10px; box-shadow: 0 0 10px;>" +
                    "    <div style=font-size: 18px; text-align: center;>" +
                    "        <h1 style=color:black>Title of Your Article</h1>" +
                    "        <p style=text-align: justify;>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum vero dicta adipisci consequatur Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum, repellat Lorem ipsum dolor sit amet consectetur adipisicing elit. Nulla recusandae repudiandae suscipit mollitia dolores esse labore blanditiis voluptatem culpa excepturi velit a qui, omnis ea dolorum distinctio, voluptate sunt at!</p>" +
                    "       <a href=https://www.dreamstime.com/environment-earth-day-hands-trees-growing-seedlings-bokeh-green-background-female-hand-holding-tree-nature-field-gra-image130247647> <button style=font-size: 18px; background-color: greenyellow; border: 2px solid green; margin: 1em 0;padding: 7px 16px; border-radius: 10px;>Sign UP</button></a>" +
                    "    </div>" +
                    "</div>";






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
    //            MimeBodyPart attachmentPart = new MimeBodyPart();
//            DataSource source = new FileDataSource(file);
//            attachmentPart.setDataHandler(new DataHandler(source));
//            attachmentPart.setFileName(new File(file.toURI()).getName());

//            messages +="file:///android_asset/templete_file.html";
//            AssetManager assetManager = context.getAssets();
//            InputStream in = assetManager.open("templete_file.html");
//            BufferedReader r = new BufferedReader(new InputStreamReader(in));
//            StringBuilder total = new StringBuilder();


    //messages = String.valueOf(Html.fromHtml(String.valueOf(R.string.templete_data)));

//            messages+="<img src=https://www.meridean.org/frontend/images/about/our-team.webp width=200px/><br>";
//            messages+="Email : <a href=https://mail.google.com/mail/u/0/#inbox?compose=new target=_blank>hr@meridean.org</a><br>";
//
//            messages+="Hello i, am here ";
//            messages+="Text file : <a href=https://drive.google.com/file/d/1ckbOISSVDeBCaz652JiWVszR2_WE99pC/view?usp=share_link>Open click</a>";




}
