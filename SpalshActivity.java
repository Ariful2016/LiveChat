package com.creativeitinstitute.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SpalshActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {


            }

            @Override
            public void onFinish() {

                if (firebaseUser != null) {

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                } else {

                    startActivity(new Intent(getApplicationContext(), StartActivity.class));
                    finish();

                }


            }
        }.start();
    }
}