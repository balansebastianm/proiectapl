package com.example.proiect.ui.activities.menu_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.proiect.R;
import com.example.proiect.ui.activities.userActivity;

public class listDrugs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_drugs);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
            startActivity(activity2Intent);
        });
    }
}