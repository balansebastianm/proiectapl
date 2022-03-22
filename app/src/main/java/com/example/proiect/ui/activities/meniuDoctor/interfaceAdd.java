package com.example.proiect.ui.activities.meniuDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;
import com.example.proiect.ui.activities.registerActivity;

import sql.DatabaseHelperDrugs;
import sql.DatabaseHelperUser;
import sql.Drugs;

public class interfaceAdd extends AppCompatActivity {

    Drugs drug = new Drugs();
    private final AppCompatActivity activity = interfaceAdd.this;
    DatabaseHelperDrugs db = new DatabaseHelperDrugs(activity);
    EditText etNumeMedicament, etDescriereMedicament, etCodMedicament, etStocMedicament, etPretMedicament;
    String numeMedicament, descriereMedicament, codMedicament;
    TextView tvEroare;
    int stocMedicament, pretMedicament;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_add);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);

            }
        });
        Button addDrug = findViewById(R.id.buttonAddDrug);
        addDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();

            }
        });
    }
    void init(){

        tvEroare = findViewById(R.id.tvEroareAddDrugs);
        etNumeMedicament = findViewById(R.id.textNumeMedicamentNou);
        etDescriereMedicament = findViewById(R.id.textDescriereMedicamentNou);
        etCodMedicament = findViewById(R.id.textCodMedicamentNou);
        etStocMedicament = findViewById(R.id.textStocMedicamentNou);
        etPretMedicament = findViewById(R.id.textPretMedicamentNou);

        numeMedicament = etNumeMedicament.getText().toString();
        descriereMedicament = etDescriereMedicament.getText().toString();
        codMedicament = etCodMedicament.getText().toString();
        try{
            stocMedicament = Integer.parseInt(etStocMedicament.getText().toString());
        } catch(Exception ex){
            System.out.println("Stoc empty");
        }
        try{
            pretMedicament = Integer.parseInt(etPretMedicament.getText().toString());
        } catch(Exception ex){
            System.out.println("Stoc empty");
        }



        if(TextUtils.isEmpty(numeMedicament) || TextUtils.isEmpty(descriereMedicament) || TextUtils.isEmpty(codMedicament) || TextUtils.isEmpty(etStocMedicament.getText()) || TextUtils.isEmpty(etPretMedicament.getText())){
            Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
        }
        else if(!db.checkCode(codMedicament)){
            Toast.makeText(getApplicationContext(), "Codul exista deja", Toast.LENGTH_SHORT).show();
        }
        else{
            drug.setNume(numeMedicament);
            drug.setDescriere(descriereMedicament);
            drug.setCod(codMedicament);
            drug.setPrice(pretMedicament);
            drug.setStoc(stocMedicament);
            db.addDrug(drug);
            Toast.makeText(getApplicationContext(), "Medicament adaugat cu succes", Toast.LENGTH_SHORT).show();
            System.out.println("Succes");
            Intent activity2Intent = new Intent(getApplicationContext(), addDrugs.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        }
    }
}