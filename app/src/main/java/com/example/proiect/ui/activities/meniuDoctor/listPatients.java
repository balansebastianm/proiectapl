package com.example.proiect.ui.activities.meniuDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;

public class listPatients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patients);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
                startActivity(activity2Intent);
            }
        });
    }
}