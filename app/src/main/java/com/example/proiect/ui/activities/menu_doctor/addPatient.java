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

import sql.DatabaseHelperUser;

public class addPatient extends AppCompatActivity {

    Button buttonAdd;
    EditText etUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);

        });
        buttonAdd = findViewById(R.id.buttonAddPatient);
        buttonAdd.setOnClickListener(view -> {
            final AppCompatActivity activity = addPatient.this;
            DatabaseHelperUser db = new DatabaseHelperUser(activity);
            etUserID = findViewById(R.id.etidUserToAssign);
            int userID = Integer.parseInt(etUserID.getText().toString());
            int doctorID = Integer.parseInt(singleton.getInstance().getId());

            if(userID == doctorID){
                Toast.makeText(getApplicationContext(), "User ID can't be the same as Doctor ID.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(db.checkAssignedDoctor(userID, doctorID)){
                Toast.makeText(getApplicationContext(), "User already is your patient. ", Toast.LENGTH_SHORT).show();
                return;
            }
            if(db.checkIfHasDoctor(userID)){
                Toast.makeText(getApplicationContext(), "User is someone else's patient.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(db.assignDoctor(userID, doctorID)){
                Toast.makeText(getApplicationContext(), "Patient successfully added.", Toast.LENGTH_SHORT).show();
                Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }else{
                Toast.makeText(getApplicationContext(), "User not found.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}