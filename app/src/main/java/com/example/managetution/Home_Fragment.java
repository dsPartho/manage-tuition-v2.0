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

public class Home_Fragment extends Fragment {
    private RecyclerView postShowRecyclerView;
    private ArrayList<PostShowDataModel> dataPostShowArrayList;
    private String date,time,username,postDetails;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button sendTuitionRequestButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_bottom_fragment,container,false);
        postShowRecyclerView = view.findViewById(R.id.home_recyclerview_id);
        postShowRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth = FirebaseAuth.getInstance();
        sendTuitionRequestButton = view.findViewById(R.id.sendtuitionrequestbuttonId);
        firebaseUser = mAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("post");
        dataPostShowArrayList = new ArrayList<>();
        postShowRecyclerView.setAdapter(new PostShowAdapter(dataPostShowArrayList)) ;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Toast.makeText(getActivity(), "dhuksee", Toast.LENGTH_SHORT).show();
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        //Toast.makeText(getActivity(), "heyyey", Toast.LENGTH_SHORT).show();
                        PostShowDataModel postShowDataModel = dataSnapshot.getValue(PostShowDataModel.class);
                        dataPostShowArrayList.add(postShowDataModel);

                    }
                }
                else{
                    Toast.makeText(getActivity(), "Please create a tuition post!!!!", Toast.LENGTH_SHORT).show();
                }

                postShowRecyclerView.setAdapter(new PostShowAdapter(dataPostShowArrayList)) ;
                new PostShowAdapter(dataPostShowArrayList).notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*sendTuitionRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ;
            }
        });*/

       /* PostShowDataModel postShowDataModel = new PostShowDataModel("11-12-19","04:30","Tutor Needed class 9 Salary 8K","Sagar");
        dataPostShowArrayList.add(postShowDataModel);
        PostShowDataModel postShowDataModel1 = new PostShowDataModel("11-12-19","04:30","Tutor Needed class 9 Salary 8K","Sagar");
        dataPostShowArrayList.add(postShowDataModel1);
        PostShowDataModel postShowDataModel2 = new PostShowDataModel("11-12-19","04:30","Tutor Needed class 9 Salary 8K","Sagar");
        dataPostShowArrayList.add(postShowDataModel2);*/
        //postShowRecyclerView.setAdapter(new PostShowAdapter(dataPostShowArrayList)) ;
        return view;
    }
}
