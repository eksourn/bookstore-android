package com.database2.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText editTextUsername, editTextPassword;
    Button submitUsers, submitPubs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextUsername.setText("");
        editTextPassword.setText("");
        submitUsers = (Button) findViewById(R.id.btnSubmitUsers);
        submitPubs = (Button)findViewById(R.id.btnSubmitPubs);

        submitUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password",
                            Toast.LENGTH_LONG).show();
                }else{
                    performRequest(username, password, true);
                }

            }
        });

        submitPubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password",
                            Toast.LENGTH_LONG).show();
                }else{
                    performRequest(username, password, false);
                }
            }
        });
    }

    private void performRequest(String username, String password, boolean isUser){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        Intent intent = new Intent(getApplicationContext(),
                                OrderHistoryActivity.class);
                        intent.putExtra("data", response);
                        if(!isUser) intent.putExtra("publisher", "true");
                        LoginActivity.this.startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "User/ Publisher doesn't exist",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Map<String, String> map = new HashMap<>();
        String php = "";
        if(isUser){
            map.put("username", username);
            map.put("password", password);
            php = "user-login.php";
        } else{
            map.put("pub_username", username);
            map.put("pub_password", password);
            php = "publisher-login.php";
        }
        QueryRequest queryRequest = new QueryRequest(map, getString(R.string.url)+php,
                responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(queryRequest);
    }
}