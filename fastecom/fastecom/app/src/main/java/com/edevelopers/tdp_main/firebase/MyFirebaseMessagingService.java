package com.edevelopers.tdp_main.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.edevelopers.tdp_main.MainActivity;
import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";


    private final String ADMIN_CHANNEL_ID ="admin_channel";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sgen.CTOKEN = token;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        if(sgen.ATOKEN.equals(sgen.CTOKEN)) {
            final Intent intent = new Intent(this, MainActivity.class);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationID = new Random().nextInt(3000);

      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupChannels(notificationManager);
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);


            Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),(R.mipmap.ic_launcher)))
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true)
                    .setSound(notificationSoundUri)
                    .setContentIntent(pendingIntent);

            //Set notification color to match your app color template
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
            }
            notificationManager.notify(notificationID, notificationBuilder.build());
        }
//    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager){
        CharSequence adminChannelName = "New notification";
        String adminChannelDescription = "Device to devie notification";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.YELLOW);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    public static void functionnewinstance(){
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        sgen.CTOKEN = token;
                        FunctionsaveToServer(token);
                    }
                });
    }

    public static void FunctionsaveToServer(String token){
        if(sgen.Login_Id != "" && sgen.Login_Id1 != "" && token != ""){
            String Query = "UPDATE C_USER SET FTOKEN = '"+token+"' WHERE ID = '"+ sgen.Login_Id1+"' AND C_ID = '"+ sgen.Login_Id +"'; ";
            try{
                sgen.exc_sqlite(sgen.Context,"UPDATE C_USER SET FTOKEN = '"+token+"' WHERE ID = '"+ sgen.Login_Id1+"' AND C_ID = '"+ sgen.Login_Id +"';");
            }catch (Exception e){
                e.printStackTrace();
            }
            VolleyExecute.volleydynamicsavefun(sgen.Context, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Team> teams) {
                    Toast.makeText(sgen.Context,"Token: "+teams.get(0).getcol1(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}