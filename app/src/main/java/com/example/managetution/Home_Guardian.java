package com.example.managetution;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home_Guardian extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView btmNavView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guardian_home);
        loadFragments(new Home_Fragment());
        getSupportActionBar().hide();

        btmNavView = findViewById(R.id.bottom_Nav);
        btmNavView.setSelectedItemId(R.id.home_bottom_nav);
        btmNavView.setOnNavigationItemSelectedListener(this);


    }

    public  boolean loadFragments(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
        }
        return  true;
    }


    @Override
    public void onBackPressed() {
        if(btmNavView.getSelectedItemId() == R.id.home_bottom_nav){
            super.onBackPressed();
            finish();
        }
        else{
            btmNavView.setSelectedItemId(R.id.home_bottom_nav);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.notification_bottom_nav:
                fragment = new Notification_Fragment();
                item.setChecked(true);
                break;
            //break;

            case R.id.post_bottom_nav:
                fragment = new Post_Fragment();
                item.setChecked(true);
                break;

            case R.id.chat_bottom_nav:
                fragment = new Chat_Fragment();
                item.setChecked(true);
                break;

            case R.id.home_bottom_nav:
                fragment = new Home_Fragment();
                item.setChecked(true);
                break;
        }
        return loadFragments(fragment);
    }

}