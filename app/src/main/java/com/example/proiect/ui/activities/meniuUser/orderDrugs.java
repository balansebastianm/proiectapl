package com.example.proiect.ui.activities.meniuUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;
import com.example.proiect.ui.activities.userActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sql.DatabaseHelperDrugs;
import sql.DatabaseHelperRetete;

public class orderDrugs extends AppCompatActivity {
    DatabaseHelperRetete db = new DatabaseHelperRetete(this);
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_drugs);

        ListView lvRetete = findViewById(R.id.lvRetete);
        SimpleCursorAdapter simpleCursorAdapter = db.populateListViewFromDB();
        lvRetete.setAdapter(simpleCursorAdapter);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }
        });
        fab = findViewById(R.id.fabOrder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), orderMenu.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }
        });
    }
}