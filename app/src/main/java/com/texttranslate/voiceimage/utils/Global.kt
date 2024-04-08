package com.texttranslate.voiceimage.utils

import android.os.Build

object Global {
    @JvmStatic
    val isLatestVersion: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}
