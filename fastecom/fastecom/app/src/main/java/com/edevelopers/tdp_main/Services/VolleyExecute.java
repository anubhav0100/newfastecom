package com.edevelopers.tdp_main.Services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.edevelopers.tdp_main.models.Product;
import com.edevelopers.tdp_main.models.Productcat;
import com.edevelopers.tdp_main.models.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class VolleyExecute {

    public interface VolleyCallback
    {
        void onSuccess(ArrayList<Team> teams);
    }

    public interface VolleyCallbackproducts
    {
        void onSuccess(ArrayList<Product> products);
    }

    public interface VolleyCallbackproductcat
    {
        void onSuccess(ArrayList<Productcat> productcats);
    }

    public static void volleydynamicgetfun(Context context, String col1, String col2, String col3, String col4, String col5, final VolleyCallback callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://ecom.aasinfotech.com/api/WebApi/getData";

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

    public static void volleycheckemail(Context context, String col1, String col2, String col3, String col4, String col5, final VolleyCallback callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://ecom.aasinfotech.com/api/WebApi/getemailmobilevalid";

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

    public static void volleydynamicloginfun(Context context, String col1, String col2, String col3, String col4, String col5, final VolleyCallback callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://ecom.aasinfotech.com/api/WebApi/login";

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

    public static void volleydynamicsavefun(Context context, String col1, String col2, String col3, String col4, String col5, final VolleyCallback callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://ecom.aasinfotech.com/api/WebApi/saveData";

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

    public static void volleydynamicsendalert(Context context, String mobileid, String title, String body, String comp, String unit, final VolleyCallback callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://ecom.aasinfotech.com/api/WebApi/sendalert";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("col1", mobileid);
            jsonObject.accumulate("col2", title);
            jsonObject.accumulate("col3", body);
            jsonObject.accumulate("col4", comp);
            jsonObject.accumulate("col5", unit);
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

    public static void volleydynamicgetfunproduct(Context context, String col1, String col2, String col3, String col4, String col5, final VolleyCallbackproducts callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Product> fed = new ArrayList<>();
        String url = "http://ecom.aasinfotech.com/api/WebApi/getDataProduct";

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
                            fed.add(new Product(explrObject.getString("PC_ID"),
                                    explrObject.getString("PC_NAME"),
                                    explrObject.getString("DESCRIPTION"),
                                    explrObject.getString("META_TAG"),
                                    explrObject.getString("META_DESCRIPTION"),
                                    explrObject.getString("META_KEYWORD"),
                                    explrObject.getString("PC_TYPE"),
                                    explrObject.getString("PC_PRIORITY"),
                                    explrObject.getString("PRODUCT_CATEGORY_LEVEL"),
                                    explrObject.getString("PC_MODEL"),
                                    explrObject.getString("PC_SKU"),
                                    explrObject.getString("PC_UPC"),
                                    explrObject.getString("PC_EAN"),
                                    explrObject.getString("PC_JAN"),
                                    explrObject.getString("PC_ISBN"),
                                    explrObject.getString("PC_MPN"),
                                    explrObject.getString("PC_LOCATION"),
                                    explrObject.getString("PC_QUANTITY"),
                                    explrObject.getString("PC_STOCK_STATUS_ID"),
                                    explrObject.getString("PC_IMG_ID"),
                                    explrObject.getString("PC_MANUFACTURING_ID"),
                                    explrObject.getString("PC_SHIPPING"),
                                    explrObject.getString("PC_PRICE"),
                                    explrObject.getString("PC_POINTS"),
                                    explrObject.getString("PC_TAX_CLASS_ID"),
                                    explrObject.getString("PC_DATA_AVAILABLE"),
                                    explrObject.getString("PC_WEIGHT"),
                                    explrObject.getString("PC_WEIGHT_CLASS_ID"),
                                    explrObject.getString("PC_LENGTH"),
                                    explrObject.getString("PC_WIDTH"),
                                    explrObject.getString("PC_HEIGHT"),
                                    explrObject.getString("PC_LENGTH_CLASS_ID"),
                                    explrObject.getString("PC_SUBTRACT"),
                                    explrObject.getString("PC_MINIMUM"),
                                    explrObject.getString("PC_SORT_ORDER"),
                                    explrObject.getString("PC_STATUS"),
                                    explrObject.getString("CREATED_DATE"),
                                    explrObject.getString("WISHID"),
                                    explrObject.getString("CARTID"),
                                    false));
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

    public static void volleydynamicgetfunproductcat(Context context, String col1, String col2, String col3, String col4, String col5, final VolleyCallbackproductcat callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Productcat> fed = new ArrayList<>();
        String url = "http://ecom.aasinfotech.com/api/WebApi/getDataProductcate";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("col1", col1);
            jsonObject.accumulate("col2", col2);// query
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
                            fed.add(new Productcat(
                                    explrObject.getString("PC_ID"),
                                    explrObject.getString("PC_NAME"),
                                    explrObject.getString("PC_DEFAULT"),
                                    explrObject.getString("PC_LEVEL"),
                                    explrObject.getString("PC_LEVEL_NAME1"),
                                    explrObject.getString("PC_LEVEL_NAME2"),
                                    explrObject.getString("PC_LEVEL_NAME3"),
                                    explrObject.getString("PC_LEVEL_NAME4"),
                                    explrObject.getString("PC_ORDER"),
                                    explrObject.getString("PC_UID"),
                                    explrObject.getString("CREATED_DATE"),
                                    explrObject.getString("PC_TYPE"),
                                    false
                            ));
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

    public static String execute_transaction(String col1, String col2, String col3, String col4, String col5) {
        InputStream inputStream = null;
        ArrayList<Team> fed = new ArrayList<>();
        String result = "";

        boolean myval = false;
        String url1 = "http://ecom.aasinfotech.com/api/WebApi/saveData";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(url1);

            urlConnection = (HttpURLConnection) url.openConnection();
            //start 1
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);
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
            String json = jsonObject.toString();
            String jsonInputString = json;
            try(OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
                result="";
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
                    result += responseLine;
                }

                if (result != null) {

                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject explrObject = jsonArray.getJSONObject(i);
                            result = explrObject.getString("col1");
                        }
                    } catch (Exception e) {
                        result = e.toString();
                    }
                } else
                    result = "Did not work!";
            }
            //end 1

        } catch (Exception e) {
            e.printStackTrace();
            //sgen.finalresult = e.toString().trim();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
//        try {
//            HttpParams httpParameters = new BasicHttpParams();
//            // Set the timeout in milliseconds until a connection is established.
//            // The default value is zero, that means the timeout is not used.
//            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
//            HttpClient httpclient = new DefaultHttpClient(httpParameters);
//            HttpPost httpPost = new HttpPost(OPERATION_NAME);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("col1", cocd);
//            jsonObject.accumulate("col2", query1);
//            jsonObject.accumulate("col3", "-");
//            jsonObject.accumulate("col4", "-");
//            jsonObject.accumulate("col5", "-");
//            String json = jsonObject.toString();
//            StringEntity se = new StringEntity(json);
//            httpPost.setEntity(se);
//            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
//            HttpResponse httpResponse = httpclient.execute(httpPost);
//            inputStream = httpResponse.getEntity().getContent();
//            if (inputStream != null) {
//                String wsresult = convertInputStreamToString(inputStream);
//                try {
//                    JSONArray jsonArray = new JSONArray(wsresult);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject explrObject = jsonArray.getJSONObject(i);
//                        result = explrObject.getString("col1");
//                    }
//                } catch (Exception e) {
//                    result = e.toString();
//                }
//            } else
//                result = "Did not work!";
//
//        } catch (Exception exception) {
//
//            sgen.finalresult = exception.toString().trim();
//
//        }
        return result;

    }

    public static void volleydynsendmessage(Context context, String col1, String col2, String col3, String col4, String col5, final VolleyCallback callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://ecom.aasinfotech.com/api/WebApi/sendMessage";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("col1", col1); //Msg or Email
            jsonObject.accumulate("col2", col2); //EMail Id or Mobile Number
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
