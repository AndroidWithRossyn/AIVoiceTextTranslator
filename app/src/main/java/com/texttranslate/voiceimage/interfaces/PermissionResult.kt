package com.texttranslate.voiceimage.interfaces

interface PermissionResult {
    fun permissionGranted()
    fun permissionDenied()
    fun permissionForeverDenied()
}
