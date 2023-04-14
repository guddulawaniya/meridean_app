package com.example.meridean;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class comlete_profile_Activity extends AppCompatActivity {

    Calendar calendar;
    TextInputEditText dob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        String[] arr = {"India","United Kingdom","Italy","New Zealand","Austrialia","Usa","Germany","Canada","Dubai","Mautirius","Poland","Latvia","Ireland","Europe","Malta"};

        calendar = Calendar.getInstance();


         dob = findViewById(R.id.savedobinput);
        AutoCompleteTextView country = findViewById(R.id.selectcountry);
        CheckBox checkBox = findViewById(R.id.checkbox);
        Button savebutton = findViewById(R.id.savebutton);


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
                if (checkBox.isChecked()) startActivity(new Intent(comlete_profile_Activity.this,MainActivity.class));
                else Toast.makeText(comlete_profile_Activity.this, "Please Checked Box", Toast.LENGTH_SHORT).show();




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
}