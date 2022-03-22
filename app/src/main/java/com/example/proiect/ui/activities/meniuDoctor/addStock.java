package com.example.proiect.ui.activities.meniuDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;

import sql.DatabaseHelperDrugs;

public class addStock extends AppCompatActivity {
    Button addButton;
    DatabaseHelperDrugs db = new DatabaseHelperDrugs(this);
    EditText etId, etCantitate;
    TextView eroare;
    int id, cantitate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);

            }
        });

        addButton = findViewById(R.id.buttonAddStock);
        eroare = findViewById(R.id.tvEroareAddStock);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etId = findViewById(R.id.drugIdToChange);
                etCantitate = findViewById(R.id.stockValueNew);
                boolean succes;
                try{
                    id = Integer.parseInt(etId.getText().toString());
                    cantitate = Integer.parseInt(etCantitate.getText().toString());
                    succes = true;
                }catch(Exception ex){
                    succes = false;
                    System.out.println("Input invalid");
                    eroare.setText("Input invalide");
                    eroare.setTextColor(0xffa61f16);
                }
                if(succes){
                    if(db.updateDrug(id, cantitate)){
                        Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
                        activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(activity2Intent);
                        Toast.makeText(getApplicationContext(), "Stock updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        eroare.setText("ID invalid");
                        eroare.setTextColor(0xffa61f16);
                    }

                }
            }
        });
    }
}