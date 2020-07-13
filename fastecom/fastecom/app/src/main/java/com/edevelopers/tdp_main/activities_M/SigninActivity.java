package com.edevelopers.tdp_main.activities_M;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edevelopers.tdp_main.MainActivity;
import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.firebase.MyFirebaseMessagingService;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SigninActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;


    private int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;

    private TextInputEditText Usrname,password;
    private Button SignIn;
    private TextView CreateAccount;
    private Dialog dialog;
    SharedPreferences sharedpreferences;


    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        Usrname = (TextInputEditText) findViewById(R.id.Usrname);
        password = (TextInputEditText) findViewById(R.id.password);
        SignIn = (Button) findViewById(R.id.btn_login);
        CreateAccount = (TextView) findViewById(R.id.link_signup);

        StrictMode.setThreadPolicy(sgen.policy); //EQUAL OR ABOVE ANDROID 9
        dialog = new Dialog(SigninActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.reclycer_data);
        dialog.setCancelable(false);

        try{
            sgen.create_tables(SigninActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this,SignupActivity.class));
                finish();
            }
        });

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String USr = Usrname.getText().toString();
                String pass = password.getText().toString();
                if(!USr.equals("")&& !pass.equals("")){
                    SignIn.setEnabled(false);
                    ServerSignin(USr,pass);
                }
            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
    }

    private String SqlLogin(){
        String status = "Failed";
        sharedpreferences = getApplicationContext().getSharedPreferences(sgen.mypreferencegroup,Context.MODE_PRIVATE);
        String UserName = sharedpreferences.getString(sgen.sp_Email, "");
        String UserName1 = sharedpreferences.getString(sgen.sp_Mobile, "");
        try{

            String Query = "SELECT ID||'!~!~!'||+C_ID||'!~!~!'||+CF_NAME||'!~!~!'||+CL_NAME||'!~!~!'||+EMAIL||'!~!~!'||+MOBILE as col1," +
                    "FTOKEN||'!~!~!'||+ACTIVATION_CODE||'!~!~!'||+LOCATION||'!~!~!'||+SIGNIN as col2 ," +
                    " IS_EMAILVERIFIED as col3," +
                    "IS_MOBILEVERIFIED as col4 , LOGIN_STATUS as col5" +
                    " FROM C_USER WHERE EMAIL = '"+UserName+"' OR MOBILE = '"+UserName1+"';";
            ArrayList<Team> ct = new ArrayList<>();
            ct = sgen.getdata_fromsql(SigninActivity.this,Query);

            for (int l = 0; l < ct.size(); l++) {
                Team tt = ct.get(l);
                sgen.Login_Id1 = String.valueOf(Integer.parseInt(tt.getcol1().split(sgen.textseprator)[0].toString()));
                sgen.Login_Id = String.valueOf(Integer.parseInt(tt.getcol1().split(sgen.textseprator)[1].toString()));
                sgen.User_Id = tt.getcol1().split(sgen.textseprator)[2].toString();
                sgen.Email = tt.getcol1().split(sgen.textseprator)[4].toString();
                sgen.Mobile = tt.getcol1().split(sgen.textseprator)[5].toString();
                sgen.FTOKEN = tt.getcol2().split(sgen.textseprator)[0].toString();
                sgen.User_Name = tt.getcol1().split(sgen.textseprator)[2].toString()+" "+tt.getcol1().split(sgen.textseprator)[3].toString();
                sgen.LOGIN_STATUS = tt.getcol5();
                status = sgen.LOGIN_STATUS;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if(!status.equals("Failed")){
            dbAddress();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(sgen.sp_Login_Id, sgen.Login_Id);
            editor.putString(sgen.sp_Login_Id1, sgen.Login_Id1);
            editor.putString(sgen.sp_User_Id, sgen.User_Id);
            editor.putString(sgen.sp_User_Name, sgen.User_Name);
            editor.putString(sgen.sp_FTOKEN, sgen.FTOKEN);
            editor.putString(sgen.sp_Email, sgen.Email);
            editor.putString(sgen.sp_Mobile, sgen.Mobile);
            editor.putString(sgen.sp_LOGIN_STATUS, sgen.LOGIN_STATUS);
            editor.commit();
        }

        return status;
    }


    @Override
    protected void onStart() {
        super.onStart();
        String SqlLoginstr = SqlLogin();
        if(!SqlLoginstr.equals("Failed")){
            SignIn.setEnabled(true);

            if(sgen.Mobile.equals("0")){
                startActivity(new Intent(SigninActivity.this,AddMobileActivity.class));
                finish();
            }
            if(sgen.activty_logincallnew.equals("PlaceOrderActivity")){
                startActivity(new Intent(SigninActivity.this,PlaceOrderActivity.class));
                finish();
            }
            else if(sgen.activty_logincall.equals("CartActivity")){
                startActivity(new Intent(SigninActivity.this,CartActivity.class));
                finish();
            }
            else{
                startActivity(new Intent(SigninActivity.this,MainLandingActivity.class));
                finish();
            }
        }
    }


    private void ServerSignin(String Username, String password) {
        dialog.show();
        sharedpreferences = getApplicationContext().getSharedPreferences(sgen.mypreferencegroup,
                Context.MODE_PRIVATE);
        String Query = "SELECT CONCAT(ID,'!~!~!',C_ID,'!~!~!',CF_NAME,'!~!~!',CL_NAME,'!~!~!',EMAIL,'!~!~!',MOBILE) AS COL1" +
                ",CONCAT(FTOKEN,'!~!~!',ACTIVATION_CODE,'!~!~!',LOCATION,'!~!~!',SIGNIN) AS COL2,IS_EMAILVERIFIED AS COL3" +
                ",IS_MOBILEVERIFIED AS COL4,C_PWD AS COL5 FROM C_USER WHERE TYPE = 'CUS' AND EMAIL = '"+Username+"' OR TYPE = 'CUS' AND MOBILE = '%"+Username+"%';";
        VolleyExecute.volleydynamicloginfun(SigninActivity.this, password, Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                String status = "Failed";
                String sq = "";
                for (int l = 0; l < teams.size(); l++) {
                    Team tt = teams.get(l);
                    sgen.Login_Id1 = String.valueOf(Integer.parseInt(tt.getcol1().split(sgen.textseprator)[0].toString()));
                    sgen.Login_Id = String.valueOf(Integer.parseInt(tt.getcol1().split(sgen.textseprator)[1].toString()));
                    sgen.User_Id = tt.getcol1().split(sgen.textseprator)[2].toString();
                    sgen.Email = tt.getcol1().split(sgen.textseprator)[4].toString();
                    sgen.Mobile = tt.getcol1().split(sgen.textseprator)[5].toString();
                    sgen.FTOKEN = tt.getcol2().split(sgen.textseprator)[0].toString();
                    sgen.User_Name = tt.getcol1().split(sgen.textseprator)[2].toString()+" "+tt.getcol1().split(sgen.textseprator)[3].toString();
                    sgen.LOGIN_STATUS = tt.getcol5();
                    status = sgen.LOGIN_STATUS;
                    sq = "INSERT INTO C_USER(ID,C_ID," +
                            "CF_NAME,CL_NAME," +
                            "EMAIL,MOBILE," +
                            "FTOKEN," +
                            "IS_EMAILVERIFIED,IS_MOBILEVERIFIED," +
                            "ACTIVATION_CODE,LOCATION," +
                            "EMAILOTP,MOBILEOTP," +
                            "SIGNIN,LOGIN_STATUS)" +
                            "VALUES(" +
                            "'"+String.valueOf(Integer.parseInt(tt.getcol1().split(sgen.textseprator)[0].toString()))+"','"+String.valueOf(Integer.parseInt(tt.getcol1().split(sgen.textseprator)[1].toString()))+"'," +
                            "'"+tt.getcol1().split(sgen.textseprator)[2].toString()+"','"+tt.getcol1().split(sgen.textseprator)[3].toString()+"'," +
                            "'"+tt.getcol1().split(sgen.textseprator)[4].toString()+"','"+tt.getcol1().split(sgen.textseprator)[5].toString()+"'," +
                            "'"+tt.getcol2().split(sgen.textseprator)[0].toString()+"'," +
                            "'"+tt.getcol3()+"','"+tt.getcol4()+"'," +
                            "'"+tt.getcol2().split(sgen.textseprator)[1].toString()+"','"+tt.getcol2().split(sgen.textseprator)[2].toString()+"'," +
                            "'0','0'," +
                            "'"+tt.getcol2().split(sgen.textseprator)[3].toString()+"','"+tt.getcol5()+"'" +
                            ")";
                }

                if(!status.equals("Failed")){
                    dbAddress();
                    LoginFirebase(sq);
                }else{
                    dialog.hide();
                    SignIn.setEnabled(true);
                    functionAlert();
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                final String personName = acct.getDisplayName();
                final String personGivenName = acct.getGivenName();
                final String personFamilyName = acct.getFamilyName();
                final String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                String query = "SELECT EMAIL AS COL1 FROM C_USER WHERE EMAIL = '"+personEmail+"' AND TYPE = 'CUS';";
                VolleyExecute.volleycheckemail(SigninActivity.this, "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Team> teams) {
                        if(teams.get(0).getcol1().toString().toUpperCase().equals("TRUE")){
                          ServerSignin(personEmail,personEmail);
                        }
                        else{
                            VolleyExecute.volleydynamicgetfun(SigninActivity.this, sgen.sp_GETPASSWORDENCRYPTED, personEmail, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                                @Override
                                public void onSuccess(ArrayList<Team> teams) {
                                    String query = "CALL AUTOC_USER('" + teams.get(0).getcol2() + "','" + personGivenName + "','0','CUS','"+personFamilyName+"'," +
                                            "'" + personEmail + "','0','N','1',''," +
                                            "'" + teams.get(0).getcol3() + "',STR_TO_DATE('" + sgen.gettodaydate_timemysql() + "','%m/%d/%Y %r'),'Location')";

                                    VolleyExecute.volleydynamicsavefun(SigninActivity.this, "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                                        @Override
                                        public void onSuccess(ArrayList<Team> teams) {
                                            if(teams.get(0).getcol1().equals("Saved")){
                                                String qry = "Update C_USER SET SIGNIN = 'G' Where Email = '"+personEmail+"';";
                                                VolleyExecute.volleydynamicsavefun(SigninActivity.this, "-", qry, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                                                    @Override
                                                    public void onSuccess(ArrayList<Team> teams) {
                                                        if(teams.get(0).getcol1().equals("Saved")){
                                                            signOut(personEmail);
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }

            // Signed in successfully, show authenticated UI.
            //updateUI(account);

        } catch (ApiException e) {
            e.printStackTrace();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }


    private void lastSigninAccount(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(SigninActivity.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            startActivity(new Intent(SigninActivity.this,MainActivity.class));
            finish();
        }
    }

    private void signOut(final String Email) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ServerSignin(Email,Email);
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void LoginFirebase(String Query){
        /****************************************************************************************************/

        //firebase token
        MyFirebaseMessagingService.functionnewinstance();

        String token = sgen.CTOKEN;
        try{
            SharedPreferences sharedPreferences
                    = getApplicationContext().getSharedPreferences(sgen.mypreferencegroup,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sgen.FTOKEN, token);
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }

        /****************************************************************************************************/

        String mq = "delete from C_USER";
        sgen.exc_sqlite(SigninActivity.this, mq);
        sgen.exc_sqlite(SigninActivity.this, Query);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(sgen.sp_Login_Id, sgen.Login_Id);
        editor.putString(sgen.sp_Login_Id1, sgen.Login_Id1);
        editor.putString(sgen.sp_User_Id, sgen.User_Id);
        editor.putString(sgen.sp_User_Name, sgen.User_Name);
        editor.putString(sgen.sp_FTOKEN, sgen.FTOKEN);
        editor.putString(sgen.sp_Email, sgen.Email);
        editor.putString(sgen.sp_Mobile, sgen.Mobile);
        editor.putString(sgen.sp_LOGIN_STATUS, sgen.LOGIN_STATUS);

        editor.commit();

        dialog.hide();

        if(sgen.Mobile.equals("0")){
            startActivity(new Intent(SigninActivity.this,AddMobileActivity.class));
            finish();
        }
        if(sgen.activty_logincallnew.equals("PlaceOrderActivity")){
            startActivity(new Intent(SigninActivity.this,PlaceOrderActivity.class));
            finish();
        }
        else if(sgen.activty_logincall.equals("CartActivity")){
            startActivity(new Intent(SigninActivity.this,CartActivity.class));
            finish();
        }
        else{
            startActivity(new Intent(SigninActivity.this,MainLandingActivity.class));
            finish();
        }
    }


    private void dbAddress(){
        String Query = "SELECT ID AS COL1, A_ID AS COL2, C_ID AS COL3,CONCAT(C_ADD1,C_ADD2) AS COL4, LOCATION AS COL5 FROM C_ADDRESS WHERE C_ID = '"+sgen.Login_Id+"' ORDER BY ID;";
        VolleyExecute.volleydynamicgetfun(SigninActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                sgen.Addressrecord = new ArrayList<>();
                for(int i = 0; i < teams.size(); i++){
                    sgen.Addressrecord.add(new RecyclerViewItem(
                            null,
                            ""+teams.get(i).getcol4(),
                            ""+teams.get(i).getcol1(),
                            ""+teams.get(i).getcol2(),
                            ""+teams.get(i).getcol3(),
                            ""+teams.get(i).getcol5(),
                            ""+teams.get(i).getcol5(),
                            false
                    ));
                }
            }
        });
    }

    private void functionAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Invalid Credientials")
//set message
                .setMessage("Incorect User Name or Password")
//set positive button
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //set what would happen when positive button is clicked
                    }
                })
//set negative button
                .show();
    }
}