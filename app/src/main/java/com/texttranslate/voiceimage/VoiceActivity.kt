package com.texttranslate.voiceimage

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import com.texttranslate.voiceimage.base.PermissionHelperActivity
import com.texttranslate.voiceimage.databinding.ActivityVoiceBinding
import com.texttranslate.voiceimage.interfaces.OnItemClickListener
import com.texttranslate.voiceimage.interfaces.OnTranslationCompleteListener
import com.texttranslate.voiceimage.translateApi.TranslateApi
import com.texttranslate.voiceimage.utils.Utils
import java.util.Locale

class VoiceActivity : PermissionHelperActivity(), View.OnClickListener, OnItemClickListener {
    private var mLanguageCodeFrom: String? = "en"
    private var mLanguageCodeTo: String? = "en"
    var IsFrom = true
    var voiceActivity: VoiceActivity? = null
    private var binding: ActivityVoiceBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoiceBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        voiceActivity = this
        binding!!.mTxtInput.text = "Click Microphone for recording your voice"
        binding!!.mTxtInput.visibility = View.VISIBLE
        binding!!.mETInput.visibility = View.GONE
        binding!!.mIVRecordVoice.setVisibility(View.VISIBLE)
        binding!!.mIVText.setVisibility(View.GONE)
        binding!!.mIVClear.setVisibility(View.GONE)
        binding!!.mIVBack.setOnClickListener(this)
        binding!!.mIVRecordVoice.setOnClickListener(this)
        binding!!.mIVTrans.setOnClickListener(this)
        binding!!.mRLTranslate.setOnClickListener(this)
        binding!!.mTxtFromLang.setOnClickListener(this)
        binding!!.mTxtToLang.setOnClickListener(this)
    }

    var dialog: LanguageDialog? = null
    override fun onClick(v: View) {
        if (v === binding!!.mIVBack) {
            super@VoiceActivity.finish()
        } else if (v === binding!!.mIVRecordVoice) {
            mRecordVoice()
        } else if (v === binding!!.mIVTrans) {
            mTransLanguage()
        } else if (v === binding!!.mRLTranslate) {
            mTranslateText()
        } else if (v === binding!!.mTxtFromLang) {
            IsFrom = true
            Utils.WHATIS = "voice"
            dialog = LanguageDialog(voiceActivity!!, this)
            dialog!!.show()
        } else if (v === binding!!.mTxtToLang) {
            IsFrom = false
            Utils.WHATIS = "voice"
            dialog = LanguageDialog(voiceActivity!!, this)
            dialog!!.show()
        }
    }

    private fun mTransLanguage() {
        val mTmp = mLanguageCodeFrom
        mLanguageCodeFrom = mLanguageCodeTo
        mLanguageCodeTo = mTmp
        val mLanTmp = binding!!.mTxtFromLang.getText().toString()
        binding!!.mTxtFromLang.text = binding!!.mTxtToLang.getText().toString()
        binding!!.mTxtToLang.text = mLanTmp
    }

    private fun mTranslateText() {
        Utils.hideKeyboard(binding!!.mETInput)
        val translate = TranslateApi()
        translate.setOnTranslationCompleteListener(object : OnTranslationCompleteListener {
            override fun onStartTranslation() {
                // here you can perform initial work before translated the text like displaying progress bar
            }

            override fun onCompleted(text: String?) {
                // "text" variable will give you the translated text
                binding!!.mTxtResult.text = text
            }

            override fun onError(e: Exception?) {}
        })
        translate.execute(
            binding!!.mTxtInput.getText().toString(),
            mLanguageCodeFrom,
            mLanguageCodeTo
        )
    }

    fun mRecordVoice() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
        try {
            startActivityForResult(intent, 22)
        } catch (e: Exception) {
            Toast.makeText(this@VoiceActivity, " " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 22) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding!!.mTxtInput.text = result!![0]
            }
        }
    }

    override fun mGetSelctedLang(lang: String?, str: String?) {
        if (IsFrom) {
            mLanguageCodeFrom = str
            binding!!.mTxtFromLang.text = "" + lang
        } else {
            mLanguageCodeTo = str
            binding!!.mTxtToLang.text = "" + lang
        }
        dialog!!.dismiss()
    }
}