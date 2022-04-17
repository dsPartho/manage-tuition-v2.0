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

import java.util.UUID;

public class NotificationTutorShowAdapter extends FirebaseRecyclerAdapter<NotificationColabartion,NotificationTutorShowAdapter.notificationShowViewHolder> {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private DatabaseReference acceptDataBaseRef,decDataBaseRef;
    public String guardianUserName,tutorUserName;



    public NotificationTutorShowAdapter(@NonNull FirebaseRecyclerOptions<NotificationColabartion> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationTutorShowAdapter.notificationShowViewHolder holder, int position, @NonNull NotificationColabartion model) {

        holder.guardianUsername.setText(model.getGuardianUserName());
        holder.type.setText(model.getType());



    }


    @NonNull
    @Override
    public NotificationTutorShowAdapter.notificationShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_tutor_show_layout,parent,false);

        return  new NotificationTutorShowAdapter.notificationShowViewHolder(view);
    }

    public class notificationShowViewHolder extends RecyclerView.ViewHolder {
        public TextView guardianUsername,type,string;


        public notificationShowViewHolder(@NonNull View itemView) {
            super(itemView);
            guardianUsername = itemView.findViewById(R.id.guardianUserNamenotificationtytorId);
            type = itemView.findViewById(R.id.typeId);
            string = itemView.findViewById(R.id.post_tt_id_notification);


        }
    }

}
