package com.edevelopers.tdp_main.classes;

/**
 * Created by Ram on 04-04-2018.
 */

import android.app.ProgressDialog;
import android.content.Context;

import com.edevelopers.tdp_main.R;

/**
 * Created by Anubhav Singh on 3/18/2020.
 */

public  class MyProgressdialog {

    Context tcntx;
    ProgressDialog progressDialog;

    public MyProgressdialog(Context cntx) {
        this.tcntx = cntx;
    }

    public void show() {
        progressDialog = new ProgressDialog(tcntx, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Processing");
        progressDialog.show();
    }

    public void dismiss() {
        progressDialog.dismiss();
    }
}