package com.microblink.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.microblink.libutils.R;

public class LoadingActivity {
    public Activity activity;
    AlertDialog dialog;

    public LoadingActivity(Activity myActivity){
        activity = myActivity;
    }


    public void startLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(false);

        dialog = builder.create();

        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
