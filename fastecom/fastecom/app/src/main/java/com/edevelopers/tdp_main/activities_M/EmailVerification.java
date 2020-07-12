package com.edevelopers.tdp_main.activities_M;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EmailVerification extends AppCompatActivity {

    private TextInputEditText Email;
    private Button resend,verify;
    private String EmailID = "",MobileNumber = "",otpemail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        Email = findViewById(R.id.EmailOTP);
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

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otpemail.equals(Email.getText().toString())){
                    verifyotp();
                }
            }
        });

    }
    private  void Sendotp(){
        VolleyExecute.volleydynsendmessage(EmailVerification.this, "Msg", EmailID, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                otpemail = teams.get(0).getcol2();
                Toast.makeText(EmailVerification.this,"OTP Send",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyotp(){
        String Query = "UPDATE C_USER SET IS_EMAILVERIFIED = '1' WHERE EMAIL = '"+EmailID+"';";
        VolleyExecute.volleydynamicsavefun(EmailVerification.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                if(teams.get(0).getcol1().equals("Saved")){
                    if(sgen.activty_logincall.equals("CartActivity")){
                        startActivity(new Intent(EmailVerification.this,CartActivity.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(EmailVerification.this,MainLandingActivity.class));
                        finish();
                    }
                }
            }
        });
    }

}