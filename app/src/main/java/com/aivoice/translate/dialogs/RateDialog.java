package com.aivoice.translate.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.aivoice.translate.R;

public class RateDialog extends DialogFragment implements View.OnClickListener {
    public AlertDialog alertDialog;
    public MaterialButton mMbtnCancel;
    public MaterialButton mMbtnDiscard;
    public OnClickListener m_clickListener;

    public interface OnClickListener {
        void OnRate();
        void OnCancel();
    }

    public RateDialog() {
    }
    public RateDialog(OnClickListener onClickListener) {
        this.m_clickListener = onClickListener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mMbtnDiscard) {
            this.m_clickListener.OnRate();
            this.alertDialog.dismiss();
        }
        if (view.getId() == R.id.mMbtnCancel) {
            this.m_clickListener.OnCancel();
            this.alertDialog.dismiss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_rate, (ViewGroup) null);
        builder.setView(inflate);
        if (this.alertDialog == null) {
            this.alertDialog = builder.create();
        }
        this.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.alertDialog.setCancelable(false);
        mMbtnDiscard=inflate.findViewById(R.id.mMbtnDiscard);
        mMbtnCancel=inflate.findViewById(R.id.mMbtnCancel);
        this.mMbtnDiscard.setOnClickListener(this);
        this.mMbtnCancel.setOnClickListener(this);
        return this.alertDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        this.alertDialog = null;
    }
}