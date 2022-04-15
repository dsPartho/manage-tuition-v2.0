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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout logemail, logpass;
    EditText email, pass;
    Button btnLogIn;
    String current_User_Id,user_role;
    DatabaseReference userDatabaseReference;
    TextView btnSignUp;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();

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
                    if(task.isSuccessful()  ){
                        current_User_Id = mAuth.getCurrentUser().getUid();
                        userDatabaseReference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("SignInData").child(current_User_Id);
                        userDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                SignInData signInDataInformation = snapshot.getValue(SignInData.class);
                                user_role = signInDataInformation.getRole();
                                System.out.println(user_role);
                                if((user_role.equals("Guardian"))){
                                    Toast.makeText(LoginActivity.this, "Guardian Login Successful", Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(getApplicationContext(), Home_Guardian.class));
                                    //Intent i = new Intent(LoginActivity.this, com.example.managetution.Home_Guardian.class);
                                    Intent i = new Intent(LoginActivity.this, com.example.managetution.Home_Guardian.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                    startActivity(i);
                                    finish();

                                }
                                if((user_role.equals("Tutor")) ){
                                    Toast.makeText(LoginActivity.this, "Tutor Login Successful", Toast.LENGTH_SHORT).show();
                                    // startActivity(new Intent(getApplicationContext(), ));
                                    //Intent i = new Intent(LoginActivity.this, com.example.managetution.Home_Tutor.class);
                                    Intent i = new Intent(LoginActivity.this, Home_Tutor.class);

                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                    startActivity(i);
                                    finish();

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });






                    }

                    else{
                        Toast.makeText(LoginActivity.this, "Login UnSuccessful" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
    /*
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),Home_Guardian.class));
            finish();
        }

    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
