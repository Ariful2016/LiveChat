package com.creativeitinstitute.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.d("Life Cycle", "OnCreate");


    }

    public void Register(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();

    }

    public void signin(View view) {
        startActivity(new Intent(getApplicationContext(), SigninActivity.class));
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Life Cycle", "OStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Life Cycle", "On resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("Life Cycle", "ON Pause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Life Cycle", "On Stop");


    }


    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("Life Cycle", "OnRestart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Life Cycle", "OnDestroy");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}