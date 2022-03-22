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
import com.example.proiect.ui.activities.meniuUser.istoricComenzi;
import com.example.proiect.ui.activities.meniuUser.orderDrugs;

public class userActivity extends AppCompatActivity {
    Button orderDrugs, accountDetails, istoricComenzi, logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        orderDrugs = findViewById(R.id.buttonComandaMedicamentePacient);
        accountDetails = findViewById(R.id.buttonDetaliiContUser);
        istoricComenzi = findViewById(R.id.buttonIstoricComenziUser);
        logOut = findViewById(R.id.buttonLogOutUser);
        orderDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), orderDrugs.class);
                startActivity(activity2Intent);
            }
        });
        accountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), accountDetails.class);
                startActivity(activity2Intent);
            }
        });
        istoricComenzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), istoricComenzi.class);
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