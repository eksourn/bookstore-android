package com.database2.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    TextView key;
    private static final String TAG = "SearchResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchResults);
        key = key = (TextView)findViewById(R.id.textViewKey);
        Intent intent = getIntent();

        final String data = intent.getStringExtra("data");
        final String keyword = intent.getStringExtra("keyword");

        key.setText("Results for "+ "\""+keyword+"\"");

        ArrayList<JSONObject> objList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(data);
            for(int i = 0; i < jsonResponse.length()-1; i++){
                objList.add(jsonResponse.getJSONObject(Integer.toString(i)));
            }

            // Log.i(TAG, "passed Intent: jsonResponse " + jsonResponse);
            recyclerViewAdapter = new RecyclerViewAdapter(objList);
            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(recyclerViewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}