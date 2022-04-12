package com.example.proiect.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proiect.R;

import sql.DatabaseHelperUser;
import sql.User;

public class registerActivity extends AppCompatActivity {
    User user = new User();
    TextView tvRegistered;
    String strUsername, strPassword,  strEmail, strFirstName,  strLastName;
    EditText etLastName, etFirstName, etEmail, etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(activity2Intent);
        });

        Button register = findViewById(R.id.buttonRegisterRegister);
        register.setOnClickListener(view -> {
            init();
            checkInput();
        });

    }
    private final AppCompatActivity activity = registerActivity.this;
    DatabaseHelperUser databaseHelperUser = new DatabaseHelperUser(activity);


public void init(){
     tvRegistered = findViewById(R.id.textViewRegister);
     etLastName = findViewById(R.id.editTextLastName);
    etFirstName = findViewById(R.id.editTextFirstName);
     etEmail = findViewById(R.id.editTextEmail);
     etUsername = findViewById(R.id.editTextUsernameRegister);
     etPassword = findViewById(R.id.editTextPasswordRegister);

     strLastName = etLastName.getText().toString();
     strFirstName = etFirstName.getText().toString();
     strEmail = etEmail.getText().toString();
     strUsername = etUsername.getText().toString();
     strPassword = etPassword.getText().toString();
}

    @SuppressLint("SetTextI18n")
    private int inputValidation(){
        int a = 0;
        if(TextUtils.isEmpty(strLastName)) {
            etLastName.setError("Empty field");
            a = 1;
        }
        if(TextUtils.isEmpty(strFirstName)) {
            etFirstName.setError("Empty field");
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
            if(!databaseHelperUser.checkUser(etUsername.getText().toString().trim(), etEmail.getText().toString().trim()))  {

                user.setLast_name(etLastName.getText().toString().trim());
                user.setFirst_name(etFirstName.getText().toString().trim());
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
                databaseHelperUser.addUser(user);



                System.out.println("Account created");
                tvRegistered.setTextColor(0xFF3700B3);
                tvRegistered.setText("Account created successfully.");
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
                emptyFields();
            }else{
                System.out.println("Email + username");
                tvRegistered.setTextColor(0xffa61f16);
                tvRegistered.setText("Email or username already exists.");

            }

            }
        else if (inputValidation()==1){
            tvRegistered.setTextColor(0xffa61f16);
            tvRegistered.setText("Fill in all fields.");
        }
    }
    public void emptyFields(){
        etLastName.setText(null);
        etFirstName.setText(null);
        etEmail.setText(null);
        etUsername.setText(null);
        etPassword.setText(null);
    }
}