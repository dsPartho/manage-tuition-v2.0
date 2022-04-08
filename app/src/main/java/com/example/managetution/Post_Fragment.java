package com.example.managetution;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Post_Fragment extends Fragment {
    private Button updatePostButton;
    private EditText postText;
    private String curDate , curTime,dateTime;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String postDetails,current_User,firstName,lastName,username;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private DatabaseReference userDatabaseReference,postDatabaseReference;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializations();
        mAuth = FirebaseAuth.getInstance();
        current_User = mAuth.getCurrentUser().getUid();
        userDatabaseReference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("GuardianUser").child(current_User);
        postDatabaseReference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("TutorUser");

        updatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 postDetails = postText.getText().toString();
                if(TextUtils.isEmpty(postDetails)){
                    Toast.makeText(getActivity(),"please Enter tuition requirements and Details", Toast.LENGTH_LONG).show();
                }
                else{
                    Calendar calendarDate =  Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    curDate =   currentDate.format(calendarDate.getTime());

                    Calendar calendarTime =  Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm");
                    curTime =   currentTime.format(calendarDate.getTime());

                    dateTime = curDate + curTime;
                   /* firstName = userDatabaseReference.child("firstname").toString();
                    lastName = userDatabaseReference.child("lastname").toString();
                    username = firstName+lastName;*/

                    userDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           GuardianUsers guardianUsers = snapshot.getValue(GuardianUsers.class);
                           System.out.println(guardianUsers.getFirstname());
                            System.out.println(guardianUsers.getLastname());
                          // if(guardianUsers.getFirstname()!=null)
                            firstName = guardianUsers.getFirstname();
                           lastName =  guardianUsers.getLastname();
                            username = firstName +" " + lastName;
                            //String username = "sagar";
                            PostSaveDetails postSaveData = new PostSaveDetails(current_User,curDate,curTime,postDetails,username);
                            FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("GuardianUser").child(current_User).child("post").child(current_User+dateTime).setValue(postSaveData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(),"postData inserted into databasse",Toast.LENGTH_SHORT).show();
                                        Intent newIntent = new Intent(getActivity(),Home.class);
                                        newIntent .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(newIntent);
                                    }
                                    else{
                                        Toast.makeText(getActivity(),"postData not inserted into databasse",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });







                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_bottom_fragment,container,false);
        return view;
    }
    public void initializations(){
        postText = (EditText) getView().findViewById(R.id.editMltTextId);
        updatePostButton = (Button)  getView().findViewById(R.id.PostButtonId);
    }
}
