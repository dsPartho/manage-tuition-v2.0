package com.example.managetution;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home_Guardian extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView btmNavView;

    //Variables (Partho)
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //sagar
    ActionBarDrawerToggle Toggle;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guardian_home);
        loadFragments(new Home_Fragment());
        //getSupportActionBar().hide();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Manage Tuition");

        btmNavView = findViewById(R.id.bottom_nav);
        // btmNavView.setOnNavigationItemSelectedListener(NavigationView);
        btmNavView.setSelectedItemId(R.id.home_bottom_nav);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.top_nav);

        btmNavView.setOnNavigationItemSelectedListener(this);

        /* ------- ToolBAR ----------*/
        setSupportActionBar(toolbar);

        /* -------------- Navigation Drawer Menu ---*/
        navigationView.bringToFront();
         Toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // drawerLayout.closeDrawer(GravityCompat.START);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

    }

    public  boolean loadFragments(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
        }
        return  true;
    }


    @Override
    public void onBackPressed() {
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//        else{
//            super.onBackPressed();
//        }
        if(btmNavView.getSelectedItemId() == R.id.home_bottom_nav && drawerLayout.isDrawerOpen(GravityCompat.START)){
            Toast.makeText(getApplicationContext(),"FIRST CONDITION", Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(GravityCompat.START);
            btmNavView.setSelectedItemId(R.id.home_bottom_nav);
        }
        else if(btmNavView.getSelectedItemId() == R.id.home_bottom_nav &&(!drawerLayout.isDrawerOpen(GravityCompat.START)) ){
            Toast.makeText(getApplicationContext(),"IF CONDITION", Toast.LENGTH_LONG).show();
            super.onBackPressed();
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),"ELSE CONDITION", Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(GravityCompat.START);
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
   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(Toggle.onOptionsItemSelected(item)){
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}