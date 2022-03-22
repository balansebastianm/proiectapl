package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proiect.R;

import sql.DatabaseHelperDrugs;
import sql.DatabaseHelperUser;

public class accountDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        ImageView back = findViewById(R.id.backArrowToolbar);
        if(Integer.parseInt(singleton.getInstance().getIsDoctor()) == 1){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
                    startActivity(activity2Intent);
                }
            });
        }
        else{
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                    activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity2Intent);
                }
            });
        }

        DatabaseHelperUser db = new DatabaseHelperUser(this);
        TextView id, username, nume, prenume, email;
        id = findViewById(R.id.tvid);
        username = findViewById(R.id.tvusername);
        nume = findViewById(R.id.tvnume);
        prenume = findViewById(R.id.tvprenume);
        email = findViewById(R.id.tvemail);
        System.out.println("id:" + singleton.getInstance().getId());
        System.out.println("nume:" + singleton.getInstance().getNume());
        System.out.println("prenume:" + singleton.getInstance().getPrenume());
        System.out.println("username:" + singleton.getInstance().getUsername());
        System.out.println("email:" + singleton.getInstance().getEmail());
        id.setText(singleton.getInstance().getId());
        nume.setText(singleton.getInstance().getNume());
        prenume.setText(singleton.getInstance().getPrenume());
        username.setText(singleton.getInstance().getUsername());
        email.setText(singleton.getInstance().getEmail());
    }
}