package com.creativeitinstitute.mychat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.creativeitinstitute.mychat.Adapters.ChatAdapter;
import com.creativeitinstitute.mychat.Model.Chat;
import com.creativeitinstitute.mychat.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    EditText textMessage;
    ImageView sendBtn, attachmentBtn;

    RecyclerView recyclerView;
    String userId, myId;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    CircleImageView profile_image;
    TextView userName_toolbar;

    ChatAdapter chatAdapter;
    List<Chat> chatList;
    BottomSheetBehavior bottomSheetBehavior;

    final int IMAGE_REQ = 1;
    Button pickImage;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        profile_image = findViewById(R.id.profile_image);
        userName_toolbar = findViewById(R.id.userName_toolbar);

        chatList = new ArrayList<>();
        Intent intent = getIntent();

        userId = intent.getStringExtra("userId");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {

            myId = firebaseUser.getUid();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        storageReference = FirebaseStorage.getInstance().getReference("User_Upload");

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                if (user != null) {
                    userName_toolbar.setText(user.getName());
                    if (user.getImgurl().equals("null")) {

                        profile_image.setImageResource(R.mipmap.ic_launcher);

                    } else {
                        Glide.with(ChatActivity.this).load(user.getImgurl()).into(profile_image);

                    }


                }

                readMessage(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.recyleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.getStackFromEnd();
        linearLayoutManager.setStackFromEnd(true);



        recyclerView.setLayoutManager(linearLayoutManager);


        textMessage = findViewById(R.id.testMessage);
        sendBtn = findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = textMessage.getText().toString();


                sendMessage(message);


            }
        });


        View view = findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setPeekHeight(50);
        bottomSheetBehavior.setDraggable(true);


        pickImage = findViewById(R.id.pickImage);


        attachmentBtn = findViewById(R.id.attachmentBtn);
        attachmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });


        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQ);


            }
        });

    }

    private void readMessage(final User user) {


        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Chat chat = dataSnapshot.getValue(Chat.class);

                    if (chat != null) {

                        if (chat.getSender().equals(myId) && chat.getReceiver().equals(userId) ||
                                chat.getReceiver().equals(myId) && chat.getSender().equals(userId)
                        ) {
                            chatList.add(chat);
                        }


                    }


                }

                chatAdapter = new ChatAdapter(ChatActivity.this, chatList, user.getImgurl());

                recyclerView.setAdapter(chatAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void sendMessage(String message) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        HashMap<String, Object> chatMap = new HashMap<>();
        chatMap.put("message", message);
        chatMap.put("sender", myId);
        chatMap.put("receiver", userId);
        chatMap.put("content_img", "null");


        databaseReference.push().setValue(chatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()) {
                    textMessage.setText("");


                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == IMAGE_REQ) {

            if (resultCode == RESULT_OK) {


                if (data != null) {

                    Uri uri = data.getData();

                    final StorageReference myReference = storageReference.child(userId).child("IMG_" + System.currentTimeMillis());


                    myReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            myReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri myUri = task.getResult();

                                    String myImagePath = String.valueOf(myUri);


                                    databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

                                    HashMap<String, Object> chatMap = new HashMap<>();
                                    chatMap.put("message", "null");
                                    chatMap.put("sender", myId);
                                    chatMap.put("receiver", userId);
                                    chatMap.put("content_img", myImagePath);


                                    databaseReference.push().setValue(chatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                Toast.makeText(ChatActivity.this, "OK ", Toast.LENGTH_SHORT).show();


                                            }

                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    Toast.makeText(ChatActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });


                }

            }


        }


    }
}