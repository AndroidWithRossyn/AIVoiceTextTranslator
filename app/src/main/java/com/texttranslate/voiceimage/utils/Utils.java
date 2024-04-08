package com.texttranslate.voiceimage.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.texttranslate.voiceimage.MyApp;
import com.texttranslate.voiceimage.R;

import static android.os.Build.VERSION.SDK_INT;

public class Utils {
    public static String WHATIS = "voice";
    public static String TEXTEXTRACT = "";

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static Shader getShader(TextView textView) {
        Shader shader = new LinearGradient(0, 0, 0, textView.getLineHeight(), MyApp.Companion.getInstance().getResources().getColor(R.color.colorUpSide), MyApp.Companion.getInstance().getResources().getColor(R.color.colorDown), Shader.TileMode.CLAMP);
        return shader;
    }

    public static boolean isEmptyStr(String str) {
        if (str == null || str.trim().isEmpty() || str.equalsIgnoreCase(""))
            return true;
        return false;
    }


    public static boolean isLatestVersion() {
        return SDK_INT >= Build.VERSION_CODES.R;
    }


    static AlertDialog alertDialog;
    private static ProgressDialog dialog;

    @SuppressLint("LogNotTimber")
    public static void displayProgress(Context context) {

        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.progressbar, null);
            dialogBuilder.setView(dialogView);

            if (alertDialog == null) {
                alertDialog = dialogBuilder.create();

            }
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            alertDialog.setCancelable(false);

            WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.CENTER;
            lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            lp.dimAmount = 0.5f;
            alertDialog.getWindow().setAttributes(lp);

            if (!alertDialog.isShowing())
                alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgress() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }

            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
                alertDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean isNetworkAvail = false;
        if (context == null) return isNetworkAvail;
        try {
            ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            isNetworkAvail = connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNetworkAvail;
    }
    public static void hideKeyboard(View view) {
        if (view != null) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
