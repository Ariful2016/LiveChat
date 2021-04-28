package com.creativeitinstitute.mychat.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeitinstitute.mychat.R;

public class PostViewHolder extends RecyclerView.ViewHolder {
   public ImageView profileImg,postImg;
   public TextView userName, postText;


    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImg=itemView.findViewById(R.id.postProfile);
        postImg=itemView.findViewById(R.id.postImg);
        userName=itemView.findViewById(R.id.postUserName);
        postText=itemView.findViewById(R.id.postText);
    }
}
