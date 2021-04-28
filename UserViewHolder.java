package com.creativeitinstitute.mychat.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeitinstitute.mychat.R;


public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView profileImage;

    public TextView name, email;

  public   Button chatWithMe, viewProfile;


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImage = itemView.findViewById(R.id.userProfile);
        name = itemView.findViewById(R.id.userName);
        email = itemView.findViewById(R.id.userEmail);

        chatWithMe = itemView.findViewById(R.id.chatWithMe);
        viewProfile = itemView.findViewById(R.id.viewProfile);


    }
}
