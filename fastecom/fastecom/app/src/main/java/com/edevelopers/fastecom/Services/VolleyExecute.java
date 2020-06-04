package com.edevelopers.fastecom.Services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.edevelopers.fastecom.models.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VolleyExecute {

    public interface VolleyCallback
    {
        void onSuccess(ArrayList<Team> teams);
    }

    public static void volleydynamicgetfun(Context context, String col1, String col2, String col3, String col4, String col5, final VolleyCallback callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://wservice.skyinfy.com/hdfiles/getlist";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("col1", col1);
            jsonObject.accumulate("col2", col2);
            jsonObject.accumulate("col3", col3);
            jsonObject.accumulate("col4", col4);
            jsonObject.accumulate("col5", col5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            customRequest jsonObjectRequest = new customRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try{
                        for(int i = 0;i < response.length();i++){
                            JSONObject explrObject = response.getJSONObject(i);
                            fed.add(new Team(explrObject.getString("col1"), explrObject.getString("col2"), explrObject.getString("col3"), explrObject.getString("col4"), explrObject.getString("col5"), false));
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    callback.onSuccess(fed);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   /* Toast.makeText(context,""+error.getMessage(),Toast.LENGTH_LONG).show();*/
                }
            });

            queue.add(jsonObjectRequest);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
