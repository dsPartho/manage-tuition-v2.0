package com.example.managetution;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostShowAdapt extends FirebaseRecyclerAdapter<PostSaveDetails,PostShowAdapt.postShowViewHolder> {
    private FirebaseAuth mAuth;
    private String currentUser,postUserId,sendDate,sendTime,postId;
    private  FirebaseUser firebaseUser;
    //private chatInterface chatInterface;
    BottomNavigationView btmNav;




    public PostShowAdapt(@NonNull FirebaseRecyclerOptions<PostSaveDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull postShowViewHolder holder, int position, @NonNull PostSaveDetails PostSavaDetails) {
        holder.image.setImageResource(R.drawable.ic_baseline_profile_24);
        holder.username.setText(PostSavaDetails.getUsername());
        holder.hasUpdated.setText("Has updated a Tuition Post");
        holder.date.setText(" " +PostSavaDetails.getDate());
        holder.time.setText(" " + PostSavaDetails.getTime());
        holder.postDetails.setText(PostSavaDetails.getPostDetails());
        holder.locationText.setText(PostSavaDetails.getLocation());

       /* postUserId = PostSavaDetails.getUserId();
        sendDate = PostSavaDetails.getDate();
        sendTime = PostSavaDetails.getTime();
        postId = PostSavaDetails.getPostId();*/
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                // activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Chat_Fragment()).commit();

                Intent intent = new Intent(view.getContext(),PostShowOrEdit.class);
                intent.putExtra("userName",PostSavaDetails.getUsername());
                intent.putExtra("PostDetails",PostSavaDetails.getPostDetails());
                intent.putExtra("locationText",PostSavaDetails.getLocation());
                intent.putExtra("date",PostSavaDetails.getDate());
                intent.putExtra("time",PostSavaDetails.getTime());
                intent.putExtra("postUserId",userID);
                intent.putExtra("postId",PostSavaDetails.getPostId());
                activity.startActivity(intent);
                activity.finish();

            }
        });

       /* holder.sendTuitionRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Notification_Fragment()).addToBackStack(null).commit();
            }
        });*/


    }

    @NonNull
    @Override
    public postShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guardianpostlayout,parent,false);

        return  new postShowViewHolder(view);
    }

    public class postShowViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView date, time, postDetails,username, hasUpdated,location, locationText;
        Button editButton;

        public postShowViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuth = FirebaseAuth.getInstance();
            image = itemView.findViewById(R.id.circleImage_user_Post_id);
            username= itemView.findViewById(R.id.post_userName_txt_Id);
            hasUpdated = itemView.findViewById(R.id.has_updated_post_id);
            date = itemView.findViewById(R.id.post_date_id);
            time = itemView.findViewById(R.id.post_time_id);
            postDetails = itemView.findViewById(R.id.user_post_details_id);
            location = itemView.findViewById(R.id.guardianPostlocationId);
            locationText = itemView.findViewById(R.id.guardianPostlocationTextId);
            editButton = itemView.findViewById(R.id.editId);
            //btmNav = itemView.findViewById(R.id.bo);
            /*sendTuitionRequestButton = itemView.findViewById(R.id.sendtuitionrequestbuttonId);
            sendTuitionRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Notification_Fragment()).addToBackStack(null).commit();

                }
            });*/
           // System.out.println("post" + postUserId);
           // System.out.println("Adapter " + sendDate);
           // System.out.println("Adapter " + sendTime);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    // activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Chat_Fragment()).commit();

                    Intent intent = new Intent(view.getContext(),PostShowOrEdit.class);
                    intent.putExtra("userName",username.getText());
                    intent.putExtra("PostDetails",postDetails.getText());
                    intent.putExtra("locationText",locationText.getText());
                    intent.putExtra("date",sendDate);
                    intent.putExtra("time",sendTime);
                    intent.putExtra("postUserId",postUserId);
                    intent.putExtra("postId",postId+date+time);
                    activity.startActivity(intent);
                    activity.finish();



                    // itemView.setTag(vie);
                }
            });*/

        }
    }

}
