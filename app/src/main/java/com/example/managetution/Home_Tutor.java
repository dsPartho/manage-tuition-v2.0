package com.example.managetution;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_Tutor extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView btmNavView;

    //Variables (Partho)
    DrawerLayout drawerLayoutTutor;
    NavigationView navigationViewTutor;
    Toolbar toolbar;
    Fragment fragment;
    //sagar
    ActionBarDrawerToggle Toggle;
    Integer count = 0;
    private AlertDialog.Builder logOutBuilder,exitAppBuilder;
    private String username,userId;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private View headerView;
    private TextView userNameText;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutor_home);
        loadFragments(new Home_Fragment());
        //getSupportActionBar().hide();
        toolbar = (Toolbar)findViewById(R.id.toolbar_tutor);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Manage Tuition");

        btmNavView = findViewById(R.id.bottom_nav_tutor);
        // btmNavView.setOnNavigationItemSelectedListener(NavigationView);
        btmNavView.setSelectedItemId(R.id.home_bottom_nav);
        drawerLayoutTutor = findViewById(R.id.drawer_layout_tutor);
        navigationViewTutor = findViewById(R.id.top_nav_tutor);

        btmNavView.setOnNavigationItemSelectedListener(this);

        /* ------- ToolBAR ----------*/
        setSupportActionBar(toolbar);
        /* -------------- Navigation Drawer Menu ---*/
        navigationViewTutor.bringToFront();
        Toggle = new ActionBarDrawerToggle( this, drawerLayoutTutor, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayoutTutor.addDrawerListener(Toggle);
        Toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_view_headline_24);
        //headerview for username
        headerView = navigationViewTutor.getHeaderView(0);
        userNameText =(TextView) headerView.findViewById(R.id.userNameId);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
        tutorReadData(new DataTutorCallBackFirebase() {
            @Override
            public void onCallBack(String value) {
                Log.d("username " ,value);
                userNameText.setText(username);
            }
        });
        // drawerLayout.closeDrawer(GravityCompat.START);
        if(fragment== null){
            loadFragments(new Home_Tutor_Fragment());
        }
        navigationViewTutor.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_profile:
                        Intent profIntent = new Intent(getApplicationContext(),TutorProfile.class);
                        profIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(profIntent);
                        drawerLayoutTutor.closeDrawer(GravityCompat.START);
                        finish();
                        return true;
                    case R.id.nav_logout:
                        //Toast.makeText(MainActivity.this, "Log out  Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        logOutBuilder = new AlertDialog.Builder(Home_Tutor.this);
                        //setTitle
                        logOutBuilder.setTitle("Log Out");
                        //Set message
                        logOutBuilder.setMessage("Are You Sure to Logout ???");
                        //positive yes button
                        logOutBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // log out
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                finish();
                            }
                        });

                        //log out cancel button
                        logOutBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //dismiss dialogue;
                                dialogInterface.dismiss();
                            }
                        });
                        // show  dialogue
                        logOutBuilder.show();
                        return true;

                    case R.id.nav_contact_us:
                        AlertDialog.Builder contactUsBuilder = new AlertDialog.Builder(Home_Tutor.this);
                        contactUsBuilder.setTitle("Contact Us Through Mobile");
                        contactUsBuilder.setMessage("Phone No 1: 01521551806\nPhone No 2: 01572181261");

                        contactUsBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        contactUsBuilder.show();
                        return true;

                    case R.id.nav_share:
                        TextView tv  = new TextView(Home_Tutor.this);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setTextSize(18);
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tv.setText(Html.fromHtml("<br><a href='https://github.com/dsPartho/manage-tuition-v2.0'> Click Here</a>"));
                        AlertDialog.Builder shareBtnBuilder = new AlertDialog.Builder(Home_Tutor.this);
                        shareBtnBuilder.setTitle("Share The App If You Like It!");
                        //shareBtnBuilder.setMessage(Html.fromHtml("<br><a href='https://github.com/dsPartho/manage-tuition-v2.0'>Click Here</a>"));
                        shareBtnBuilder.setView(tv);
                        shareBtnBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        shareBtnBuilder.show();
                        return true;
                }


                return true;
            }
        });

    }

    public  boolean loadFragments(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_tutor,fragment).addToBackStack(null).commit();
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
        if(btmNavView.getSelectedItemId() == R.id.home_bottom_nav_tutor && drawerLayoutTutor.isDrawerOpen(GravityCompat.START)){
            Toast.makeText(getApplicationContext(),"FIRST CONDITION", Toast.LENGTH_LONG).show();
            drawerLayoutTutor.closeDrawer(GravityCompat.START);
            if(fragment instanceof  Notification_Fragment){
                System.out.println("dhukse  if noti ");
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            if(fragment instanceof  Chat_Fragment){
                System.out.println("dhukse  if  chat");
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            btmNavView.setSelectedItemId(R.id.home_bottom_nav);
        }
        else if(btmNavView.getSelectedItemId() == R.id.home_bottom_nav_tutor &&(!drawerLayoutTutor.isDrawerOpen(GravityCompat.START) &&(fragment instanceof Chat_Fragment))){
            Toast.makeText(getApplicationContext(),"FIRST CONDITION", Toast.LENGTH_LONG).show();
            drawerLayoutTutor.closeDrawer(GravityCompat.START);
            btmNavView.setSelectedItemId(R.id.home_bottom_nav);
        }
        else if(btmNavView.getSelectedItemId() == R.id.home_bottom_nav_tutor &&(!drawerLayoutTutor.isDrawerOpen(GravityCompat.START) )  ){
            Toast.makeText(getApplicationContext(),"IF CONDITION", Toast.LENGTH_LONG).show();
            super.onBackPressed();
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),"ELSE CONDITION", Toast.LENGTH_LONG).show();
            drawerLayoutTutor.closeDrawer(GravityCompat.START);
            if(fragment instanceof  Notification_Fragment){
                System.out.println("dhukse else if noti ");
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            if(fragment instanceof  Chat_Fragment){
                System.out.println("dhukse else if  chat");
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            btmNavView.setSelectedItemId(R.id.home_bottom_nav_tutor);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.notification_bottom_nav_tutor:
                fragment = new Notification_Tutor_Fragment();
                Fragment finalFragment = fragment;
                tutorReadData(new DataTutorCallBackFirebase() {
                    @Override
                    public void onCallBack(String value) {
                        Log.d("notification_tutor",value);
                        System.out.println("notifictutor" + value);
                        Bundle dataBundle = new Bundle();
                        dataBundle.putString("guardianUserName",value);
                        finalFragment.setArguments(dataBundle);

                    }
                });
                item.setChecked(true);
                break;
            //break;

            case R.id.search_bottom_nav_tutor:
                fragment = new Search_Tuition_Fragment();
                Toast.makeText(this, "search bar Pressed!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.chat_bottom_nav_tutor:
                fragment = new Chat_Fragment_Tutor();
                item.setChecked(true);
                break;


            case R.id.home_bottom_nav_tutor:
                fragment = new Home_Tutor_Fragment();
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(Toggle.onOptionsItemSelected(item)){
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
    public  void tutorReadData(DataTutorCallBackFirebase dataCallBackFirebase){
        firebaseDatabase.getReference("TutorUser").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TutorUsersByPartho tutorUsers = snapshot.getValue(TutorUsersByPartho.class);
                username = tutorUsers.getFirstname() + " " + tutorUsers.getLastname();
                dataCallBackFirebase.onCallBack(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private interface DataTutorCallBackFirebase{
        void onCallBack(String value);
    }
}
