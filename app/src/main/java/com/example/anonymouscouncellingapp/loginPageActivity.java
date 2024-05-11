package com.example.anonymouscouncellingapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anonymouscouncellingapp.links.Links;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class loginPageActivity extends AppCompatActivity {
    private OkHttpClient client;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.client_username);
        passwordEditText = findViewById(R.id.client_password);
        client = new OkHttpClient();

    }

    public void toRegistration(View view) {
        startActivity(new Intent(loginPageActivity.this, registerPageActivity.class));
        finish();
    }

    public void loginUser(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {
            RequestBody requestBody = new FormBody.Builder()
                    .add("name", username)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url(Links.login)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(loginPageActivity.this, "Failed to login", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        // Login successful, start HomeActivity
                        startActivity(new Intent(loginPageActivity.this, HomeActivity.class));
                        finish(); // Finish current activity to prevent going back to login page
                    } else {
                        runOnUiThread(() -> Toast.makeText(loginPageActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show());
                    }
                }
            });
        } else {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        }
    }
}
