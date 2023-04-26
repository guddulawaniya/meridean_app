package com.example.meridean;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.meridean.databinding.FragmentEditProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class Edit_profile_Fragment extends DialogFragment {

    FragmentEditProfileBinding binding;
    static final String SHARE_PREFE = "share_pre";
    static final String YOUR_NAME = "your_name";
    static final String EMAIL_ID = "email_id";
    static final String DOB = "dob";
    static final String GENDER = "gender";
    static final String CONTAXT_NO = "contactnumber";
    static final String PIN_CODE = "pincode";
    static final String ADDRESS = "address";
    static final String CITY = "city";
    static final String STATE = "state";
    static final String COUNTRY = "country";

    SharedPreferences sharedPreferences;
    public static String yourname,updateemailid,dob,gender,contactno,pincode,address,city,state,country;

    String url = "https://demo.merideanoverseas.in/registration.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        String[] arr = {"India","United Kingdom","Italy","New Zealand","Austrialia","Usa","Germany","Canada","Dubai","Mautirius","Poland","Latvia","Ireland","Europe","Malta"};
        String[] genderlist = {"Male","Female","Others",};


        sharedPreferences = getActivity().getSharedPreferences(SHARE_PREFE, Context.MODE_PRIVATE);

        yourname = sharedPreferences.getString(YOUR_NAME,null);
        updateemailid = sharedPreferences.getString(EMAIL_ID,null);
        dob = sharedPreferences.getString(DOB,null);
        gender = sharedPreferences.getString(GENDER,null);
        contactno = sharedPreferences.getString(CONTAXT_NO,null);
        pincode = sharedPreferences.getString(PIN_CODE,null);
        address = sharedPreferences.getString(ADDRESS,null);
        city = sharedPreferences.getString(CITY,null);
        state = sharedPreferences.getString(STATE,null);
        country = sharedPreferences.getString(COUNTRY,null);


        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.updatename.setText(yourname);
        binding.updatedob.setText(dob);
        binding.updateemail.setText(updateemailid);
        binding.updatecontactnumber.setText(contactno);
        binding.updatepincode.setText(pincode);
        binding.updateaddress.setText(address);
        binding.updatecity.setText(city);
        binding.updatestate.setText(state);
        binding.selectcountryupdate.setText(country);
        binding.genderselect.setText(gender);




        binding.updatedob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                binding.updatedob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },year, month, day);


                datePickerDialog.show();
            }
        });

        binding.closefragmentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),android.R.layout.select_dialog_item, arr);
        ArrayAdapter<String> genderapadter = new ArrayAdapter(getContext(),android.R.layout.select_dialog_item, genderlist);

        binding.selectcountryupdate.setThreshold(2);
        binding.selectcountryupdate.setAdapter(adapter);
        binding.genderselect.setThreshold(2);
        binding.genderselect.setAdapter(genderapadter);

        binding.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nametext = binding.updatename.getText().toString().trim();
                String dobtext = binding.updatedob.getText().toString().trim();
                String emailtext = binding.updateemail.getText().toString().trim();
                String gendertext = binding.genderselect.getText().toString().trim();
                String contactnoupdate = binding.updatecontactnumber.getText().toString().trim();
                String pincodetext = binding.updatepincode.getText().toString().trim();
                String addresstext = binding.updateaddress.getText().toString().trim();
                String citytext = binding.updatecity.getText().toString().trim();
                String statetext = binding.updatestate.getText().toString().trim();
                String countrytext = binding.selectcountryupdate.getText().toString().trim();
                if (!nametext.isEmpty() && !dobtext.isEmpty() &&!gendertext.isEmpty() && ! emailtext.isEmpty())
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(YOUR_NAME,nametext);
                    editor.putString(DOB,dobtext);
                    editor.putString(EMAIL_ID,emailtext);
                    editor.putString(PIN_CODE,pincodetext);
                    editor.putString(CONTAXT_NO,contactnoupdate);
                    editor.putString(ADDRESS,addresstext);
                    editor.putString(CITY,citytext);
                    editor.putString(STATE,statetext);
                    editor.putString(GENDER,gendertext);
                    editor.putString(COUNTRY,countrytext);
                    editor.commit();

                    uploaddataonserver();

                }

               else Toast.makeText(getContext(), "Please Checked Box", Toast.LENGTH_SHORT).show();




            }
        });

        return binding.getRoot();
    }

    void uploaddataonserver()
    {
        String nametext = binding.updatename.getText().toString().trim();
        String dobtext = binding.updatedob.getText().toString().trim();
        String emailtext = binding.updateemail.getText().toString().trim();
        String gendertext = binding.genderselect.getText().toString().trim();
        String contactnoupdate = binding.updatecontactnumber.getText().toString().trim();
        String pincodetext = binding.updatepincode.getText().toString().trim();
        String addresstext = binding.updateaddress.getText().toString().trim();
        String citytext = binding.updatecity.getText().toString().trim();
        String statetext = binding.updatestate.getText().toString().trim();
        String countrytext = binding.selectcountryupdate.getText().toString().trim();


        String customurl = url+"?name="+nametext+"&dobtext="+dobtext+"&gender="+gendertext+"&email="+
                emailtext+"&contactno="+contactnoupdate+"&pincode="+pincodetext+"&address="+
                addresstext+"&citytext="+citytext+"&state="+statetext+"&country="+countrytext;

        class uploaddata extends AsyncTask<String,String,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                try {

                    JSONObject obj = new JSONObject(s);
                    int status = obj.getInt("status");
//                    String namel = users.getString("name");
//                    String dob = users.getString("dob");
//                    String pincode = users.getString("pincode");
//                    String address = users.getString("address");
//                    String city = users.getString("city");
//                    String state = users.getString("state");
//                    String country = users.getString("country");
                    if (status==0)
                    {
                        Toast.makeText(getContext(), "data successfully updated", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getDialog().dismiss();

                            }
                        },1000);
                    }
                    else
                        Toast.makeText(getActivity(), "data not insert in server same technical issue", Toast.LENGTH_SHORT).show();

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


    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
        getDialog().getWindow().setWindowAnimations(
                R.style.dialog_animation_fade);
    }
}