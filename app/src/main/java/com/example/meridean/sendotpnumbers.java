package com.example.meridean;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public  class sendotpnumbers extends AsyncTask<String,String,String> {


    Context context;

    public sendotpnumbers(Context context) {
        this.context = context;
    }

    @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject obj = new JSONObject(s);
                int status = obj.getInt("code");
                if (status == 100) {

                    Toast.makeText(context, "Please check your inbox", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Unable to retrive any data on server", Toast.LENGTH_SHORT).show();
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