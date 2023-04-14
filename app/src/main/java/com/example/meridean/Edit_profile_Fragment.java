package com.example.meridean;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.meridean.databinding.FragmentEditProfileBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class Edit_profile_Fragment extends DialogFragment {

    FragmentEditProfileBinding binding;

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