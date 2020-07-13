package com.edevelopers.tdp_main.activities_M;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MobileVerification extends AppCompatActivity {

    private TextInputEditText Mobile;
    private Button resend,verify;
    private String EmailID = "",MobileNumber = "",otpmobile = "0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        Mobile = findViewById(R.id.MobileOTP);
        resend = findViewById(R.id.btn_Resend);
        verify = findViewById(R.id.btn_verify);

        Intent intent = getIntent();
        EmailID = intent.getStringExtra("Email");
        MobileNumber = intent.getStringExtra("Mobile");
        Sendotp();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sendotp();
            }
        });

        verify.setEnabled(false);
        Mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 6){
                    verify.setEnabled(true);
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otpmobile.equals(Mobile.getText().toString())){
                    verifyotp();
                }
                else{
                    Toast.makeText(MobileVerification.this,"Invalid  OTP",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private  void Sendotp(){
        VolleyExecute.volleydynsendmessage(MobileVerification.this, "Msg", MobileNumber, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
             otpmobile = teams.get(0).getcol2();
                Toast.makeText(MobileVerification.this,"OTP Send",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyotp(){
        String Query = "UPDATE C_USER SET IS_MOBILEVERIFIED = '1' WHERE MOBILE = '"+MobileNumber+"'; ";
        VolleyExecute.volleydynamicsavefun(MobileVerification.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                if(teams.get(0).getcol1().equals("Saved")){
                    if(sgen.activty_logincall.equals("CartActivity")){
                        startActivity(new Intent(MobileVerification.this,CartActivity.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(MobileVerification.this,MainLandingActivity.class));
                        finish();
                    }
                }
            }
        });
    }

}