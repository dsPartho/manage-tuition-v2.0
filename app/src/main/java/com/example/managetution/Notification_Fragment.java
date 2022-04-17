package com.example.managetution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class Notification_Fragment extends Fragment {
    private RecyclerView notificationRecyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference chatDataRef;
    private FirebaseDatabase firebaseDatabase;
    private NotificationGuardianShowAdapter notificationGuardianShowAdapter;
    private String gUserName,userId,username;

    public Notification_Fragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_bottom_fragment,container,false);
        notificationRecyclerView = view.findViewById(R.id.notification_recyclerview_id_guardian);
        LinearLayoutManager notificationLinearLayoutManager = new WrapLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        notificationRecyclerView.setLayoutManager(notificationLinearLayoutManager);
        notificationLinearLayoutManager.setReverseLayout(true);
        notificationLinearLayoutManager.setStackFromEnd(true);

        ///getting username from home
        Bundle dataBundle = this.getArguments();
        //gUserName = dataBundle.getString("guardianUserName");
        System.out.println("guser" + gUserName);


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
         userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
        chatDataRef = firebaseDatabase.getReference("GuardianUser").child(userId).child("notification");
        chatDataRef.keepSynced(true);

       /*  firebaseDatabase.getReference("GuardianUser").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        GuardianUsers guardianUsers = snapshot.getValue(GuardianUsers.class);
                        String userName = guardianUsers.getFirstname() + " " + guardianUsers.getLastname();
                        System.out.println(dataSnapshot);

                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        readData(new DataNCallBackFirebase() {
            @Override
            public void onCallBack(String value) {
                gUserName = value;
                System.out.println("g " + gUserName);

                /* FirebaseRecyclerOptions<ChatReference>notificationOptions =
                        new FirebaseRecyclerOptions.Builder<ChatReference>().setQuery(chatDataRef.orderByChild("guardianUserName").equalTo(gUserName),ChatReference.class).build();
                notificationGuardianShowAdapter = new NotificationGuardianShowAdapter(notificationOptions);
                notificationGuardianShowAdapter.notifyDataSetChanged();
                notificationRecyclerView.setAdapter(notificationGuardianShowAdapter);
                notificationRecyclerView.stopScroll();*/

                //

            }
        });

        FirebaseRecyclerOptions<ChatReference>notificationOptions =
                new FirebaseRecyclerOptions.Builder<ChatReference>().setQuery(chatDataRef,ChatReference.class).build();

        notificationGuardianShowAdapter = new NotificationGuardianShowAdapter(notificationOptions);

        notificationGuardianShowAdapter.notifyDataSetChanged();
        notificationRecyclerView.setAdapter(notificationGuardianShowAdapter);
        notificationRecyclerView.stopScroll();


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
       notificationGuardianShowAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        notificationGuardianShowAdapter.stopListening();
    }
    public  void readData(DataNCallBackFirebase dataCallBackFirebase){
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
    private interface DataNCallBackFirebase{
        void onCallBack(String value);
    }
}
