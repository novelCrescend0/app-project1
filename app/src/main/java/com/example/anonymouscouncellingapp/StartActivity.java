package com.example.anonymouscouncellingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toLoginPage(View view) {
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
        finish();
    }

    public void toRegistrationPage(View view) {
        startActivity(new Intent(StartActivity.this, RegisterActivity.class));
        finish();
    }
}