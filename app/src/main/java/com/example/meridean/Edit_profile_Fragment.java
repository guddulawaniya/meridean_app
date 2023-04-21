package com.example.meridean;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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

    String url = "#####";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        String[] arr = {"India","United Kingdom","Italy","New Zealand","Austrialia","Usa","Germany","Canada","Dubai","Mautirius","Poland","Latvia","Ireland","Europe","Malta"};


        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


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



        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),android.R.layout.select_dialog_item, arr);

        binding.selectcountryupdate.setThreshold(2);
        binding.selectcountryupdate.setAdapter(adapter);

        binding.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.updatecheckbox.isChecked()) getDialog().dismiss();
               else Toast.makeText(getContext(), "Please Checked Box", Toast.LENGTH_SHORT).show();




            }
        });

        return binding.getRoot();
    }

//    void uploaddataonserver()
//    {
//        String nametext = binding.updatename.getText().toString().trim();
//        String dobtext = binding.updatedob.getText().toString().trim();
//        String pincodetext = binding.updatepincode.getText().toString().trim();
//        String addresstext = binding.updateaddress.getText().toString().trim();
//        String citytext = binding.updatecity.getText().toString().trim();
//        String statetext = binding.updatestate.getText().toString().trim();
//        String countrytext = binding.selectcountryupdate.getText().toString().trim();
//
//        String customurl = url+"?name="+nametext+"&dobtext="+dobtext+"&pincode="+pincodetext+"&address="+addresstext+"&citytext="+citytext+"&state="+statetext+"&country="+countrytext;
//
//        class uploaddata extends AsyncTask<String,String,String> {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                try {
//
//                    JSONObject obj = new JSONObject();
//                    JSONArray users = obj.getJSONArray("status");
//                    String namel = users.getString("name");
//                    String dob = users.getString("dob");
//                    String pincode = users.getString("pincode");
//                    String address = users.getString("address");
//                    String city = users.getString("city");
//                    String state = users.getString("state");
//                    String country = users.getString("country");
//                    if (status==0)
//                    {
//                        Toast.makeText(getContext(), "data successfully uploaded in server", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                        Toast.makeText(getActivity(), "data not insert in server same technical issue", Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//                super.onPostExecute(s);
//            }
//
//            @Override
//            protected String doInBackground(String... strings) {
//                try {
//                    URL url = new URL(strings[0]);
//                    HttpURLConnection conn =(HttpURLConnection) url.openConnection();
//                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                    return br.readLine();
//                } catch (Exception e) {
//                    return e.getMessage();
//                }
//
//            }
//        }
//        uploaddata up = new uploaddata();
//        up.execute(customurl);
//    }

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