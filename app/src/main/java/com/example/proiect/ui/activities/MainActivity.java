package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proiect.R;

import sql.DatabaseHelperUser;

public class MainActivity extends AppCompatActivity {
    final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final AppCompatActivity activity = MainActivity.this;
    DatabaseHelperUser databaseHelperUser = new DatabaseHelperUser(activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyPermissions();


        Button registerButton = (Button)findViewById(R.id.buttonRegisterLogin);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Button pressed");
                Intent activity2Intent = new Intent(getApplicationContext(), registerActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }
        });
        Button loginButton = (Button)findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInput();
            }
        });
    }
    public void checkInput(){
        boolean invalid = false;
        EditText etUsername = (EditText) findViewById(R.id.editTextUsernameLogin);
        EditText etPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        String strUsername = etUsername.getText().toString();
        String strPassword = etPassword.getText().toString();
        if(TextUtils.isEmpty(strUsername)) {
            etUsername.setError("Empty field");
            invalid = true;
        }
        if(TextUtils.isEmpty(strPassword)) {
            etPassword.setError("Empty field");
            invalid = true;
        }
        if(!invalid){

            if(databaseHelperUser.checkUserLogin(etUsername.getText().toString().trim(), etPassword.getText().toString().trim())){
                System.out.println("SUCCES");
                if(databaseHelperUser.isDoctor(etUsername.getText().toString().trim(), etPassword.getText().toString().trim())){
                    Intent activity2Intent = new Intent(getApplicationContext(), doctorActivity.class);
                    activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity2Intent);
                }
                else{
                    Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                    activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(activity2Intent);
                }
            }
            else{
                System.out.println("Erorare");
                TextView tvl = (TextView) findViewById(R.id.textViewLogin);
                tvl.setTextColor(0xffa61f16);
                tvl.setText("Username sau parola gresite.");
        }
            invalid = false;
        }
    }


    void verifyPermissions(){
        int permissionRead = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionRead != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, 1);
        }
    }

}