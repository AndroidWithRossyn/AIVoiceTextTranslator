package com.texttranslate.voiceimage.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.texttranslate.voiceimage.R

class RateDialog : DialogFragment, View.OnClickListener {
    var alertDialog: AlertDialog? = null
    var mMbtnCancel: MaterialButton? = null
    var mMbtnDiscard: MaterialButton? = null
    var m_clickListener: OnClickListener? = null

    interface OnClickListener {
        fun OnRate()
        fun OnCancel()
    }

    constructor()
    constructor(onClickListener: OnClickListener?) {
        m_clickListener = onClickListener
    }

    override fun onClick(view: View) {
        if (view.id == R.id.mMbtnDiscard) {
            m_clickListener!!.OnRate()
            alertDialog!!.dismiss()
        }
        if (view.id == R.id.mMbtnCancel) {
            m_clickListener!!.OnCancel()
            alertDialog!!.dismiss()
        }
    }

    override fun onCreateDialog(bundle: Bundle?): Dialog {
        val builder = AlertDialog.Builder(
            requireActivity()
        )
        val inflate = requireActivity().layoutInflater.inflate(R.layout.dialog_rate, null as ViewGroup?)
        builder.setView(inflate)
        if (alertDialog == null) {
            alertDialog = builder.create()
        }
        alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        alertDialog!!.setCancelable(false)
        mMbtnDiscard = inflate.findViewById(R.id.mMbtnDiscard)
        mMbtnCancel = inflate.findViewById(R.id.mMbtnCancel)
        mMbtnDiscard!!.setOnClickListener(this)
        mMbtnCancel!!.setOnClickListener(this)
        return alertDialog!!
    }

    override fun onDismiss(dialogInterface: DialogInterface) {
        super.onDismiss(dialogInterface)
        alertDialog = null
    }
}