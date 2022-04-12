package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.proiect.R;
import com.example.proiect.ui.activities.menu_doctor.addDrugs;
import com.example.proiect.ui.activities.menu_doctor.addPatient;
import com.example.proiect.ui.activities.menu_doctor.newPrescription;


public class doctorActivity extends AppCompatActivity {
    Button addDrugs, addPatient, sendPrescription, accountDetails, logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        addDrugs = findViewById(R.id.buttonAddDrugsDoctor);
        addPatient = findViewById(R.id.buttonAddPatientDoctor);
        sendPrescription = findViewById(R.id.buttonSendPrescriptionDoctor);
        accountDetails = findViewById(R.id.buttonAccountDetailsDoctor);
        logOut = findViewById(R.id.LogOutDoctor);
        addDrugs.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
            startActivity(activity2Intent);
        });
        addPatient.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), addPatient.class);
            startActivity(activity2Intent);
        });
        sendPrescription.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), newPrescription.class);
            startActivity(activity2Intent);
        });
        accountDetails.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), accountDetails.class);
            startActivity(activity2Intent);
        });
        logOut.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });
    }
}