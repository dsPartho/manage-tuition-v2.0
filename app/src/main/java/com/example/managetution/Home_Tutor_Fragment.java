package com.example.managetution;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home_Tutor_Fragment extends Fragment {
    private RecyclerView postShowRecyclerView;
    private String date,time,username,postDetails;
    LinearLayoutManager linearLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button sendTuitionRequestButton;
    TutorPostAdapter adapter;
    public  Home_Tutor_Fragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_bottom_fragment_tutor,container,false);

        postShowRecyclerView = view.findViewById(R.id.home_recyclerview_id_tutor);
        LinearLayoutManager linearLayoutManager = new WrapLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        postShowRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mAuth = FirebaseAuth.getInstance();
        sendTuitionRequestButton =( Button) view.findViewById(R.id.sendtuitionrequestbuttonId);
        firebaseUser = mAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference =  firebaseDatabase.getReference("post");
        databaseReference.keepSynced(true);

        // ChildEventListener
        FirebaseRecyclerOptions<PostSaveDetails> options =
                new FirebaseRecyclerOptions.Builder<PostSaveDetails>()
                        .setQuery(databaseReference.orderByChild("dateTime"), PostSaveDetails.class)
                        .build();
        adapter = new TutorPostAdapter(options);
        postShowRecyclerView.setAdapter(adapter);


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
