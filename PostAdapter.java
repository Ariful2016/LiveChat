package com.creativeitinstitute.mychat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.creativeitinstitute.mychat.Model.Post;
import com.creativeitinstitute.mychat.Model.User;
import com.creativeitinstitute.mychat.R;
import com.creativeitinstitute.mychat.ViewHolders.PostViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    Context context;
    List<Post> postList;


    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    FirebaseUser firebaseUser;

    public PostAdapter() {
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Post post = postList.get(position);

        if (post!= null && firebaseUser!= null){

            holder.postText.setText(post.getStatus());
            Glide.with(context).load(post.getPost_img()).into(holder.postImg);

            getAuthor(holder.profileImg,holder.userName,post.getUser_id());
        }




    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void getAuthor(final ImageView profileImg, final TextView userName, String userId) {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                if (user != null) {

                    userName.setText(user.getName());



                    if (user.getImgurl().equals("null")){

                        profileImg.setImageResource(R.mipmap.ic_launcher);
                    }else {

                        Glide.with(context).load(user.getImgurl()).into(profileImg);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
