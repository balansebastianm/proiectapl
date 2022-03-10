package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sql.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private final AppCompatActivity activity = MainActivity.this;
    DatabaseHelper databaseHelper = new DatabaseHelper(activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerButton = (Button)findViewById(R.id.buttonRegisterLogin);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Button pressed");
                Intent activity2Intent = new Intent(getApplicationContext(), registerActivity.class);
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

            if(databaseHelper.checkUserLogin(etUsername.getText().toString().trim(), etPassword.getText().toString().trim())){
                System.out.println("SUCCES");
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

}