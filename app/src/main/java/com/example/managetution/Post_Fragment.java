package com.example.managetution;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.Locale;
import java.util.UUID;

public class Post_Fragment extends Fragment {
    private Button updatePostButton;
    private EditText postText,LocationText;
   private TextView Location,postStatusText,postStatusChecked;
    private String curDate , curTime,dateTime,postStatus;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private Switch isAvailable;
    private String postDetails,current_User,firstName,lastName,username,locationText;
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
                locationText = LocationText.getText().toString().toLowerCase();
                if(isAvailable.isChecked()){
                    //postStatusChecked.setText("available");
                    postStatus = "available";
                    //System.out.println("postStatusava" + "available");
                }
                /*else if(!isAvaiable.isChecked()){
                    //postStatusChecked.setText("onHold");
                    //postStatus = postStatusChecked.getText().toString();
                    postStatus = "available";
                    System.out.println("postStatusonHold" + "onHold");
                }*/


                if(TextUtils.isEmpty(postDetails)){
                    Toast.makeText(getActivity(),"please Enter tuition requirements and Details", Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(locationText)){
                    Toast.makeText(getActivity(),"please Enter tuition Location", Toast.LENGTH_LONG).show();
                }
                if(postStatus==null){
                    Toast.makeText(getActivity(), "please make post Status Available", Toast.LENGTH_SHORT).show();
                }
               /* if(TextUtils.isEmpty(postStatus)){
                    Toast.makeText(getActivity(),"please Enter post Status available or onHold", Toast.LENGTH_LONG).show();
                }*/
                else{
                    Calendar calendarDate =  Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    curDate =   currentDate.format(calendarDate.getTime());

                    Calendar calendarTime =  Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm");
                    curTime =   currentTime.format(calendarDate.getTime());


                    dateTime = curDate+curTime;
                    String uniqueID = UUID.randomUUID().toString();
                   /* firstName = userDatabaseReference.child("firstname").toString();
                    lastName = userDatabaseReference.child("lastname").toString();
                    username = firstName+lastName;*/

                    userDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            GuardianUsersByPartho guardianUsers = snapshot.getValue(GuardianUsersByPartho.class);
                            System.out.println(guardianUsers.getFirstname());
                            System.out.println(guardianUsers.getLastname());
                            // if(guardianUsers.getFirstname()!=null)
                            firstName = guardianUsers.getFirstname();
                            lastName =  guardianUsers.getLastname();
                            username = firstName +" " + lastName;
                            //String username = "sagar";
                            PostSaveDetails postSaveData = new PostSaveDetails(current_User,curDate,curTime,postDetails,username,dateTime,locationText,uniqueID,postStatus);
                            FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("GuardianUserOwnPost").child(current_User).child(uniqueID).setValue(postSaveData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Toast.makeText(getActivity(), "complete", Toast.LENGTH_SHORT).show();

                                }
                            });
                            FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("post").child(uniqueID).setValue(postSaveData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //Toast.makeText(getActivity(),"postData inserted into databasse",Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().replace(R.id.frame_layout,new Home_Fragment()).addToBackStack(null).commit();
                                        //getFragmentManager().beginTransaction().remove().commit();
                                    }
                                    else{
                                        //Toast.makeText(getActivity(),"postData not inserted into databasse",Toast.LENGTH_SHORT).show();
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
        LocationText = (EditText) getView().findViewById(R.id.PostlocationTextId);
        Location = (TextView) getView().findViewById(R.id.PostlocationId);
        postStatusText = (TextView) getView().findViewById(R.id.guardianPostStatusPId);
        isAvailable = (Switch) getView().findViewById(R.id.isAvailable);
        //postStatusChecked = (TextView) getView().findViewById(R.id.postStatusChecked);
    }
}