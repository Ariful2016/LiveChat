package com.creativeitinstitute.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.creativeitinstitute.mychat.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    ImageView profile, cover;
    TextView userName, userEmail;


    DatabaseReference databaseReference;


    String userId;

    Button profileEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();


        profile = findViewById(R.id.profile_image);
        cover = findViewById(R.id.coverPic);
        profileEdit = findViewById(R.id.profileEdit);

        userName = findViewById(R.id.profileName);
        userEmail = findViewById(R.id.profileEmail);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        userId = intent.getStringExtra("userId");


        assert userId != null;
        if (!userId.equals("")) {


            databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    User user = snapshot.getValue(User.class);

                    if (user != null) {

                        userName.setText(user.getName());
                        userEmail.setText(user.getEmail());

                        if (user.getImgurl().equals("null")) {

                            cover.setImageResource(R.mipmap.ic_launcher);
                            profile.setImageResource(R.mipmap.ic_launcher);


                        } else {


                            Glide.with(ProfileActivity.this).load(user.getImgurl()).into(profile);

                            Glide.with(ProfileActivity.this).load(user.getImgurl()).into(cover);
                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), ProEditActivity.class));

            }
        });

    }
}