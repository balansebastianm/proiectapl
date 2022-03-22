package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proiect.R;

import sql.DatabaseHelper;
import sql.User;

public class registerActivity extends AppCompatActivity {
    User user = new User();
    TextView tvRegistered;
    String strUsername, strPassword,  strEmail, strPrenume,  strNume;
    EditText etNume, etPrenume, etEmail, etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.buttonRegisterRegister);
        register.setOnClickListener(view -> {
            init();
            checkInput();
        });

    }
    private final AppCompatActivity activity = registerActivity.this;
    DatabaseHelper databaseHelper = new DatabaseHelper(activity);


public void init(){
     tvRegistered = findViewById(R.id.textViewRegister);
     etNume = findViewById(R.id.editTextNume);
     etPrenume = findViewById(R.id.editTextPrenume);
     etEmail = findViewById(R.id.editTextEmail);
     etUsername = findViewById(R.id.editTextUsernameRegister);
     etPassword = findViewById(R.id.editTextPasswordRegister);

     strNume = etNume.getText().toString();
     strPrenume = etPrenume.getText().toString();
     strEmail = etEmail.getText().toString();
     strUsername = etUsername.getText().toString();
     strPassword = etPassword.getText().toString();
}

    @SuppressLint("SetTextI18n")
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

    @SuppressLint("SetTextI18n")
    private void checkInput(){
        if(inputValidation()==0){
            if(!databaseHelper.checkUser(etUsername.getText().toString().trim(), etEmail.getText().toString().trim()))  {

                user.setNume(etNume.getText().toString().trim());
                user.setPrenume(etPrenume.getText().toString().trim());
                user.setEmail(etEmail.getText().toString().trim());
                user.setUsername(etUsername.getText().toString().trim());
                user.setPassword(etPassword.getText().toString().trim());
                CheckBox chk = findViewById(R.id.checkBox);
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
            tvRegistered.setText("Completati tot campurile.");
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