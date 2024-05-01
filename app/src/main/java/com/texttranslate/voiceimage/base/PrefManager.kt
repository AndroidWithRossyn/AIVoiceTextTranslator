package com.texttranslate.voiceimage.base

import android.content.Context
import android.content.SharedPreferences

class PrefManager(_context: Context) {

    var pref: SharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = pref.edit()

    var isFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }
    var isFirstTimeDetail: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_INFO, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_INFO, isFirstTime)
            editor.commit()
        }

    fun setBoolean(dnd: String?, checked: Boolean) {
        editor.putBoolean(dnd, checked)
        editor.commit()
    }

    fun getBoolean(key: String?): Boolean {
        return pref.getBoolean(key, false)
    }

    companion object {
        private const val PREF_NAME = "TEXT-VOICE-TRANSLATOR"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
        private const val IS_FIRST_TIME_INFO = "IsFirstTimeInfo"
    }
}
