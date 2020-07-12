package com.edevelopers.tdp_main.activities_M;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SignupActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    private TextInputEditText fname,lname,email,mobile,password,cpassword;
    private Button btn_signup;
    private TextView login_link,checkemail,checkmobile,chkpassword;
    private boolean vemail = true,vmobile = true,vpassword = false;
    private String eml = "";


    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

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

        setContentView(R.layout.activity_signup);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        fname = findViewById(R.id.input_fname);
        lname = findViewById(R.id.input_lname);
        email = findViewById(R.id.input_email);
        mobile = findViewById(R.id.input_Mobile);
        password = findViewById(R.id.input_password);
        cpassword = findViewById(R.id.input_cpassword);
        btn_signup = findViewById(R.id.btn_signup);
        login_link = findViewById(R.id.link_login);
        checkemail = findViewById(R.id.chkemail);
        checkmobile = findViewById(R.id.chkmobile);
        chkpassword = findViewById(R.id.chkpassword);

        functioncheckevent();

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,SigninActivity.class));
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = fname.getText().toString();
                String s2 = lname.getText().toString();
                String s3 = email.getText().toString();
                String s4 = mobile.getText().toString();
                String s5 = password.getText().toString();
                String s6 = cpassword.getText().toString();
                if(s1.equals("")||s2.equals("")||s4.equals("")||s5.equals("")||s6.equals("")){
                    Toast.makeText(SignupActivity.this,"Fields Are Empty Please fill",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(vemail){
                        if(vmobile){
                            if(vpassword){
                                saveuser();
                            }
                            else{
                                Toast.makeText(SignupActivity.this,"Valid The Fields",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignupActivity.this,"Valid The Fields",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignupActivity.this,"Valid The Fields",Toast.LENGTH_SHORT).show();
                    }
                }
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
    }

    private void saveuser(){
        if(email.getText().toString().equals("")){
            eml = "0";
        }
        else
        {
            eml = email.getText().toString();
        }
        VolleyExecute.volleydynamicgetfun(SignupActivity.this, sgen.sp_GETPASSWORDENCRYPTED, password.getText().toString(), "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                String query = "CALL AUTOC_USER('" + teams.get(0).getcol2() + "','" + fname.getText().toString() + "','0','CUS','"+lname.getText().toString()+"'," +
                        "'" + eml + "','+91"+mobile.getText().toString()+"','N','',''," +
                        "'" + teams.get(0).getcol3() + "',STR_TO_DATE('" + sgen.gettodaydate_timemysql() + "','%m/%d/%Y %r'),'Location')";

                VolleyExecute.volleydynamicsavefun(SignupActivity.this, "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Team> teams) {
                        if(teams.get(0).getcol1().equals("Saved")){
                            String qry = "Update C_USER SET SIGNIN = 'M' Where EMAIL = '"+email.getText().toString()+"' or MOBILE = '"+mobile.getText().toString()+"';";
                            VolleyExecute.volleydynamicsavefun(SignupActivity.this, "-", qry, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                                @Override
                                public void onSuccess(ArrayList<Team> teams) {
                                    if(teams.get(0).getcol1().equals("Saved")){
                                        if(email.getText().toString().equals("")){
                                            Intent intent = new Intent(SignupActivity.this,MobileVerification.class);
                                            intent.putExtra("Email",email.getText().toString());
                                            intent.putExtra("Mobile","+91"+mobile.getText().toString());
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Intent intent = new Intent(SignupActivity.this,EmailVerification.class);
                                            intent.putExtra("Email",email.getText().toString());
                                            intent.putExtra("Mobile",mobile.getText().toString());
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }



    private void functioncheckevent(){
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 1) {
                    checkemailandmobile();
                }
            }
        });

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >= 10){
                    checkemailandmobile();
                }
            }
        });
        cpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwordss = password.getText().toString();
                if(passwordss.equals(s.toString())){
                    chkpassword.setVisibility(View.GONE);
                    vpassword = true;
                }
                else{
                    vpassword = false;
                    chkpassword.setVisibility(View.VISIBLE);
                    chkpassword.setText("Password Not Matched");
                }
            }
        });
    }

    private void checkemailandmobile(){
        String mobilee = "+91" + mobile.getText().toString();
        String Email = email.getText().toString();
        if(!Email.equals("")){
            String query = "SELECT EMAIL AS COL1 FROM M_USER WHERE EMAIL = '"+Email+"' AND TYPE = 'SHU';";
            VolleyExecute.volleycheckemail(SignupActivity.this, "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Team> teams) {
                    if(teams.get(0).getcol1().toString().toUpperCase().equals("TRUE")){
                        checkemail.setVisibility(View.VISIBLE);
                        checkemail.setText("Email Already exists");
                        vemail = false;
                    }
                    else{
                        checkemail.setVisibility(View.GONE);
                        vemail = true;
                    }
                }
            });
        }
        if(!mobilee.equals("")){
            String query = "SELECT MOBILE AS COL1 FROM M_USER WHERE MOBILE = '"+mobilee+"' AND TYPE = 'SHU';";
            VolleyExecute.volleycheckemail(SignupActivity.this, "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Team> teams) {
                    if(teams.get(0).getcol1().toString().toUpperCase().equals("TRUE")){
                        checkmobile.setVisibility(View.VISIBLE);
                        checkmobile.setText("Mobile No. Already exists");
                        vmobile = false;
                    }
                    else{
                        checkmobile.setVisibility(View.GONE);
                        vmobile = true;
                    }
                }
            });
        }
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
}