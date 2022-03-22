package com.example.proiect.ui.activities.meniuUser;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect.R;
import com.example.proiect.ui.activities.userActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import sql.DatabaseHelperComenzi;

public class istoricComenzi extends AppCompatActivity {
    DatabaseHelperComenzi db = new DatabaseHelperComenzi(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_comenzi);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }
        });
        ListView lvIstoric = findViewById(R.id.lvIstoric);
        SimpleCursorAdapter simpleCursorAdapter = db.populateListViewFromDB();
        lvIstoric.setAdapter(simpleCursorAdapter);
    }
}