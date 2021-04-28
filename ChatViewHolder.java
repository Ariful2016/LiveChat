package com.creativeitinstitute.mychat.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeitinstitute.mychat.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {


    public ImageView profile, imgMessage;

    public TextView message;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        profile = itemView.findViewById(R.id.profile_images);
        message = itemView.findViewById(R.id.textMessage);
        imgMessage = itemView.findViewById(R.id.imgMessage);


    }
}
