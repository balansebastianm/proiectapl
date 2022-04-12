package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.proiect.R;
import com.example.proiect.ui.activities.menu_user.orderHistory;
import com.example.proiect.ui.activities.menu_user.orderDrugs;

public class userActivity extends AppCompatActivity {
    Button orderDrugs, accountDetails, orderHistory, logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        orderDrugs = findViewById(R.id.buttonOrderDrugsPatient);
        accountDetails = findViewById(R.id.buttonAccountDetailsPatient);
        orderHistory = findViewById(R.id.buttonOrderHistoryPatient);
        logOut = findViewById(R.id.buttonLogOutPatient);
        orderDrugs.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), orderDrugs.class);
            startActivity(activity2Intent);
        });
        accountDetails.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), accountDetails.class);
            startActivity(activity2Intent);
        });
        orderHistory.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), orderHistory.class);
            startActivity(activity2Intent);
        });
        logOut.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });
    }
}