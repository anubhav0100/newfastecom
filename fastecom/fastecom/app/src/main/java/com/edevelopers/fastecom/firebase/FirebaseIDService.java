package com.edevelopers.fastecom.firebase;

import android.util.Log;

import com.edevelopers.fastecom.sgen;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.edevelopers.fastecom.sgen.CTOKEN;


public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";


    private static final String SUBSCRIBE_TO = sgen.Subscribe;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
       /* String refreshedToken = FirebaseInstanceId.getInstance().getToken();*/
        /*Log.d(TAG, "Refreshed token: " + refreshedToken);*/

        // TODO: Implement this method to send any registration to your app's servers.

        String token = FirebaseInstanceId.getInstance().getToken();
        CTOKEN = token;

//       if(sgen.ID != "") {
//           String query = "UPDATE USER_DETAILS SET FTOKEN = '"+token+"' WHERE ID = '"+sgen.ID+"'; ";
//           ArrayList<Team> savedatateam = servicesRequest.save_data(query);
//           sgen.FTOKEN = token;
//       }


        // Once the token is generated, subscribe to topic with the userId
//        if(sgen.ATOKEN.equals(sgen.CTOKEN)) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);
            Log.i(TAG, "onTokenRefresh completed with token: " + token);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        }


      /*  sendRegistrationToServer(refreshedToken);*/
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     *
     *
     */
   /* private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }*/

}
