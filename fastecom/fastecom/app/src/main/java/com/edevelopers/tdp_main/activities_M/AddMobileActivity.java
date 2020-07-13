package com.edevelopers.tdp_main.activities_M;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class AddMobileActivity extends AppCompatActivity {

    private TextInputEditText Mobile,AddMobileNumber;
    private TextView Changenumber;
    private Button resend,verify;
    private String EmailID = "",MobileNumber = "",otpmobile = "000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mobile);


        Mobile = findViewById(R.id.MobileOTP);
        AddMobileNumber = findViewById(R.id.AddMobileNumber);
        resend = findViewById(R.id.btn_Resend);
        verify = findViewById(R.id.btn_verify);
        Changenumber = findViewById(R.id.Changenumber);

        Changenumber.setVisibility(View.GONE);
        verify.setEnabled(false);

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend.setText("Resend");
                Changenumber.setVisibility(View.VISIBLE);
                AddMobileNumber.setEnabled(false);
                Sendotp();
            }
        });

        Changenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMobileNumber.setEnabled(true);
                Changenumber.setVisibility(View.GONE);
                resend.setText("send");
                Mobile.setText("");
                otpmobile = "000";
            }
        });

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
                    Toast.makeText(AddMobileActivity.this,"Invalid  OTP",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private  void Sendotp(){
        MobileNumber = "+91"+AddMobileNumber.getText().toString();
        VolleyExecute.volleydynsendmessage(AddMobileActivity.this, "Msg", MobileNumber, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                otpmobile = teams.get(0).getcol2();
                Toast.makeText(AddMobileActivity.this,"OTP Send",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyotp(){
        MobileNumber = "+91"+AddMobileNumber.getText().toString();
        String Query = "UPDATE C_USER SET MOBILE = '"+MobileNumber+"' IS_MOBILEVERIFIED = '1' WHERE C_ID = '"+sgen.Login_Id+"' and ID = '"+sgen.Login_Id1+"'; ";
        VolleyExecute.volleydynamicsavefun(AddMobileActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                if(teams.get(0).getcol1().equals("Saved")){
                        startActivity(new Intent(AddMobileActivity.this,SigninActivity.class));
                        finish();
                }
            }
        });
    }
}