package com.example.managetution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout logemail, logpass;
    EditText email, pass;
    Button btnLogIn;
    TextView btnSignUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        logemail = (TextInputLayout)findViewById(R.id.layoutUser);
        logpass = (TextInputLayout) findViewById(R.id.layoutPass);
        btnLogIn = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.txtSignUp);
        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
                finish();
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String email = Objects.requireNonNull(logemail.getEditText()).getText().toString() ;
        String pass = Objects.requireNonNull(logpass.getEditText()).getText().toString();

        if(email.isEmpty()) {
            logemail.setError("Email is required");
            Toast.makeText(LoginActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (pass.isEmpty()) {
            logpass.setError("Password is required");
            Toast.makeText(LoginActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, Home_Guardian.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login UnSuccessful" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
    //@Override
   /* protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
    }*/
}
