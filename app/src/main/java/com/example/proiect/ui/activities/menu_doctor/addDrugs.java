package com.example.proiect.ui.activities.menu_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sql.DatabaseHelperDrugs;

public class addDrugs extends AppCompatActivity {
    DatabaseHelperDrugs db = new DatabaseHelperDrugs(this);


    Animation rotateOpen, rotateClose, fromBottom, toBottom;
    FloatingActionButton expand, addStock, addDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drugs);
        expand = findViewById(R.id.expand_button);
        addStock = findViewById(R.id.addStockExpanded);
        addDrug = findViewById(R.id.addDrugExpanded);

        ListView lvDrugs = findViewById(R.id.lvDrugs);
        SimpleCursorAdapter simpleCursorAdapter = db.populateListViewFromDB();
        lvDrugs.setAdapter(simpleCursorAdapter);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);

        });
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        expand.setOnClickListener(view -> onButtonClicked());
        addStock.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), addStock.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });
        addDrug.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), interfaceAdd.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });
        viewData();

    }
    public void viewData(){
        Cursor cursor = db.getDrugs();
        System.out.println("Entered view data");
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No drugs available.", Toast.LENGTH_SHORT).show();
        }
    }
    boolean clicked = false;


    private void onButtonClicked(){
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setAnimation(boolean clicked){
        if(!clicked){
            addStock.startAnimation(fromBottom);
            addDrug.startAnimation(fromBottom);
            expand.startAnimation(rotateOpen);
        }else{
            addStock.startAnimation(toBottom);
            addDrug.startAnimation(toBottom);
            expand.startAnimation(rotateClose);
        }
    }

    private void setVisibility(boolean clicked){
        if(!clicked){
            addStock.setVisibility(View.VISIBLE);
            addDrug.setVisibility(View.VISIBLE);
        }else{
            addStock.setVisibility(View.INVISIBLE);
            addStock.setVisibility(View.INVISIBLE);
        }
    }
}