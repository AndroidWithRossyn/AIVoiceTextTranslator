package com.texttranslate.voiceimage.interfaces

interface OnTranslationCompleteListener {
    fun onStartTranslation()
    fun onCompleted(text: String?)
    fun onError(e: Exception?)
}
