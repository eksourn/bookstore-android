package com.database2.bookstore;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class QueryRequest extends StringRequest {
    private static final String TAG = "Query Request";
    private Map<String, String> args;
    private static Response.ErrorListener err = new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error){
            Log.d("please","Error listener response: " + error.getMessage());
        }
    };

    public QueryRequest(Map<String, String> dataMap, String url,
                        Response.Listener<String> listener){
        super(Method.POST, url, listener, err);
        // args = new HashMap<String, String>();
        args = dataMap;
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return args;
    }
}