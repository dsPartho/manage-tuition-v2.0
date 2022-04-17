package com.example.managetution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    FirebaseUser fuser;
    DatabaseReference reference;

    Intent intent;

    ImageButton btnSend;
    EditText textSend;

    MessageAdapter messageAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.send_msg_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        btnSend = findViewById(R.id.btn_send_image);
        textSend = findViewById(R.id.txt_send);

        intent = getIntent();
        String userID = intent.getStringExtra("userID");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        System.out.println("USERID\n"+ userID + "\n\n");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = textSend.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(), userID, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "Empty Message", Toast.LENGTH_SHORT).show();
                }
                textSend.setText("");
            }
        });


        reference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("GuardianUser").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GuardianUsersByPartho user = dataSnapshot.getValue(GuardianUsersByPartho.class);
                //ekhane username set kora jay


                readMessages(fuser.getUid(),userID);  //picture add kora jaitp
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference r_reference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        r_reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessages(String myID, String userID){  /*3 ta attribute chilo for image(Part 8, 16:10 mnt) */
        mChat = new ArrayList<>();
        DatabaseReference f_reference = FirebaseDatabase.getInstance("https://managetution-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Chats");
        f_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    assert chat != null;
                    if(chat.getReceiver().equals(myID) && chat.getSender().equals(userID) || chat.getReceiver().equals(userID) && chat.getSender().equals(myID)){
                        mChat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}