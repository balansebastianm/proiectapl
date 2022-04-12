package com.example.proiect.ui.activities.menu_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect.R;

import sql.DatabaseHelperDrugs;
import sql.Drugs;

public class interfaceAdd extends AppCompatActivity {

    Drugs drug = new Drugs();
    private final AppCompatActivity activity = interfaceAdd.this;
    DatabaseHelperDrugs db = new DatabaseHelperDrugs(activity);
    EditText etDrugName, etDrugDescription, etDrugCode, etDrugStock, etDrugPrice;
    String drugName, drugDescription, drugCode;
    TextView tvError;
    int drugStock, drugPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_add);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);

        });
        Button addDrug = findViewById(R.id.buttonAddDrug);
        addDrug.setOnClickListener(view -> init());
    }
    void init(){

        tvError = findViewById(R.id.tvNewDrugError);
        etDrugName = findViewById(R.id.etNewDrugName);
        etDrugDescription = findViewById(R.id.etNewDrugDescription);
        etDrugCode = findViewById(R.id.etNewDrugCode);
        etDrugStock = findViewById(R.id.etNewDrugStock);
        etDrugPrice = findViewById(R.id.etNewDrugPrice);

        drugName = etDrugName.getText().toString();
        drugDescription = etDrugDescription.getText().toString();
        drugCode = etDrugCode.getText().toString();
        try{
            drugStock = Integer.parseInt(etDrugStock.getText().toString());
        } catch(Exception ex){
            System.out.println("Stock empty");
        }
        try{
            drugPrice = Integer.parseInt(etDrugPrice.getText().toString());
        } catch(Exception ex){
            System.out.println("Stock empty");
        }



        if(TextUtils.isEmpty(drugName) || TextUtils.isEmpty(drugDescription) || TextUtils.isEmpty(drugCode) || TextUtils.isEmpty(etDrugStock.getText()) || TextUtils.isEmpty(etDrugPrice.getText())){
            Toast.makeText(getApplicationContext(), "Fill in all fields", Toast.LENGTH_SHORT).show();
        }
        else if(!db.checkCode(drugCode)){
            Toast.makeText(getApplicationContext(), "Drug code already exists.", Toast.LENGTH_SHORT).show();
        }
        else{
            drug.setName(drugName);
            drug.setDescription(drugDescription);
            drug.setCode(drugCode);
            drug.setPrice(drugPrice);
            drug.setStock(drugStock);
            db.addDrug(drug);
            Toast.makeText(getApplicationContext(), "Drug successfully added.", Toast.LENGTH_SHORT).show();
            System.out.println("Success");
            Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        }
    }
}