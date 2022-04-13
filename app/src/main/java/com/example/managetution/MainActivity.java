package com.example.managetution;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView btmNavView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guardian_home);
        getActionBar().hide();
        loadFragments(new Home_Fragment());

       /* btnLogout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Home.this, LoginActivity.class));
            }
        });*/

        // btmNavView = findViewById(R.id.bottom_Nav);
        // btmNavView.setOnNavigationItemSelectedListener(NavigationView);
        btmNavView.setSelectedItemId(R.id.home_bottom_nav);

        btmNavView.setOnNavigationItemSelectedListener(this);
          /*  @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.notification_bottom_nav:
                        showFragmentsss(new Notification_Fragment());
                        return  true;
                    //break;

                    case R.id.post_bottom_nav:
                        //fragment = new Post_Fragment();
                        showFragmentsss(new Post_Fragment());
                        return true;
                    //break;

                    case R.id.chat_bottom_nav:
                        //fragment = new Chat_Fragment();
                        showFragmentsss(new Chat_Fragment());
                        return true;
                    //break;

                    case R.id.home_bottom_nav:
                        showFragmentsss(new Home_Fragment());
                        return  true;
                    //break;

                }

                return false;
            }*/

    }

    public  boolean loadFragments(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
        }
        return  true;
    }
    public void showFragments(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    public void showFragmentsss(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
                break;
            //break;

            case R.id.post_bottom_nav:
                fragment = new Post_Fragment();
                break;

            case R.id.chat_bottom_nav:
                 fragment = new Chat_Fragment();
                 break;


            case R.id.home_bottom_nav:
                fragment = new Home_Fragment();
                break;
        }
        return loadFragments(fragment);
    }
}