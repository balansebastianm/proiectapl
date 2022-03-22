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
import com.example.proiect.ui.activities.meniuUser.listDrugs;
import com.example.proiect.ui.activities.meniuUser.orderDrugs;

public class userActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ImageView menuB = findViewById(R.id.menuButton);
        //meniu
        menuB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), menuB);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.userMenuDrugs){
                            Intent activity2Intent = new Intent(getApplicationContext(), listDrugs.class);
                            startActivity(activity2Intent);
                        }
                        else if(menuItem.getItemId() == R.id.userMenuOrder){
                            Intent activity2Intent = new Intent(getApplicationContext(), orderDrugs.class);
                            startActivity(activity2Intent);
                        }
                        else if(menuItem.getItemId() == R.id.userMenuDetails){
                            Intent activity2Intent = new Intent(getApplicationContext(), accountDetails.class);
                            startActivity(activity2Intent);
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.menu_user);
                popupMenu.show();
            }
        });
    }
}