package com.example.managetution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SampleLogOut extends AppCompatActivity {
    Button button;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
                startActivity(new Intent(SampleLogOut.this, MainActivity.class));
                finish();
            }
        });
    }

    public void logOut()
    {
        mAuth.signOut();
    }
}
