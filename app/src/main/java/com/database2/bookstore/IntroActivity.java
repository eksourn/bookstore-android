package com.database2.bookstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IntroActivity extends AppCompatActivity {
    private static final String TAG = "Intro Activity";
    TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        loading = (TextView)findViewById(R.id.textViewLoading);


        loading.setText(getString(R.string.loading));
        new CountDownTimer(3000, 1000){
            String message = getString(R.string.loading);

            @Override
            public void onTick(long millisUntilFinished) {
                message += " . ";
                loading.setText(message);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(IntroActivity.this, SearchActivity.class);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO: search logic will go here

                        try{
                            // Log.i(TAG, "onResponse: " + response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                // TODO: put whatever the results you get from server

                                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                                intent.putExtra("data", response);
                                IntroActivity.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("Sign In Failed").setNegativeButton("Retry",
                                        null).create().show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                Map<String, String> map = new HashMap<>();
                map.put("all", "all");
                QueryRequest queryRequest = new QueryRequest(map,getString(R.string.url) + "get" +
                        "-all-books" +
                        ".php", responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(queryRequest);
            }
        }.start();
    }
}