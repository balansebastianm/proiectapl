package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.*;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.Console;

import sql.DatabaseHelper;
import sql.User;

public class registerActivity extends AppCompatActivity {
    User user = new User();
    TextView tvRegistered, tvRegistered2;
    String strUsername, strPassword,  strEmail, strPrenume,  strNume;
    EditText etNume, etPrenume, etEmail, etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = (Button)findViewById(R.id.buttonRegisterRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
                checkInput();
            }
        });

    }
    private final AppCompatActivity activity = registerActivity.this;
    DatabaseHelper databaseHelper = new DatabaseHelper(activity);
    private NestedScrollView nestedScrollView;


public void init(){
     tvRegistered = (TextView) findViewById(R.id.textViewRegister);
     etNume = (EditText) findViewById(R.id.editTextNume);
     etPrenume = (EditText) findViewById(R.id.editTextPrenume);
     etEmail = (EditText) findViewById(R.id.editTextEmail);
     etUsername = (EditText) findViewById(R.id.editTextUsernameRegister);
     etPassword = (EditText) findViewById(R.id.editTextPasswordRegister);

     strNume = etNume.getText().toString();
     strPrenume = etPrenume.getText().toString();
     strEmail = etEmail.getText().toString();
     strUsername = etUsername.getText().toString();
     strPassword = etPassword.getText().toString();
}

    private int inputValidation(){
        int a = 0;
        if(TextUtils.isEmpty(strNume)) {
            etNume.setError("Empty field");
            a = 1;
        }
        if(TextUtils.isEmpty(strPrenume)) {
            etPrenume.setError("Empty field");
            a = 1;
        }
        if(TextUtils.isEmpty(strEmail)) {
            etEmail.setError("Empty field");
            a = 1;
        }
        if(TextUtils.isEmpty(strUsername)) {
            etUsername.setError("Empty field");
            a = 1;
        }
        if(TextUtils.isEmpty(strPassword)) {
            etPassword.setError("Empty field");
            a = 1;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()){
            System.out.println(etEmail.getText().toString().trim());
            tvRegistered.setTextColor(0xffa61f16);
            tvRegistered.setText("Email invalid.");
            a += 2;
        }
        System.out.println(a);
        return a;
    }

    private void checkInput(){
        if(inputValidation()==0){
            if(!databaseHelper.checkUser(etUsername.getText().toString().trim(), etEmail.getText().toString().trim())){

                user.setNume(etNume.getText().toString().trim());
                user.setPrenume(etPrenume.getText().toString().trim());
                user.setEmail(etEmail.getText().toString().trim());
                user.setUsername(etUsername.getText().toString().trim());
                user.setPassword(etPassword.getText().toString().trim());
                CheckBox chk = (CheckBox) findViewById(R.id.checkBox);
                if(chk.isChecked()){

                    user.setDoctor(1);
                }
                else{

                    user.setDoctor(0);
                }
                databaseHelper.addUser(user);



                System.out.println("Cont creat");
                tvRegistered.setTextColor(0xFF3700B3);
                tvRegistered.setText("Contul a fost creat cu succes.");
                emptyFields();
            }else{
                System.out.println("Email + username");
                tvRegistered.setTextColor(0xffa61f16);
                tvRegistered.setText("Email sau username existent.");

            }

            }
        else if (inputValidation()==1){
            tvRegistered.setTextColor(0xffa61f16);
            tvRegistered.setText("Completati toate campurile.");
        }
    }
    public void emptyFields(){
        etNume.setText(null);
        etPrenume.setText(null);
        etEmail.setText(null);
        etUsername.setText(null);
        etPassword.setText(null);
    }
}