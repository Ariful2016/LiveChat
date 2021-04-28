package com.creativeitinstitute.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    EditText email, pass;
    FirebaseAuth firebaseAuth;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        firebaseAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPass = sharedPreferences.getString("pass", "");


        if (!savedEmail.equals("") && !savedPass.equals("")) {

            email.setText(savedEmail);
            pass.setText(savedPass);


        }


        findViewById(R.id.signInBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_str = email.getText().toString().trim();
                String pass_str = pass.getText().toString().trim();


                if (!email_str.equals("") && !pass_str.equals("")) {

                    signIntoDashboard(email_str, pass_str);


                }


            }
        });


    }

    private void signIntoDashboard(String email_str, String pass_str) {


        firebaseAuth.signInWithEmailAndPassword(email_str, pass_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(SigninActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                }else {
                    Log.d("siginin", task.getException().getMessage());

                }


            }
        }

        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("siginin", e.getMessage());

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}