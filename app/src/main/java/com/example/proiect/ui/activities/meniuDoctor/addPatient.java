package com.example.proiect.ui.activities.meniuDoctor;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;
import com.example.proiect.ui.activities.registerActivity;
import com.example.proiect.ui.activities.singleton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import sql.DatabaseHelperUser;

public class addPatient extends AppCompatActivity {

    Button buttonAdd;
    EditText etidUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);

            }
        });
        buttonAdd = findViewById(R.id.buttonAddPatient);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppCompatActivity activity = addPatient.this;
                DatabaseHelperUser db = new DatabaseHelperUser(activity);
                etidUser = findViewById(R.id.etidUserToAssign);
                int idUser = Integer.parseInt(etidUser.getText().toString());
                int idDoctor = Integer.parseInt(singleton.getInstance().getId());

                if(idUser == idDoctor){
                    Toast.makeText(getApplicationContext(), "ID-ul userului nu poate fi acelasi cu ID-ul doctorului.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(db.checkAssignedDoctor(idUser, idDoctor)){
                    Toast.makeText(getApplicationContext(), "Userul este deja pacientul dvs. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(db.checkIfHasDoctor(idUser, idDoctor)){
                    Toast.makeText(getApplicationContext(), "Userul este deja pacientul altcuiva", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(db.assignDoctor(idUser, idDoctor)){
                    Toast.makeText(getApplicationContext(), "Pacient adaugat cu succes", Toast.LENGTH_SHORT).show();
                    Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
                    activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity2Intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Utilizatorul nu a fost gasit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}