package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.proiect.R;
import com.example.proiect.ui.activities.meniuDoctor.addDrugs;
import com.example.proiect.ui.activities.meniuDoctor.listPatients;

public class doctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        ImageView menud = findViewById(R.id.menuButton);
        //meniu
        menud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenuDoctor = new PopupMenu(getApplicationContext(), menud);
                popupMenuDoctor.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.doctorAddDrugs){
                            Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
                            startActivity(activity2Intent);
                        }
                        else if(menuItem.getItemId() == R.id.doctorListPatients){
                            Intent activity2Intent = new Intent(getApplicationContext(), listPatients.class);
                            startActivity(activity2Intent);
                        }
                        else if(menuItem.getItemId() == R.id.doctorMenuDetails){
                            Intent activity2Intent = new Intent(getApplicationContext(), accountDetails.class);
                            startActivity(activity2Intent);
                        }
                        return false;
                    }
                });
                popupMenuDoctor.inflate(R.menu.menu_doctor);
                popupMenuDoctor.show();
            }
        });
    }
}