package com.example.anonymouscouncellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;


import com.example.anonymouscouncellingapp.links.Links;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> problemsList;
    private ArrayAdapter<String> adapter;

    private ArrayList<String> filteredList; //for search bar filtering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SearchView searchView = findViewById(R.id.search_view);

        filteredList = new ArrayList<>();
        problemsList = new ArrayList<>();

        listView = findViewById(R.id.list_data);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, problemsList);
        listView.setAdapter(adapter);

        fetchProblems();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list based on user input
                filteredList.clear();
                for (String problem : problemsList) {
                    if (problem.toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(problem);
                    }
                }
                adapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_list_item_1, filteredList);
                listView.setAdapter(adapter);
                return true;
            }
        });

        // Handle item click in ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedProblem = (String) parent.getItemAtPosition(position);
            // Handle the selected problem (e.g., display in a Toast or navigate to another activity)
        });
    }

    private void fetchProblems() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Links.LOGIN_PHP) // Replace "Your_API_URL_here" with the actual URL to fetch problems
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(HomeActivity.this, "Failed to fetch problems", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    parseProblemsResponse(responseData);
                } else {
                    runOnUiThread(() -> Toast.makeText(HomeActivity.this, "Failed to fetch problems", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void parseProblemsResponse(String responseData) {
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String problemName = jsonObject.getString("problem_name");
                problemsList.add(problemName);
            }
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(HomeActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show());
        }
    }
}
