package com.example.managetution;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;

public class Home_Fragment extends Fragment {
    private RecyclerView postShowRecyclerView;
    private String date,time,username,postDetails;
    LinearLayoutManager linearLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button sendTuitionRequestButton;
    PostShowAdapt adapter;

    public Home_Fragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_bottom_fragment,container,false);

        postShowRecyclerView = view.findViewById(R.id.home_recyclerview_id);
        LinearLayoutManager linearLayoutManager = new WrapLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        postShowRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mAuth = FirebaseAuth.getInstance();
        sendTuitionRequestButton =( Button) view.findViewById(R.id.sendtuitionrequestbuttonId);
        firebaseUser = mAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference =  firebaseDatabase.getReference("GuardianUserOwnPost").child(userId);
        databaseReference.keepSynced(true);

        FirebaseRecyclerOptions<PostSaveDetails> options =
                new FirebaseRecyclerOptions.Builder<PostSaveDetails>()
                        .setQuery(databaseReference.orderByChild("dateTime"), PostSaveDetails.class)
                        .build();
        adapter = new PostShowAdapt(options);
        adapter.notifyDataSetChanged();
        postShowRecyclerView.setAdapter(adapter);
        postShowRecyclerView.stopScroll();


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}