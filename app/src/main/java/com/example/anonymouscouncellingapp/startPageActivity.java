package com.example.anonymouscouncellingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class startPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toLoginPage(View view) {
        startActivity(new Intent(startPageActivity.this, loginPageActivity.class));
        finish();
    }

    public void toRegistrationPage(View view) {
        startActivity(new Intent(startPageActivity.this, registerPageActivity.class));
        finish();
    }
}