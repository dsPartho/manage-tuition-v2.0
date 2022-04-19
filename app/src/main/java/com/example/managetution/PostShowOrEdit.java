package com.example.managetution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostShowOrEdit extends AppCompatActivity {
     private  CircleImageView image;
     private TextView date, time, username, hasUpdated,location,userName;
     private EditText postDetailss,locationTexts;
     private Button editDoneButton, deletePostButton;
     private FirebaseAuth mAuth;
     private FirebaseUser firebaseUser;
     private FirebaseDatabase firebaseDatabase;
     private DatabaseReference databaseReference;
     private  String postId,postUserId,userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show_or_edit);
        image = findViewById(R.id.circleImage_user_Post_idED);
        userName = findViewById(R.id.post_userName_txt_IdED);
        date = findViewById(R.id.post_date_idED);
        hasUpdated = findViewById(R.id.has_updated_post_idED);
        time = findViewById(R.id.post_time_idED);
        postDetailss = (EditText) findViewById(R.id.user_post_details_idED);
        location = findViewById(R.id.guardianPostlocationIdED);
        locationTexts = findViewById(R.id.guardianPostlocationTextIdPED);
        editDoneButton = findViewById(R.id.user_EDit_post_id);
        deletePostButton = findViewById(R.id.user_Delete_post_id);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userId = firebaseUser.getUid();

        //getting Value from previous post
        Intent intent = getIntent();
        String username = intent.getStringExtra("userName");
        String postDetails = intent.getStringExtra("PostDetails");
        String location = intent.getStringExtra("locationText");
        String fDate = intent.getStringExtra("date");
        String fTime = intent.getStringExtra("time");
        postId = intent.getStringExtra("postId");
        postUserId = intent.getStringExtra("postUserId");
        postDetailss.setText(postDetails, TextView.BufferType.EDITABLE);
        userName.setText(username);
        locationTexts.setText(location);
        date.setText(fDate);
        time.setText(fTime);
         //postId = userId + fDate + fTime;
        System.out.println("ed" + postId);
        Log.d("TAG", postId);

        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference =  firebaseDatabase.getReference("post").child(postId);
        editDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("postDetails").setValue(postDetailss.getText().toString());
                databaseReference.child("location").setValue(locationTexts.getText().toString());
                Intent newIntent = new Intent(getApplicationContext(),Home_Guardian.class);
                startActivity(newIntent);
                finish();
            }
        });
        deletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Intent newIntent = new Intent(getApplicationContext(),Home_Guardian.class);
                startActivity(newIntent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(this, Home_Guardian.class);
        startActivity(newIntent);
        finish();
    }
}