package com.creativeitinstitute.mychat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.creativeitinstitute.mychat.ImageFullActivity;
import com.creativeitinstitute.mychat.Model.Chat;
import com.creativeitinstitute.mychat.R;
import com.creativeitinstitute.mychat.ViewHolders.ChatViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    Context context;
    List<Chat> chats;

    final int MY_ID = 1;
    final int OTHER_ID = 2;

    FirebaseUser firebaseUser;
    String imageUrl;

    public ChatAdapter(Context context, List<Chat> chats, String imageUrl) {
        this.context = context;
        this.chats = chats;
        this.imageUrl = imageUrl;
    }

    public ChatAdapter() {
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MY_ID) {

            View view = LayoutInflater.from(context).inflate(R.layout.my_user, parent, false);


            return new ChatViewHolder(view);

        } else {


            View view = LayoutInflater.from(context).inflate(R.layout.other_user, parent, false);


            return new ChatViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        final Chat chat = chats.get(position);


        if (chat.getContent_img().equals("null")) {
            holder.imgMessage.setVisibility(View.GONE);
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText(chat.getMessage());

        } else {
            holder.imgMessage.setVisibility(View.VISIBLE);
            Glide.with(context).load(chat.getContent_img()).into(holder.imgMessage);

        }


        if (imageUrl.equals("null")) {
            holder.profile.setImageResource(R.mipmap.ic_launcher);

        } else {

            Glide.with(context).load(imageUrl).into(holder.profile);

        }

        holder.imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, ImageFullActivity.class);
                intent.putExtra("img", chat.getContent_img());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        int returnType = 0;

        if (firebaseUser != null) {

            String userId = firebaseUser.getUid();

            if (chats.get(position).getSender().equals(userId)) {

                returnType = MY_ID;

            } else {

                returnType = OTHER_ID;
            }


        }


        return returnType;
    }
}
