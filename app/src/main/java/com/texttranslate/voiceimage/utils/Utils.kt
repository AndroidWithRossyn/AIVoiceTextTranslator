package com.texttranslate.voiceimage.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.texttranslate.voiceimage.MyApp.Companion.instance
import com.texttranslate.voiceimage.R

object Utils {
    @JvmStatic
    var WHATIS = "voice"

    fun showToast(context: Context?, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun getShader(textView: TextView): Shader {
        return LinearGradient(
            0f,
            0f,
            0f,
            textView.lineHeight.toFloat(),
            instance!!.resources.getColor(R.color.colorUpSide),
            instance!!.resources.getColor(R.color.colorDown),
            Shader.TileMode.CLAMP
        )
    }

    fun isGooglePlayServicesAvailable(context: Context): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return resultCode == ConnectionResult.SUCCESS
    }


    fun isEmptyStr(str: String?): Boolean {
        return if (str == null || str.trim { it <= ' ' }.isEmpty() || str.equals(
                "",
                ignoreCase = true
            )
        ) {
            true
        } else {
            false
        }
    }

    val isLatestVersion: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R


    var alertDialog: AlertDialog? = null


    fun displayProgress(context: Context) {
        try {
            val dialogBuilder = AlertDialog.Builder(context)
            val inflater = (context as Activity).layoutInflater
            val dialogView = inflater.inflate(R.layout.progressbar, null)
            dialogBuilder.setView(dialogView)
            if (alertDialog == null) {
                alertDialog = dialogBuilder.create()
            }
            alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog!!.setCancelable(false)
            val lp = alertDialog!!.window!!
                .attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.CENTER
            lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            lp.dimAmount = 0.5f
            alertDialog!!.window!!.setAttributes(lp)
            if (!alertDialog!!.isShowing) alertDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var dialog: ProgressDialog? = null
    fun dismissProgress() {
        try {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
                dialog = null
            }
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
                alertDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        var isNetworkAvail = false
        if (context == null)
            return isNetworkAvail
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            isNetworkAvail =
                connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
                    .isConnected
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isNetworkAvail
    }

    fun hideKeyboard(view: View?) {
        if (view != null) {
            try {
                val inputMethodManager =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
