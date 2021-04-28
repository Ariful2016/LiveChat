package com.creativeitinstitute.mychat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.creativeitinstitute.mychat.Adapters.UserAdapter;
import com.creativeitinstitute.mychat.Model.User;
import com.creativeitinstitute.mychat.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }


    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    String userId;

    RecyclerView userRecyleView;

    UserAdapter adapter;

    DatabaseReference databaseReference;

    List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        userList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userRecyleView = view.findViewById(R.id.userRecyleView);
        userRecyleView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");


        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User user = dataSnapshot.getValue(User.class);

                    if (firebaseUser.getUid().equals(user.getUid())) {
                        continue;
                    }


                    userList.add(user);


                }


                adapter = new UserAdapter(getContext(), userList);
                userRecyleView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}