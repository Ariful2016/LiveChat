package com.creativeitinstitute.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText userName, email, pass;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    CheckBox rememberMe;

    SharedPreferences sharedPreferences;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");


        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        rememberMe = findViewById(R.id.rememberMe);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);


        findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString().trim();
                String Pass = pass.getText().toString();
                String UserName = userName.getText().toString();

                if (!Email.equals("") && !Pass.equals("")) {

                    progressDialog.setTitle("Validating Email...");
                    progressDialog.setMessage("Please Wait...!");
                    progressDialog.show();


                    registerNowWithOutEmailValidation(UserName, Email, Pass);


                } else {
                    email.setError("Registration Error");
                    pass.setError("Registration Error");
                }


            }
        });


    }


    private void registerNowWithOutEmailValidation(final String UserName, final String Email, final String Pass) {

        firebaseAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                String uid = "";

                if (firebaseUser != null) {
                    uid = firebaseUser.getUid();
                }

                if (task.isSuccessful()) {
                    HashMap<String, Object> userMap = new HashMap<>();

                    userMap.put("name", UserName);
                    userMap.put("email", Email);
                    userMap.put("password", Pass);
                    userMap.put("uid", uid);
                    userMap.put("imgurl", "null");

                    databaseReference.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();


                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                        }
                    });


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Toast.makeText(RegisterActivity.this, "Error :" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}