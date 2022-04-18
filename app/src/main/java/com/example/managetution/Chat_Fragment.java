package com.example.managetution;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chat_Fragment extends Fragment {

    private RecyclerView recyclerView;

    private ChatUserAdapter userAdapter;
    private List<ChatReference> mUsers;
    String _EMAIL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_bottom_fragment,container,false);

        recyclerView = view.findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        readUsers();

        return view;
    }
    @Override
    public void onDetach() {

        //PUT YOUR CODE HERE

        super.onDetach();

    }

    private void readUsers(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("GuardianUser").child(userID).child("notification");
//        DatabaseReference cuurUserReference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("GuardianUser").child(userID);
//
//        cuurUserReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                _EMAIL = snapshot.child("email").getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                //if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        ChatReference user = dataSnapshot.getValue(ChatReference.class);


                        assert user != null;
                        assert firebaseUser != null;
                        mUsers.add(user);


                    }
               // }
                userAdapter = new ChatUserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



       /* reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //TutorUsersByPartho user = snapshot.getValue(TutorUsersByPartho.class);
                    //System.out.println("\nUser is HERE\n" + user + "\n\n");

                    String _firstname = snapshot.child("firstname").getValue(String.class);
                    String _lastname = snapshot.child("lastname").getValue(String.class);
                    String _id = snapshot.child("id").getValue(String.class);
                    String _email = snapshot.child("email").getValue(String.class);
                    String _gender = snapshot.child("gender").getValue(String.class);
                    String _pictureURL = snapshot.child("picture_URL").getValue(String.class);
                    String _password = snapshot.child("pass").getValue(String.class);
                    String _institution = snapshot.child("institution").getValue(String.class);
                    String _phoneNo = snapshot.child("contactInfo").getValue(String.class);
                    String _role = snapshot.child("role").getValue(String.class);
                    String _acYear = snapshot.child("academicYear").getValue(String.class);
                    String _batch = "25";//snapshot.child("batch").getValue(String.class);

                    TutorUsersByPartho user = new TutorUsersByPartho(_id, _pictureURL, _role, _firstname, _lastname, _email,_password, _institution,_gender,_batch,_acYear,_phoneNo);


                    assert user != null;
                    assert firebaseUser != null;
                    mUsers.add(user);
//                    if(!user.getEmail().equals(_EMAIL)){
//                        mUsers.add(user);
//                    }
                }

                userAdapter = new ChatUserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}
