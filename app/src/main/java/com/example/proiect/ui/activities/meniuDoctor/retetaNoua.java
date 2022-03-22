package com.example.proiect.ui.activities.meniuDoctor;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;
import com.example.proiect.ui.activities.singleton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import sql.DatabaseHelperDrugs;
import sql.DatabaseHelperRetete;
import sql.DatabaseHelperUser;
import sql.Retete;

public class retetaNoua extends AppCompatActivity {
    Button buttonSend;
    EditText etUserID, etMedicamenteID, etNrMedicamente, etFrecventaMedicamente;
    int userID, doctorID;
    String medicamenteID, nrMedicamente, frMedicamente, medicamentNume;
    String[] resultMedicamenteID, resultNrMedicamente, resultFrMedicamente;
    int lenresultMedicamenteID, lenresultNrMedicamente, lenresultFrMedicamente, medicamentPret;
    final AppCompatActivity activity = retetaNoua.this;
    Retete r = new Retete();
    DatabaseHelperUser dbu = new DatabaseHelperUser(activity);
    DatabaseHelperRetete dbr = new DatabaseHelperRetete(activity);
    DatabaseHelperDrugs dbd = new DatabaseHelperDrugs(activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reteta_noua);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }
        });
        buttonSend = findViewById(R.id.buttonTrimiteReteta);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUserID = findViewById(R.id.etUserReteta);
                etMedicamenteID = findViewById(R.id.etIdMedicamente);
                etNrMedicamente = findViewById(R.id.etNrMedicamente);
                etFrecventaMedicamente = findViewById(R.id.etFrecventaMedicamente);

                medicamenteID = etMedicamenteID.getText().toString();
                nrMedicamente = etNrMedicamente.getText().toString();
                frMedicamente = etFrecventaMedicamente.getText().toString();

                userID = Integer.parseInt(etUserID.getText().toString());
                doctorID = Integer.parseInt(singleton.getInstance().getId());

                if(userID == doctorID){
                    Toast.makeText(getApplicationContext(), "ID-ul userului nu poate fi acelasi cu ID-ul doctorului.", Toast.LENGTH_SHORT).show();
                    return;
                }

                resultMedicamenteID = medicamenteID.split("[,]", 0);
                resultNrMedicamente = nrMedicamente.split("[,]", 0);
                resultFrMedicamente = frMedicamente.split("[,]", 0);

                lenresultMedicamenteID = resultMedicamenteID.length;
                lenresultNrMedicamente = resultNrMedicamente.length;
                lenresultFrMedicamente = resultFrMedicamente.length;

                if(lenresultMedicamenteID!=lenresultNrMedicamente || lenresultMedicamenteID!=lenresultFrMedicamente || lenresultNrMedicamente!=lenresultFrMedicamente){
                    Toast.makeText(getApplicationContext(), "Campurile separate prin virgule trebuie sa aiba acelasi numar de elemente.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!dbu.checkAssignedDoctor(userID, doctorID)){
                    Toast.makeText(getApplicationContext(), "Userul nu este pacientul dvs.", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean ok=true;
                for(int i=0; i<lenresultMedicamenteID; i++){
                    if(!dbd.checkDrugExists(Integer.parseInt(resultMedicamenteID[i]))){
                        ok = false;
                    }
                }
                if(!ok){
                    Toast.makeText(getApplicationContext(), "Unul sau mai multe ID-uri medicament invalide.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ok = true;
                for(int i=0; i<lenresultMedicamenteID-1; i++){
                    for(int j=i+1; j<lenresultMedicamenteID; j++){
                        if(Integer.parseInt(resultMedicamenteID[i]) == Integer.parseInt(resultMedicamenteID[j])){
                            ok = false;
                        }
                    }
                }
                if(!ok){
                    Toast.makeText(getApplicationContext(), "ID-urile nu au voie sa se repete.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int medicamentID, medicamentNR, medicamentFR;
                for(int i=0; i<lenresultFrMedicamente; i++){
                    medicamentID = Integer.parseInt(resultMedicamenteID[i]);
                    medicamentNR = Integer.parseInt(resultNrMedicamente[i]);
                    medicamentFR = Integer.parseInt(resultFrMedicamente[i]);
                    medicamentNume = dbd.getDrugName(medicamentID);
                    medicamentPret = dbd.getDrugPrice(medicamentID);
                    r.setUser_id(userID);
                    r.setDoctor_id(doctorID);
                    r.setMedicament_id(medicamentID);
                    r.setMedicament_nr(medicamentNR);
                    r.setMedicament_fr(medicamentFR);
                    r.setMedicament_nume(medicamentNume);
                    r.setMedicament_pret(medicamentPret);
                    dbr.addReteta(r);
                    Toast.makeText(getApplicationContext(), "Reteta a fost trimisa cu succes.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}