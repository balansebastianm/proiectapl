package com.example.proiect.ui.activities.menu_doctor;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;
import com.example.proiect.ui.activities.singleton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import sql.DatabaseHelperDrugs;
import sql.DatabaseHelperPrescriptions;
import sql.DatabaseHelperUser;
import sql.Prescriptions;

public class newPrescription extends AppCompatActivity {
    Button buttonSend;
    EditText etUserID, etDrugID, etDrugNo, etDrugFr;
    int userID, doctorID;
    String drugID, drugNo, drugFr, drugName;
    String[] resultDrugsId, resultDrugsNo, resultDrugsFr;
    int lenResultDrugsId, lenResultDrugsNo, lenResultDrugsFr, drugPrice;
    final AppCompatActivity activity = newPrescription.this;
    Prescriptions r = new Prescriptions();
    DatabaseHelperUser dbu = new DatabaseHelperUser(activity);
    DatabaseHelperPrescriptions dbr = new DatabaseHelperPrescriptions(activity);
    DatabaseHelperDrugs dbd = new DatabaseHelperDrugs(activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prescription);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });
        buttonSend = findViewById(R.id.buttonSendPrescription);
        buttonSend.setOnClickListener(view -> {
            etUserID = findViewById(R.id.etUserPrescription);
            etDrugID = findViewById(R.id.etDrugsId);
            etDrugNo = findViewById(R.id.etDrugsNumber);
            etDrugFr = findViewById(R.id.etDrugsFrequency);

            drugID = etDrugID.getText().toString();
            drugNo = etDrugNo.getText().toString();
            drugFr = etDrugFr.getText().toString();

            userID = Integer.parseInt(etUserID.getText().toString());
            doctorID = Integer.parseInt(singleton.getInstance().getId());

            if(userID == doctorID){
                Toast.makeText(getApplicationContext(), "UserID can't be the same as DoctorID.", Toast.LENGTH_SHORT).show();
                return;
            }

            resultDrugsId = drugID.split("[,]", 0);
            resultDrugsNo = drugNo.split("[,]", 0);
            resultDrugsFr = drugFr.split("[,]", 0);

            lenResultDrugsId = resultDrugsId.length;
            lenResultDrugsNo = resultDrugsNo.length;
            lenResultDrugsFr = resultDrugsFr.length;

            if(lenResultDrugsId != lenResultDrugsNo || lenResultDrugsId != lenResultDrugsFr){
                Toast.makeText(getApplicationContext(), "Fields containing commas must have the same number of elements.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!dbu.checkAssignedDoctor(userID, doctorID)){
                Toast.makeText(getApplicationContext(), "User is not your patient", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean ok=true;
            for(int i=0; i<lenResultDrugsId; i++){
                if(!dbd.checkDrugExists(Integer.parseInt(resultDrugsId[i]))){
                    ok = false;
                }
            }
            if(!ok){
                Toast.makeText(getApplicationContext(), "One or more drug IDs are invalid", Toast.LENGTH_SHORT).show();
                return;
            }
            ok = true;
            for(int i=0; i<lenResultDrugsId-1; i++){
                for(int j=i+1; j<lenResultDrugsId; j++){
                    if(Integer.parseInt(resultDrugsId[i]) == Integer.parseInt(resultDrugsId[j])){
                        ok = false;
                    }
                }
            }
            if(!ok){
                Toast.makeText(getApplicationContext(), "IDs can't be the same.", Toast.LENGTH_SHORT).show();
                return;
            }
            int drugID, drugNO, drugFR;
            for(int i=0; i<lenResultDrugsFr; i++){
                drugID = Integer.parseInt(resultDrugsId[i]);
                drugNO = Integer.parseInt(resultDrugsNo[i]);
                drugFR = Integer.parseInt(resultDrugsFr[i]);
                drugName = dbd.getDrugName(drugID);
                drugPrice = dbd.getDrugPrice(drugID);
                r.setUser_id(userID);
                r.setDoctor_id(doctorID);
                r.setDrug_id(drugID);
                r.setDrug_no(drugNO);
                r.setDrug_fr(drugFR);
                r.setDrug_name(drugName);
                r.setDrug_price(drugPrice);
                dbr.addPrescription(r);

            }
            Toast.makeText(getApplicationContext(), "Prescription sent successfully.", Toast.LENGTH_SHORT).show();
            Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);


        });
    }
}