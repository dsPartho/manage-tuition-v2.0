package com.example.managetution;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;
import java.util.UUID;
import java.util.zip.Inflater;

public class NotificationGuardianShowAdapter extends FirebaseRecyclerAdapter<ChatReference,NotificationGuardianShowAdapter.notificationShowViewHolder> {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private DatabaseReference acceptDataBaseRef,decDataBaseRef;
    public String guardianUserName,tutorUserName;



    public NotificationGuardianShowAdapter(@NonNull FirebaseRecyclerOptions<ChatReference> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationGuardianShowAdapter.notificationShowViewHolder holder, int position, @NonNull ChatReference model) {

        holder.TutorUserName.setText(model.getTutorUserName());

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                holder.acceptButton.setVisibility(View.GONE);
                holder.declineButton.setVisibility(View.GONE);
                String uniqueID = UUID.randomUUID().toString();
                String type = "Accept";
                NotificationColabartion notificationColabartion = new NotificationColabartion(model.getGuardianUserName(), model.getTutorUserName(),type);
                firebaseDatabase.getReference("acceptUserList").child(uniqueID).setValue(notificationColabartion).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity, "databaseCreated", Toast.LENGTH_SHORT).show();
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
        holder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                holder.acceptButton.setVisibility(View.GONE);
                holder.declineButton.setVisibility(View.GONE);
                String uniqueID = UUID.randomUUID().toString();
                String type = "Decline";
                NotificationColabartion notificationColabartion = new NotificationColabartion(model.getGuardianUserName(), model.getTutorUserName(),type);
                firebaseDatabase.getReference("acceptUserList").child(uniqueID).setValue(notificationColabartion).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity, "datacreated", Toast.LENGTH_SHORT).show();
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


    @NonNull
    @Override
    public NotificationGuardianShowAdapter.notificationShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_show_layout,parent,false);

        return  new NotificationGuardianShowAdapter.notificationShowViewHolder(view);
    }

    public class notificationShowViewHolder extends RecyclerView.ViewHolder {
        public  TextView guardianUsername,TutorUserName;
        public Button acceptButton,declineButton;


        public notificationShowViewHolder(@NonNull View itemView) {
            super(itemView);
            TutorUserName = itemView.findViewById(R.id.has_updated_post_id_notification);
            acceptButton = itemView.findViewById(R.id.acceptRequestButtonId);
            declineButton = itemView.findViewById(R.id.declineRequestButtonId);

        }
    }

}
