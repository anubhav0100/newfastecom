package com.edevelopers.fastecom.Services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.edevelopers.fastecom.models.Product;
import com.edevelopers.fastecom.models.Productcat;
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

    public static void volleydynamicsendalert(Context context, String mobileid, String title, String body, String comp, String unit, final VolleyCallback callback){
        RequestQueue queue= Volley.newRequestQueue(context);
        final ArrayList<Team> fed = new ArrayList<>();
        String url = "http://wservice.skyinfy.com/hdfiles/sendalert";

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
        String url = "http://wservice.skyinfy.com/hdfiles/getDataProduct";

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
        String url = "http://wservice.skyinfy.com/hdfiles/getDataProductcate";

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

}
