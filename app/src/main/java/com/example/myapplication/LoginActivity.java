package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth autProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("login");

        editTextLoginEmail =findViewById(R.id.editText_login_email);
        editTextLoginPwd = findViewById(R.id.editText_login_pwd);
        progressBar = findViewById(R.id.progressBar);

        autProfile = FirebaseAuth.getInstance();

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPwd = editTextLoginPwd.getText().toString();
                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginActivity.this,"enter your email",Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("Email is required");
                    editTextLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LoginActivity.this, "re-enter your email", Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("valid email is required");
                    editTextLoginEmail.requestFocus();
                }else if (TextUtils.isEmpty(textPwd)){
                    Toast.makeText(LoginActivity.this,"enter your password",Toast.LENGTH_LONG).show();
                    editTextLoginPwd.setError("Password is required");
                    editTextLoginPwd.requestFocus();

                }else {
                    progressBar.setVisibility((View.VISIBLE));
                    loginUser(textEmail,textPwd);
                }

            }
        });



    }

    private void loginUser(String email, String Pwd) {
        autProfile.signInWithEmailAndPassword(email, Pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"you are logged in now",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,UserProfileActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"something went wrong",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        if (autProfile.getCurrentUser() != null ){
            Toast.makeText(LoginActivity.this ,"Already logged",Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this,UserProfileActivity.class));
            finish();

        }else {
            Toast.makeText(LoginActivity.this ,"you can login now",Toast.LENGTH_LONG).show();

        }
    }
}