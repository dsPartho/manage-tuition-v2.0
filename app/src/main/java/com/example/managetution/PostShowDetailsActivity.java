package com.example.managetution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PostShowDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show_details);
    }

    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(this, Home_Guardian.class);
        startActivity(newIntent);
        finish();
    }
}