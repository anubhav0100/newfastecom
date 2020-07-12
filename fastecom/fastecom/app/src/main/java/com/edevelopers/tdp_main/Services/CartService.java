package com.edevelopers.tdp_main.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.edevelopers.tdp_main.MainActivity;
import com.edevelopers.tdp_main.activities_M.ProductsViewActivity;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout12;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.models.Product;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;

import java.net.URL;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CartService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.edevelopers.tdp_main.Services.action.FOO";
    private static final String ACTION_BAZ = "com.edevelopers.tdp_main.Services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.edevelopers.tdp_main.Services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.edevelopers.tdp_main.Services.extra.PARAM2";

    public CartService() {
        super("CartService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CartService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CartService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String key = intent.getStringExtra("key");
            if(GridViewAdapterlayout12.CArtFILTER_ACTION_KEY.equals(key)){
                String Query = intent.getStringExtra("Query");
                String Type = intent.getStringExtra("Type");

                handelsavecart(Query,Type,intent);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */

    private void handelsavecart(String Query, String Type, final Intent intent){

        if(Type.equals("Main")){
            VolleyExecute.volleydynamicsavefun(getApplicationContext(), "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Team> teams) {
                    intent.setAction(GridViewAdapterlayout12.CArtFILTER_ACTION_KEY);
                    SystemClock.sleep(3000);
                    String echoMessage = ""+teams.get(0).getcol1();
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastMessage", echoMessage));
                }
            });
        }else{
            sgen.exc_sqlite(getApplicationContext(),Query);
            intent.setAction(GridViewAdapterlayout12.CArtFILTER_ACTION_KEY);
            SystemClock.sleep(3000);
            String echoMessage = "Sqlite Data Saved";
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastMessage", echoMessage));
        }

    }

    private void handlecartdetails(){
       String Query = "select CART_ID as col1,CONCAT(S_ID ,'!~!~!', PC_ID ,'!~!~!', QUANTITY) as col2, C_ID as col3, EDIT_DATE as col4, CREATED_DATE as col5 From CART where C_ID = '1';";
       VolleyExecute.volleydynamicgetfun(getApplicationContext(), "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
           @Override
           public void onSuccess(ArrayList<Team> teams) {
               for (int i = 0; i < teams.size(); i++){
                   String Query = "INSERT INTO CART(CART_ID," +
                           "SHOP_ID,PRODUCT_ID," +
                           "QUANTITY,C_ID,DATE1,DATE2,SYNC)"+
                           "VALUES('"+teams.get(i).getcol1()+"'," +
                           "'" + teams.get(i).getcol2().split("!~!~!")[0] + "','" + teams.get(i).getcol2().split("!~!~!")[1] + "'," +
                           "'"+teams.get(i).getcol2().split("!~!~!")[2]+"','" + teams.get(i).getcol3() + "','"+teams.get(i).getcol4()+"','"+teams.get(i).getcol5()+"'," +
                           "'Y'"+
                           ")";
                   try {
                       sgen.exc_sqlite(getApplicationContext(),Query);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
           }
       });
    }

    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
