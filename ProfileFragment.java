package com.creativeitinstitute.mychat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.creativeitinstitute.mychat.Model.User;
import com.creativeitinstitute.mychat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    ImageView profile, cover;
    TextView userName, userEmail;


    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    FirebaseUser firebaseUser;

    String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profile = view.findViewById(R.id.profile_image);
        cover = view.findViewById(R.id.coverPic);

        userName = view.findViewById(R.id.profileName);
        userEmail = view.findViewById(R.id.profileEmail);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        if (firebaseUser != null) {

            userId = firebaseUser.getUid();
            databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    User user=snapshot.getValue(User.class);

                    if (user!=null){

                        userName.setText(user.getName());
                        userEmail.setText(user.getEmail());

                        if (user.getImgurl().equals("null")){

                            cover.setImageResource(R.mipmap.ic_launcher);
                            profile.setImageResource(R.mipmap.ic_launcher);



                        }else {


                            Glide.with(getContext()).load(user.getImgurl()).into(profile);

                            Glide.with(getContext()).load(user.getImgurl()).into(cover);
                        }




                    }







                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


        return view;
    }
}