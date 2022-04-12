package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.proiect.R;

public class accountDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        ImageView back = findViewById(R.id.backArrowToolbar);
        if(Integer.parseInt(singleton.getInstance().getIsDoctor()) == 1){
            back.setOnClickListener(view -> {
                Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
                startActivity(activity2Intent);
            });
        }
        else{
            back.setOnClickListener(view -> {
                Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            });
        }

        TextView id, username, nume, prenume, email;
        id = findViewById(R.id.tvID);
        username = findViewById(R.id.tvUsername);
        nume = findViewById(R.id.tvLastName);
        prenume = findViewById(R.id.tvFirstName);
        email = findViewById(R.id.tvEmail);
        System.out.println("id:" + singleton.getInstance().getId());
        System.out.println("last name:" + singleton.getInstance().getLast_name());
        System.out.println("first name:" + singleton.getInstance().getFirst_name());
        System.out.println("username:" + singleton.getInstance().getUsername());
        System.out.println("email:" + singleton.getInstance().getEmail());
        id.setText(singleton.getInstance().getId());
        nume.setText(singleton.getInstance().getLast_name());
        prenume.setText(singleton.getInstance().getFirst_name());
        username.setText(singleton.getInstance().getUsername());
        email.setText(singleton.getInstance().getEmail());
    }
}