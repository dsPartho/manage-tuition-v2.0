package com.example.managetution;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class TutorPostAdapter extends FirebaseRecyclerAdapter<PostSaveDetails,TutorPostAdapter.postShowViewHolder> {
    private FirebaseAuth mAuth;
    private String currentUser,chat_userName,chat_userId,guardianUserName,tutorUserName;
    private  FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    //private chatInterface chatInterface;
    BottomNavigationView btmNav;
    String[] splitString ;
    public static  String firstName,lastName,tutorUserId,tutorFirstName,tutorLastName;
    DatabaseReference chatRequestReference;


    public TutorPostAdapter(@NonNull FirebaseRecyclerOptions<PostSaveDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TutorPostAdapter.postShowViewHolder holder, int position, @NonNull PostSaveDetails PostSavaDetails) {
        holder.image.setImageResource(R.drawable.ic_baseline_profile_24);
        holder.username.setText(PostSavaDetails.getUsername());
        holder.hasUpdated.setText("Has updated a Tuition Post");
        holder.date.setText(" " +PostSavaDetails.getDate());
        holder.time.setText(" " + PostSavaDetails.getTime());
        holder.postDetails.setText(PostSavaDetails.getPostDetails());



        holder.sendTuitionRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_tutor,new Home_Tutor_Fragment()).addToBackStack(null).commit();
                String uniqueID = UUID.randomUUID().toString();
                splitString = PostSavaDetails.getUsername().split(" ");
                firstName = splitString[0];
                lastName = splitString[1];
                mAuth = FirebaseAuth.getInstance();
                //System.out.println(splitString[0] + " " + splitString[1]);
                guardianUserName = PostSavaDetails.getUsername();
                System.out.println(guardianUserName);
                tutorUserId = mAuth.getCurrentUser().getUid();


                firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
                chatRequestReference = firebaseDatabase.getReference("post");
                chatRequestReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                chat_userName = dataSnapshot.child("username").getValue(String.class);
                                // Log.d("chat_userName",chat_userName);
                                System.out.println("chat"+ chat_userName);
                                System.out.println("username + chat :" + guardianUserName);
                                if(guardianUserName.equals(chat_userName)){
                                    System.out.println("yess");
                                    chat_userId = dataSnapshot.child("userId").getValue(String.class);
                                    System.out.println(chat_userId);
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                ReadDataRetrieve(new DataRetrieve() {
                    @Override
                    public void onRetrieve(String value) {
                        tutorUserName = value;
                        ChatReference chatReference= new ChatReference(firstName,lastName,tutorUserId,guardianUserName,tutorUserName);
                        FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ChatRequestReference").child(uniqueID).setValue(chatReference).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(activity, "data is inserted chat ref", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(activity, "data not inserted into child ref", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });




            }
        });
    }

    @NonNull
    @Override
    public TutorPostAdapter.postShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_post,parent,false);

        return  new TutorPostAdapter.postShowViewHolder(view);
    }

    public class postShowViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView date, time, postDetails,username, hasUpdated;
        public Button sendTuitionRequestButton;

        public postShowViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuth = FirebaseAuth.getInstance();
            image = itemView.findViewById(R.id.circleImage_user_Post_id);
            username= itemView.findViewById(R.id.post_userName_txt_Id);
            hasUpdated = itemView.findViewById(R.id.has_updated_post_id);
            date = itemView.findViewById(R.id.post_date_id);
            time = itemView.findViewById(R.id.post_time_id);
            postDetails = itemView.findViewById(R.id.user_post_details_id);
            //btmNav = itemView.findViewById(R.id.bo);
            sendTuitionRequestButton = itemView.findViewById(R.id.sendtuitionrequestbuttonId);

        }
    }

    public  void ReadDataRetrieve(DataRetrieve dataRetrieve){
        FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("TutorUser").child(tutorUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TutorUsers tutorUsers= snapshot.getValue(TutorUsers.class);
                tutorUserName = tutorUsers.getFirstname() + " " + tutorUsers.getLastname();
                System.out.println("tutt" + tutorUserName);
                dataRetrieve.onRetrieve(tutorUserName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private  interface  DataRetrieve{
         void onRetrieve(String value);
    }


}
