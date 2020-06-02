package com.edevelopers.fastecom.Services;

import android.content.Context;
import android.os.AsyncTask;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.edevelopers.fastecom.models.Team;
import com.edevelopers.fastecom.sgen;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class VolleyFutureExecute extends AsyncTask<String[], Context, ArrayList<Team>> {
    final String url = "http://wservice.skyinfy.com/hdfiles/getlist";

    public VolleyFutureExecute() {
    }

    @Override
    protected ArrayList<Team> doInBackground(String[]... params) {
        RequestQueue queue= Volley.newRequestQueue(sgen.Context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://wservice.skyinfy.com/hdfiles/getlist";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("col1", params[0][0]);
            jsonObject.accumulate("col2", params[0][1]);
            jsonObject.accumulate("col3", params[0][2]);
            jsonObject.accumulate("col4", params[0][3]);
            jsonObject.accumulate("col5", "-");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        customRequest request = new customRequest(Request.Method.POST,url, jsonObject, future, future);
        queue.add(request);
        try {
            JSONArray response = future.get();// this will block
            try{
                for(int i = 0;i < response.length();i++){
                    JSONObject explrObject = response.getJSONObject(i);
                    fed.add(new Team(explrObject.getString("col1"), explrObject.getString("col2"), explrObject.getString("col3"), explrObject.getString("col4"), explrObject.getString("col5"), false));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (VolleyError.class.isAssignableFrom(e.getCause().getClass())) {
                VolleyError ve = (VolleyError) e.getCause();
                System.err.println("ve = " + ve.toString());
                if (ve.networkResponse != null) {
                    System.err.println("ve.networkResponse = " +
                            ve.networkResponse.toString());
                    System.err.println("ve.networkResponse.statusCode = " +
                            ve.networkResponse.statusCode);
                    System.err.println("ve.networkResponse.data = " +
                            new String(ve.networkResponse.data));
                }
            }
        }

        return fed;
    }
}

