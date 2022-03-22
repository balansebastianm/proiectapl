package com.example.proiect.ui.activities.meniuUser;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect.R;
import com.example.proiect.ui.activities.doctorActivity;
import com.example.proiect.ui.activities.singleton;
import com.example.proiect.ui.activities.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sql.Comenzi;
import sql.DatabaseHelperComenzi;
import sql.DatabaseHelperDrugs;
import sql.DatabaseHelperRetete;

public class orderMenu extends AppCompatActivity {
    DatabaseHelperRetete db = new DatabaseHelperRetete(this);
    DatabaseHelperComenzi dbc = new DatabaseHelperComenzi(this);

    TextView pretTotal;
    EditText etNumeComplet, etAdresa, etCodPostal, etOras, etJudet;
    String nume, adresa, oras, judet;
    int codPostal, pretComanda, idPersoanaComanda;
    Button buttonOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), orderDrugs.class);
                startActivity(activity2Intent);
            }
        });
        if(!db.checkIsOrder(Integer.parseInt(singleton.getInstance().getId()))){
            Toast.makeText(getApplicationContext(), "Nu aveti ce comanda.", Toast.LENGTH_SHORT).show();
            Intent activity2Intent = new Intent(getApplicationContext(), orderDrugs.class);
            startActivity(activity2Intent);
            return;
        }

        pretTotal = findViewById(R.id.tvPretTotal);
        int pret = db.getTotalPrice(singleton.getInstance().getId());
        System.out.println(pret);
        pretTotal.setText(Integer.toString(pret));
        buttonOrder = findViewById(R.id.buttonComanda);
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNumeComplet = findViewById(R.id.etNumePrenumeComanda);
                etAdresa = findViewById(R.id.etAdresaComanda);
                etCodPostal = findViewById(R.id.etCodPostalComanda);
                etOras = findViewById(R.id.etOrasComanda);
                etJudet = findViewById(R.id.etJudetComanda);
                nume = etNumeComplet.getText().toString();
                adresa = etAdresa.getText().toString();
                try{
                    codPostal = Integer.parseInt(etCodPostal.getText().toString());
                    idPersoanaComanda = Integer.parseInt(singleton.getInstance().getId());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                oras = etOras.getText().toString();
                judet = etJudet.getText().toString();
                pretComanda = pret;

                inputValidation();

                if(!inputValidation()){
                    Toast.makeText(getApplicationContext(), "Completati toate campurile.", Toast.LENGTH_SHORT).show();
                }
                else if(!db.checkIsOrder(idPersoanaComanda)){
                    Toast.makeText(getApplicationContext(), "Nu aveti ce comanda.", Toast.LENGTH_SHORT).show();
                    Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                    activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity2Intent);
                }
                else{
                    Comenzi c = new Comenzi();
                    c.setIdUserComanda(idPersoanaComanda);
                    c.setNumeComplet(nume);
                    c.setAdresa(adresa);
                    c.setCodPostal(codPostal);
                    c.setOras(oras);
                    c.setJudet(judet);
                    c.setPretTotal(pretComanda);


                    if(db.deleteOrder(idPersoanaComanda)){
                        dbc.addOrder(c);
                        Toast.makeText(getApplicationContext(), "Comanda receptionata.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Stoc insuficient. Incearca mai tarziu.", Toast.LENGTH_SHORT).show();
                    }
                    Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                    activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity2Intent);
                }
            }
        });

    }

    public boolean inputValidation(){
        boolean a = true;
        if(TextUtils.isEmpty(nume)) {
            etNumeComplet.setError("Empty field");
            a = false;
        }
        if(TextUtils.isEmpty(adresa)) {
            etAdresa.setError("Empty field");
            a = false;
        }
        if(TextUtils.isEmpty(Integer.toString(codPostal))) {
            etCodPostal.setError("Empty field");
            a = false;
        }
        if(TextUtils.isEmpty(oras)) {
            etOras.setError("Empty field");
            a = false;
        }
        if(TextUtils.isEmpty(judet)) {
            etJudet.setError("Empty field");
            a = false;
        }
        return a;
    }
}