package com.database2.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private static final String TAG = "OrderHistoryActivity";
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    TextView textViewFlex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewOrdres);
        textViewFlex = (TextView)findViewById(R.id.textViewFlex) ;

        Intent intent = getIntent();
        final String data = intent.getStringExtra("data");


        try{
            final String isPub = intent.getStringExtra("publisher");
            if(isPub.equals("true")){
                textViewFlex.setText("Published Books");
            }
        }catch (Exception e){

        }

        ArrayList<JSONObject> objList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(data);

            for(int i = 0; i < jsonResponse.length()-1; i++){
                objList.add(jsonResponse.getJSONObject(Integer.toString(i)));
            }

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