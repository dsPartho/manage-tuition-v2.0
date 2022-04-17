package com.example.managetution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class Notification_Tutor_Fragment extends Fragment {

    private RecyclerView tutorNotificationRecyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference acceptDataRef,declineDataRef;
    private FirebaseDatabase firebaseDatabase;
    private NotificationTutorShowAdapter notificationTutorShowAdapter;
    private String guardianUserName,tutorUserName,userId,username;
    public Notification_Tutor_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification__tutor_,container,false);
        tutorNotificationRecyclerView= view.findViewById(R.id.notification_recyclerview_id_tutor);
        LinearLayoutManager notificationLinearTutorLayoutManager = new WrapLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        tutorNotificationRecyclerView.setLayoutManager(notificationLinearTutorLayoutManager);
        notificationLinearTutorLayoutManager.setReverseLayout(true);
        notificationLinearTutorLayoutManager.setStackFromEnd(true);
        ///getting username from home
        Bundle dataBundle = this.getArguments();
        tutorUserName = dataBundle.getString("guardianUserName");
        System.out.println("guser" +tutorUserName);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
         userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
       // chatDataRef = firebaseDatabase.getReference("ChatRequestReference");
        //chatDataRef.keepSynced(true);
        acceptDataRef = firebaseDatabase.getReference("acceptUserList");
        acceptDataRef.keepSynced(true);
        readData(new DataNTCallBackFirebase() {
            @Override
            public void onCallBack(String value) {
                tutorUserName = value;

                FirebaseRecyclerOptions<NotificationColabartion>notificationTutorOptions =
                        new FirebaseRecyclerOptions.Builder<NotificationColabartion>().setQuery(acceptDataRef.orderByChild("tutorUserName").equalTo(tutorUserName),NotificationColabartion.class).build();
            }
        });
        System.out.println("ttt"+ tutorUserName);
        FirebaseRecyclerOptions<NotificationColabartion>notificationTutorOptions =
                new FirebaseRecyclerOptions.Builder<NotificationColabartion>().setQuery(acceptDataRef.orderByChild("tutorUserName").equalTo(tutorUserName),NotificationColabartion.class).build();
        notificationTutorShowAdapter = new NotificationTutorShowAdapter(notificationTutorOptions);

        notificationTutorShowAdapter.notifyDataSetChanged();
        tutorNotificationRecyclerView.setAdapter(notificationTutorShowAdapter);
        tutorNotificationRecyclerView.stopScroll();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        notificationTutorShowAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        notificationTutorShowAdapter.stopListening();
    }
    public  void readData(DataNTCallBackFirebase dataCallBackFirebase){
        firebaseDatabase.getReference("TutorUser").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TutorUsers tutorUsers = snapshot.getValue(TutorUsers.class);
                username = tutorUsers.getFirstname() + " " + tutorUsers.getLastname();
                dataCallBackFirebase.onCallBack(username);

                //username

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private interface DataNTCallBackFirebase{
        void onCallBack(String value);
    }
}
