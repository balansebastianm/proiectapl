package com.example.proiect.ui.activities.menu_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.proiect.R;
import com.example.proiect.ui.activities.userActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sql.DatabaseHelperPrescriptions;

public class orderDrugs extends AppCompatActivity {
    DatabaseHelperPrescriptions db = new DatabaseHelperPrescriptions(this);
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_drugs);

        ListView lvPrescriptions = findViewById(R.id.lvPrescriptions);
        SimpleCursorAdapter simpleCursorAdapter = db.populateListViewFromDB();
        lvPrescriptions.setAdapter(simpleCursorAdapter);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });
        fab = findViewById(R.id.fabOrder);
        fab.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), orderMenu.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });
    }
}