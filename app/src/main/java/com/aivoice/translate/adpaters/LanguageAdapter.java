package com.aivoice.translate.adpaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.aivoice.translate.Activity.LanguageActivity;
import com.aivoice.translate.Activity.TextActivity;
import com.aivoice.translate.Activity.VoiceActivity;
import com.aivoice.translate.R;
import com.aivoice.translate.model.LanguagesModel;

import java.util.ArrayList;
import java.util.List;

import static com.aivoice.translate.utils.Utils.WHATIS;

public class LanguageAdapter extends Adapter<LanguageAdapter.ExampleViewHolder> {
    private ArrayList<LanguagesModel> exampleList = new ArrayList<LanguagesModel>();

    class ExampleViewHolder extends ViewHolder {
        TextView mTxtLang;

        ExampleViewHolder(View itemView) {
            super(itemView);
            this.mTxtLang = itemView.findViewById(R.id.mTxtLang);

        }
    }

    public LanguageAdapter(List<LanguagesModel> exampleList2) {
        exampleList.clear();
        this.exampleList.addAll(exampleList2);
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false));
    }

    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        final LanguagesModel currentItem = this.exampleList.get(position);
        holder.mTxtLang.setText(currentItem.getLanguageName());
        holder.mTxtLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (WHATIS.equalsIgnoreCase("text")) {
                    if (TextActivity.getInstance() != null)
                        TextActivity.getInstance().mGetSelctedLang(currentItem.getLanguageName(), currentItem.getLanguageCode());
                } else {
                    if (VoiceActivity.getInstance() != null)
                        VoiceActivity.getInstance().mGetSelctedLang(currentItem.getLanguageName(), currentItem.getLanguageCode());
                }
                LanguageActivity.getInstance().finish();

            }
        });
    }

    public int getItemCount() {
        return this.exampleList.size();
    }

    public void setFilter(List<LanguagesModel> filterdNames) {
        exampleList.clear();
        this.exampleList.addAll(filterdNames);
        notifyDataSetChanged();
    }
}