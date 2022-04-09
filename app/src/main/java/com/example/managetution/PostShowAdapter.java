package com.example.managetution;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostShowAdapter extends RecyclerView.Adapter<PostShowAdapter.postShowViewHolder> {
    ArrayList<PostShowDataModel> dataPostArraylist;

    public PostShowAdapter(ArrayList<PostShowDataModel> dataPostArraylist) {
        this.dataPostArraylist = dataPostArraylist;
    }

    @NonNull
    @Override
    public postShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_post,parent,false);
       return  new postShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postShowViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.ic_baseline_profile_24);
        holder.username.setText(dataPostArraylist.get(position).getUsername());
        holder.hasUpdated.setText("Has updated a Tuition Post");
        holder.date.setText(" " +dataPostArraylist.get(position).getDate());
        holder.time.setText(" " + dataPostArraylist.get(position).getTime());
        holder.postDetails.setText(dataPostArraylist.get(position).getPostDetails());
    }

    @Override
    public int getItemCount() {
        return dataPostArraylist.size();
    }

    class postShowViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView date, time, postDetails,username, hasUpdated;

        public postShowViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.circleImage_user_Post_id);
            username= itemView.findViewById(R.id.post_userName_txt_Id);
            hasUpdated = itemView.findViewById(R.id.has_updated_post_id);
            date = itemView.findViewById(R.id.post_date_id);
            time = itemView.findViewById(R.id.post_time_id);
            postDetails = itemView.findViewById(R.id.user_post_details_id);
        }
    }
}
