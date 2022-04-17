package com.example.managetution;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder> {

    private Context mContext;
    private List<TutorUsersByPartho> mUsers;

    public ChatUserAdapter(Context mContext, List<TutorUsersByPartho> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.c_username);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent,false);

        return new ChatUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TutorUsersByPartho user = mUsers.get(position);
        holder.username.setText(user.getFirstname());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userID", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
