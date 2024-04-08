package com.texttranslate.voiceimage

import android.os.Bundle
import android.util.Log
import android.view.View
import com.texttranslate.voiceimage.base.PermissionHelperActivity
import com.texttranslate.voiceimage.databinding.ActivityVoiceBinding
import com.texttranslate.voiceimage.interfaces.OnItemClickListener
import com.texttranslate.voiceimage.interfaces.OnTranslationCompleteListener
import com.texttranslate.voiceimage.translateApi.TranslateApi
import com.texttranslate.voiceimage.utils.Utils

class TextActivity : PermissionHelperActivity(), View.OnClickListener, OnItemClickListener {
    private var mLanguageCodeFrom: String? = "en"
    private var mLanguageCodeTo: String? = "en"
    var IsFrom = true
    var textActivity: TextActivity? = null
    private var binding: ActivityVoiceBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoiceBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        textActivity = this
        binding!!.mIVRecordVoice.setVisibility(View.GONE)
        binding!!.mIVText.setVisibility(View.VISIBLE)
        binding!!.mTxtTitle.text = "" + getResources().getText(R.string.text_tran)
        if (!Utils.isEmptyStr(Utils.TEXTEXTRACT)) {
            binding!!.mTxtResult.text = ""
            binding!!.mTxtInput.text = "" + Utils.TEXTEXTRACT
            //            mETInput.setSelection(mETInput.getText().length());
            binding!!.mTxtInput.visibility = View.VISIBLE
            binding!!.mETInput.visibility = View.GONE
            binding!!.mIVClear.setVisibility(View.GONE)
        } else {
            binding!!.mTxtResult.text = getResources().getString(R.string.textInfo)
            binding!!.mTxtInput.visibility = View.GONE
            binding!!.mETInput.visibility = View.VISIBLE
            binding!!.mIVClear.setVisibility(View.VISIBLE)
        }
        binding!!.mIVBack.setOnClickListener(this)
        binding!!.mIVTrans.setOnClickListener(this)
        binding!!.mRLTranslate.setOnClickListener(this)
        binding!!.mTxtFromLang.setOnClickListener(this)
        binding!!.mTxtToLang.setOnClickListener(this)
        binding!!.mIVClear.setOnClickListener(this)
    }

    var dialog: LanguageDialog? = null
    override fun onClick(v: View) {
        if (v === binding!!.mIVBack) {
            super@TextActivity.finish()
        } else if (v === binding!!.mIVTrans) {
            mTransLanguage()
        } else if (v === binding!!.mRLTranslate) {
            mTranslateText()
        } else if (v === binding!!.mTxtFromLang) {
            IsFrom = true
            Utils.WHATIS = "text"
            dialog = LanguageDialog(this@TextActivity, this)
            dialog!!.show()
        } else if (v === binding!!.mTxtToLang) {
            IsFrom = false
            Utils.WHATIS = "text"
            dialog = LanguageDialog(this@TextActivity, this)
            dialog!!.show()
        } else if (v === binding!!.mIVClear) {
            binding!!.mETInput.setText("")
            binding!!.mTxtResult.text = ""
            binding!!.mETInput.setSelection(0)
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
        val input: String
        input = if (!Utils.isEmptyStr(Utils.TEXTEXTRACT)) {
            binding!!.mTxtInput.getText().toString()
        } else {
            binding!!.mETInput.getText().toString()
        }
        Log.e("TAG", "mTranslateText:=====$mLanguageCodeFrom===$mLanguageCodeTo")
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
        translate.execute(input, mLanguageCodeFrom, mLanguageCodeTo)
    }

    override fun mGetSelctedLang(lang: String?, str: String?) {
        Log.d("TAGAdapter", "mGetSelctedLang: lang " + lang + "str " + str)
        if (IsFrom) {
            mLanguageCodeFrom = str
            binding!!.mTxtFromLang.text = "" + lang
        } else {
            mLanguageCodeTo = str
            binding!!.mTxtToLang.text = "" + lang
        }
        dialog!!.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils.TEXTEXTRACT = ""
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Utils.TEXTEXTRACT = ""
        finish()
    }
}