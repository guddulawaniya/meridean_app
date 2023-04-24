package com.example.meridean;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class comlete_profile_Activity extends AppCompatActivity {

    Calendar calendar;
    TextInputEditText dob,name,pincode,address,city,state;
    AutoCompleteTextView country;
    String url = "https://demo.merideanoverseas.in/registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        String[] arr = {"India","United Kingdom","Italy","New Zealand","Austrialia","Usa","Germany","Canada","Dubai","Mautirius","Poland","Latvia","Ireland","Europe","Malta"};

        calendar = Calendar.getInstance();


         dob = findViewById(R.id.savedobinput);
         country = findViewById(R.id.selectcountry);
        CheckBox checkBox = findViewById(R.id.checkbox);
        Button savebutton = findViewById(R.id.savebutton);
         name = findViewById(R.id.name);
         pincode = findViewById(R.id.areapincode);
         address = findViewById(R.id.address);
         city = findViewById(R.id.city);
         state = findViewById(R.id.state);
        TextView skiptext = findViewById(R.id.skiptext);
        skiptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(comlete_profile_Activity.this,MainActivity.class));
                overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                finish();

            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.select_dialog_item, arr);

        country.setThreshold(2);
        country.setAdapter(adapter);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectdate();

            }
        });




        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked())
                {
                    uploaddataonserver();
                    startActivity(new Intent(comlete_profile_Activity.this,MainActivity.class));
                    overridePendingTransition(R.anim.right_in_activity,R.anim.left_out_activity);
                    finish();
                }
                else
                {
                    Toast.makeText(comlete_profile_Activity.this, "Please Checked Box", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }

    void selectdate()
    {

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(

                comlete_profile_Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                },year, month, day);


        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void uploaddataonserver()
    {
        String nametext = name.getText().toString().trim();
        String dobtext = dob.getText().toString().trim();
        String pincodetext = pincode.getText().toString().trim();
        String addresstext = address.getText().toString().trim();
        String citytext = city.getText().toString().trim();
        String statetext = state.getText().toString().trim();
        String countrytext = country.getText().toString().trim();

        String customurl = url+"?name="+nametext+"&dobtext="+dobtext+"&pincode="+pincodetext+"&address="+addresstext+"&citytext="+citytext+"&state="+statetext+"&country="+countrytext;

        class uploaddata extends AsyncTask<String,String,String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject obj = new JSONObject();
                    int status = obj.getInt("status");

                    if (status==0)
                    {
                        Toast.makeText(comlete_profile_Activity.this, "data successfully uploaded in server", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(comlete_profile_Activity.this, "data not insert in server same technical issue", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception e) {
                   return e.getMessage();
                }

            }
        }
        uploaddata up = new uploaddata();
        up.execute(customurl);
    }
}