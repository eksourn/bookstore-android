package com.database2.bookstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    String[] search_by = {"Title", "Author", "Publisher", "ISBN", "Genre"};
    HashMap<String, String> search_map = new HashMap<>();
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    Button btnLogin;
    EditText editTextSerachBy;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextSerachBy = (EditText)findViewById(R.id.editTextSearchBy);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewBookList);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete_txt);
        btnLogin = (Button)findViewById(R.id.btnLogIn);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, search_by);
        autoCompleteTextView.setAdapter(arrayAdapter);

        search_map.put("Title", "title");
        search_map.put("Author", "author_name");
        search_map.put("Publisher", "publisher_name");
        search_map.put("ISBN", "isbn");
        search_map.put("Genre", "genre");


        Intent intent = getIntent();
        final String data = intent.getStringExtra("data");

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

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String search_value = editTextSerachBy.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO: search logic will go here
                        if(search_value.isEmpty()){

                            Toast.makeText(getApplicationContext(), "Empty Search Value",
                                    Toast.LENGTH_LONG).show();
                            autoCompleteTextView.setText("");
                        } else {
                            try {
                                Log.i(TAG, "onResponse: " + response);
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {

                                    // TODO: put whatever the results you get from server

                                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                                    intent.putExtra("data", response);
                                    intent.putExtra("keyword", search_value);
                                    SearchActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                                    builder.setMessage("No Results Found").setNegativeButton(
                                            "Retry",
                                            null).create().show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                Map<String, String> map = new HashMap<>();
                map.put(search_map.get(item), search_value);
                if(!search_value.isEmpty()){
                    QueryRequest queryRequest = new QueryRequest(map,getString(R.string.url) +
                            "search-books.php",
                            responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
                    queue.add(queryRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Empty Search Value",
                            Toast.LENGTH_LONG).show();
                    new CountDownTimer(1000, 200){

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            autoCompleteTextView.setText("");
                        }
                    }.start();

                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                SearchActivity.this.startActivity(intent);
            }
        });

    }
}