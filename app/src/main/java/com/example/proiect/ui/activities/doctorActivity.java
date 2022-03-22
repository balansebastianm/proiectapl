package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.proiect.R;
import com.example.proiect.ui.activities.meniuDoctor.addDrugs;
import com.example.proiect.ui.activities.meniuDoctor.addPatient;
import com.example.proiect.ui.activities.meniuDoctor.retetaNoua;

import sql.DatabaseHelperDrugs;

public class doctorActivity extends AppCompatActivity {
    Button addDrugs, addPatient, trimiteReteta, detaliiCont, logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHelperDrugs db = new DatabaseHelperDrugs(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        addDrugs = findViewById(R.id.buttonAdaugaMedicamenteDoctor);
        addPatient = findViewById(R.id.buttonAdaugaPacientDoctor);
        trimiteReteta = findViewById(R.id.buttonTrimiteRetetaDoctor);
        detaliiCont = findViewById(R.id.buttonDetaliiContDoctor);
        logOut = findViewById(R.id.LogOutDoctor);
        addDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
                startActivity(activity2Intent);
            }
        });
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), addPatient.class);
                startActivity(activity2Intent);
            }
        });
        trimiteReteta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), retetaNoua.class);
                startActivity(activity2Intent);
            }
        });
        detaliiCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), accountDetails.class);
                startActivity(activity2Intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }
        });
    }
}