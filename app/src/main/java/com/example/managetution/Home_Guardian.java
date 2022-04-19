package com.example.managetution;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_Guardian extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView btmNavView;

    //Variables (Partho)
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Fragment fragment;
    //sagar
    ActionBarDrawerToggle Toggle;
    Integer count = 0;
    private AlertDialog.Builder logOutBuilder,exitAppBuilder;
    private TextView userNameText;
    private  FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public  static String userId,username;
    private FirebaseUser firebaseUser;
    private View headerView;



    // 16 April 2022, Partho
    DatabaseReference reference;


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
        //headerview for username
        headerView = navigationView.getHeaderView(0);
        userNameText =(TextView) headerView.findViewById(R.id.userNameId);

        btmNavView.setOnNavigationItemSelectedListener(this);

        /* ------- ToolBAR ----------*/
        setSupportActionBar(toolbar);
        /* -------------- Navigation Drawer Menu ---*/
        navigationView.bringToFront();
        Toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_view_headline_24);
        // drawerLayout.closeDrawer(GravityCompat.START);

        //Partho 16 april firebase
        /*
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("TutorUser").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ChatUser user = snapshot.getValue(ChatUser.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
        */

        // er uporer kaj ami korsi, nicher ta sagar er ager
        //for username getting
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
         userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");

        readData(new DataCallBackFirebase() {
            @Override
            public void onCallBack(String value) {
                Log.d("username " ,value);
                userNameText.setText(username);
            }
        });


        if(fragment== null){
            loadFragments(new Home_Fragment());
            System.out.println(" userName" +userNameText.getText().toString()+"paisi");
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_profile:
                        Intent profIntent = new Intent(getApplicationContext(),GuardianProfile.class);
                        profIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(profIntent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_logout:
                        //Toast.makeText(MainActivity.this, "Log out  Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        logOutBuilder = new AlertDialog.Builder(Home_Guardian.this);
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
                        AlertDialog.Builder contactUsBuilder = new AlertDialog.Builder(Home_Guardian.this);
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
                        TextView tv  = new TextView(Home_Guardian.this);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setTextSize(18);
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tv.setText(Html.fromHtml("<br><a href='https://github.com/dsPartho/manage-tuition-v2.0'> Click Here</a>"));
                        AlertDialog.Builder shareBtnBuilder = new AlertDialog.Builder(Home_Guardian.this);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment,"My_Fragment").addToBackStack(null).commit();

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
        else if(btmNavView.getSelectedItemId() == R.id.home_bottom_nav &&(!drawerLayout.isDrawerOpen(GravityCompat.START) )  ){
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
                Fragment finalFragment = fragment;
                readData(new DataCallBackFirebase() {
                    @Override
                    public void onCallBack(String value) {
                        System.out.println("notific" + value);
                        Bundle dataBundle = new Bundle();
                        dataBundle.putString("guardianUserName",value);
                        finalFragment.setArguments(dataBundle);
                        Notification_Fragment notification_fragment = new Notification_Fragment();
                        notification_fragment.setArguments(dataBundle);
                    }
                });
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
   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       if(Toggle.onOptionsItemSelected(item)){
           return  true;
       }

       return super.onOptionsItemSelected(item);
   }
   public  void readData(DataCallBackFirebase dataCallBackFirebase){
       firebaseDatabase.getReference("GuardianUser").child(userId).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               GuardianUsersByPartho guardianUsers = snapshot.getValue(GuardianUsersByPartho.class);
               username = guardianUsers.getFirstname() + " " + guardianUsers.getLastname();
               dataCallBackFirebase.onCallBack(username);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
   }
   private interface DataCallBackFirebase{
       void onCallBack(String value);
   }

}