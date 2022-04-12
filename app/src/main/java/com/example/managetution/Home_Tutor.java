package com.example.managetution;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home_Tutor extends AppCompatActivity {
    Button btnLogout;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home);

       /* btnLogout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Home.this, LoginActivity.class));
            }
        });*/

        BottomNavigationView btmNavView = findViewById(R.id.bottom_Nav_tutor);
        btmNavView.setOnNavigationItemSelectedListener(NavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Home_Fragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener NavigationView = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = new Home_Fragment();
            switch (item.getItemId()){
                case R.id.notification_bottom_nav_tutor:
                    fragment = new Notification_Fragment();
                    break;

                case R.id.search_bottom_nav_tutor:
                    fragment = new Post_Fragment();
                    break;

                case R.id.chat_bottom_nav_tutor:
                    fragment = new Chat_Fragment();
                    break;

                case R.id.home_bottom_nav_tutor:
                    fragment = new Home_Fragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
            return true;

        }
    };
}
