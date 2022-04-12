package com.example.proiect.ui.activities.menu_user;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect.R;
import com.example.proiect.ui.activities.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import sql.DatabaseHelperOrders;

public class orderHistory extends AppCompatActivity {
    DatabaseHelperOrders db = new DatabaseHelperOrders(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });
        ListView lvHistory = findViewById(R.id.lvHistory);
        SimpleCursorAdapter simpleCursorAdapter = db.populateListViewFromDB();
        lvHistory.setAdapter(simpleCursorAdapter);
    }
}