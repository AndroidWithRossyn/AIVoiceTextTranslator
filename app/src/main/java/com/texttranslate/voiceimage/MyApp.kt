package com.texttranslate.voiceimage

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApp : Application() {
    var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        context = this
        instance = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: MyApp? = null
    }
}
