package com.baby.cy.babyfun;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Potato on 15/10/27.
 */
public class NetwordOperate {
    private Context context;
    public NetwordOperate(Context context){
        this.context = context;
        Log.d("Tomato","context------>"+context.toString());
    }
    public RequestQueue mQueue;

    public void getParams(String url){
        mQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        mQueue.add(jsonObjectRequest);
    }



    public void postLoginParams(String url, final String user_name, final String user_password){
        Log.d("Tomato","aaaaa");
        mQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "response -> " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> data = new HashMap<String, String>();
                data.put("user_name", user_name);
                data.put("user_password", user_password);
                return data;
            }
        };
        mQueue.add(stringRequest);
    }


}
