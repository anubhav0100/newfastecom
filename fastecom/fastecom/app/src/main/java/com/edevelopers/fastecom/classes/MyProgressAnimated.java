package com.edevelopers.fastecom.classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.edevelopers.fastecom.R;

public class MyProgressAnimated {
    Context tcntx;
    Dialog dialog;
    public MyProgressAnimated(Context context)
    {
        tcntx=context;

    }

    public void show() {

        dialog = new Dialog(tcntx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_progress1);
        dialog.setCancelable(true);
        AnimatedGifImageView animatedGifImageView = ((AnimatedGifImageView) dialog.findViewById(R.id.animatedGifImageView));
        animatedGifImageView.setAnimatedGif(R.raw.ajax,
                AnimatedGifImageView.TYPE.FIT_CENTER);
        dialog.show();
//        final Handler handler;
//        Runnable handlerTask;
//        handler = new Handler();
//
//
//        handlerTask = new Runnable() {
//            @Override
//            public void run() {
//
//
//
//            }
//        };
//
//        Thread thread = new Thread(handlerTask);
//        thread.start();
    }
    public void dismiss()
    {
        dialog.dismiss();
    }
}
