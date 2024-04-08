package com.texttranslate.voiceimage.adpaters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.texttranslate.voiceimage.R
import com.texttranslate.voiceimage.adpaters.LanguageAdapter.LanguageViewHolder
import com.texttranslate.voiceimage.interfaces.OnItemClickListener
import com.texttranslate.voiceimage.model.LanguagesModel

class LanguageAdapter(exampleList2: List<LanguagesModel>?, listener: OnItemClickListener?) :
    RecyclerView.Adapter<LanguageViewHolder>() {
    private val exampleList = ArrayList<LanguagesModel>()
    var mListener: OnItemClickListener?

    init {
        exampleList.clear()
        exampleList.addAll(exampleList2!!)
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.mTxtLang.text = currentItem.languageName

        holder.mTxtLang.setOnClickListener {
            Log.d("TAGAdapter", "onclick: ")
            if (mListener != null) {
                mListener!!.mGetSelctedLang(currentItem.languageName, currentItem.languageCode)
            }
        }
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    class LanguageViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var mTxtLang: TextView

        init {
            mTxtLang = itemView.findViewById(R.id.mTxtLang)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilter(filterdNames: List<LanguagesModel>?) {
        exampleList.clear()
        exampleList.addAll(filterdNames!!)
        notifyDataSetChanged()
    }
}