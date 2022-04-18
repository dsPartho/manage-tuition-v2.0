package com.example.managetution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.Inflater;

public class Search_Tuition_Fragment extends Fragment {

    private RecyclerView postShowRecyclerView;
    private String date,time,username,postDetails;
    LinearLayoutManager linearLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button sendTuitionRequestButton;
     TutorPostAdapter adapter;
public Search_Tuition_Fragment(){

}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
       View view = inflater.inflate(R.layout.fragment_search_tuition,container,false);
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
   // @Override
   /* public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }*/


    @Override
    public void  onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem item = menu.findItem(R.id.search_menu_id);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
        //return true;
    }
   // @Override
    //public boolean onCreateOptionsMenu()



    public void processsearch(String s){
    String value = s.toLowerCase();
        System.out.println(value);

        FirebaseRecyclerOptions<PostSaveDetails> options =
                new FirebaseRecyclerOptions.Builder<PostSaveDetails>()
                        .setQuery( FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("post").orderByChild("location").equalTo(value), PostSaveDetails.class)
                        .build();
        //System.out.println(PostSaveDetails.class);
        adapter = new TutorPostAdapter(options);
        adapter.startListening();
        postShowRecyclerView.setAdapter(adapter);

    }
}
