package com.creativeitinstitute.mychat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.creativeitinstitute.mychat.ChatActivity;
import com.creativeitinstitute.mychat.Model.User;
import com.creativeitinstitute.mychat.ProfileActivity;
import com.creativeitinstitute.mychat.R;
import com.creativeitinstitute.mychat.ViewHolders.UserViewHolder;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    Context context;
    List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    public UserAdapter() {
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        final User user = users.get(position);

        if (user != null) {

            holder.name.setText(user.getName());
            holder.email.setText(user.getEmail());


            if (user.getImgurl().equals("null")) {

                holder.profileImage.setImageResource(R.drawable.livechat);

            } else {

                Glide.with(context).load(user.getImgurl()).into(holder.profileImage);
            }


        }


        holder.chatWithMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user != null) {

                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("userId", user.getUid());
                    context.startActivity(intent);
                }

            }
        });

        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (user != null) {

                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("userId", user.getUid());
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
