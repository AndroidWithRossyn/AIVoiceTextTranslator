package com.texttranslate.voiceimage.base

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
object PermitConstant {
    const val Manifest_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    const val Manifest_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val Manifest_CAMERA = Manifest.permission.CAMERA
    const val READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES
    const val READ_MEDIA_VIDEOS = Manifest.permission.READ_MEDIA_VIDEO
    const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
}
