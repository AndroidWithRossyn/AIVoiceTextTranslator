package com.texttranslate.voiceimage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.texttranslate.voiceimage.adpaters.LanguageAdapter
import com.texttranslate.voiceimage.interfaces.OnItemClickListener
import com.texttranslate.voiceimage.model.Languages
import com.texttranslate.voiceimage.model.Languages.getLangCodeEN
import com.texttranslate.voiceimage.model.LanguagesModel
import com.texttranslate.voiceimage.utils.Utils
import java.util.Arrays
import java.util.Locale

class LanguageDialog(private val mContext: Context, var mListener: OnItemClickListener) : Dialog(
    mContext
) {
    private var mRecyclerView: RecyclerView? = null
    private var mETSearchInput: EditText? = null
    private var closeButton: ImageView? = null
    var mLangList = ArrayList<LanguagesModel>()
    var adapter: LanguageAdapter? = null
    var mList = ArrayList<String>()

    init {
        initDialog()
    }

    private fun initDialog() {
        val window = window!!
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Transparent background
        setContentView(R.layout.activity_language)
        setCanceledOnTouchOutside(true)
        closeButton = findViewById(R.id.mIVBack)
        closeButton!!.setOnClickListener { dismiss() }
        mETSearchInput = findViewById(R.id.mETSearchInput)
        mRecyclerView = findViewById(R.id.mRecyclerView)
        mList.clear()
        mLangList.clear()
        mList.addAll(Arrays.asList(*Languages.langsEN))
        GetLanguage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        mETSearchInput!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filterQuery(editable.toString())
            }
        })
    }

    internal inner class GetLanguage : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            for (i in mList.indices) {
                val languagesModel = LanguagesModel(mList[i], getLangCodeEN(i))
                mLangList.add(languagesModel)
            }
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            Utils.displayProgress(mContext)
        }



        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            Utils.dismissProgress()
            mRecyclerView!!.setHasFixedSize(true)
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(mContext)
            adapter = LanguageAdapter(mLangList, mListener)
            mRecyclerView!!.setLayoutManager(layoutManager)
            mRecyclerView!!.setAdapter(adapter)
        }
    }

    fun filterQuery(text: String?) {
        val filterdNames = ArrayList<LanguagesModel>()
        for (s in mLangList) {
            if (s.languageName.lowercase(Locale.getDefault())
                    .contains(text!!) || s.languageName.lowercase(
                    Locale.getDefault()
                ).contains(
                    text
                )
            ) {
                filterdNames.add(s)
            }
        }
        adapter!!.setFilter(filterdNames)
    }
}