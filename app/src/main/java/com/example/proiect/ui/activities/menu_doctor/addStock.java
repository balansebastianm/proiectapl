package com.example.proiect.ui.activities.menu_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proiect.R;

import sql.DatabaseHelperDrugs;

public class addStock extends AppCompatActivity {
    Button addButton;
    DatabaseHelperDrugs db = new DatabaseHelperDrugs(this);
    EditText etId, etQuantity;
    TextView error;
    int id, quantity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);

        });

        addButton = findViewById(R.id.buttonAddStock);
        error = findViewById(R.id.tvErrorAddStock);
        addButton.setOnClickListener(view -> {
            etId = findViewById(R.id.drugIdToChange);
            etQuantity = findViewById(R.id.stockValueNew);
            boolean success;
            try{
                id = Integer.parseInt(etId.getText().toString());
                quantity = Integer.parseInt(etQuantity.getText().toString());
                success = true;
            }catch(Exception ex){
                success = false;
                System.out.println("Invalid input.");
                error.setText("Invalid input.");
                error.setTextColor(0xffa61f16);
            }
            if(success){
                if(db.updateDrug(id, quantity)){
                    Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
                    activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity2Intent);
                    Toast.makeText(getApplicationContext(), "Stock updated successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    error.setText("Invalid ID");
                    error.setTextColor(0xffa61f16);
                }

            }
        });
    }
}