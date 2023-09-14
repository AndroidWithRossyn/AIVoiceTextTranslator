package com.aivoice.translate.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aivoice.translate.AdsUtils.FirebaseADHandlers.AdUtils;
import com.aivoice.translate.AdsUtils.Interfaces.AppInterfaces;
import com.aivoice.translate.AdsUtils.Utils.Constants;
import com.aivoice.translate.R;
import com.aivoice.translate.adpaters.LanguageAdapter;
import com.aivoice.translate.base.BaseActivity;
import com.aivoice.translate.model.Languages;
import com.aivoice.translate.model.LanguagesModel;
import com.aivoice.translate.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class LanguageActivity extends BaseActivity {
    ArrayList<LanguagesModel> mLangList = new ArrayList<LanguagesModel>();
    @BindView(R.id.mETSearchInput)
    EditText mETSearchInput;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    LanguageAdapter adapter;
    public static LanguageActivity languageActivity;
    ArrayList<String> mList = new ArrayList<>();

    public static LanguageActivity getInstance() {
        return languageActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        AdUtils.showNativeAd(LanguageActivity.this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), (LinearLayout) findViewById(R.id.native_small_ads), false);
        languageActivity = this;
        mList.addAll(Arrays.asList(Languages.getLangsEN()));
        new GetLanguage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        mETSearchInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                filterQuery(editable.toString());
            }
        });
    }

    class GetLanguage extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.displayProgress(languageActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < mList.size(); i++) {
                LanguagesModel languagesModel = new LanguagesModel(mList.get(i), Languages.getLangCodeEN(i));
                mLangList.add(languagesModel);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Utils.dismissProgress();
            setUpRecyclerView();
        }
    }

    private void setUpRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.adapter = new LanguageAdapter(mLangList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(this.adapter);
    }

    public void filterQuery(String text) {
        ArrayList<LanguagesModel> filterdNames = new ArrayList<>();
        for (LanguagesModel s : mLangList) {
            if (s.getLanguageName().toLowerCase().contains(text) || s.getLanguageName().toLowerCase().contains(text)) {
                filterdNames.add(s);
            }
        }
        adapter.setFilter(filterdNames);
    }

    @OnClick({R.id.mIVBack, R.id.mIvSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIVBack:
                AdUtils.showInterstitialAd(LanguageActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        LanguageActivity.super.onBackPressed();
                    }
                });
                break;
            case R.id.mIvSearch:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AdUtils.showInterstitialAd(LanguageActivity.this, new AppInterfaces.InterStitialADInterface() {
            @Override
            public void adLoadState(boolean isLoaded) {
                LanguageActivity.super.onBackPressed();
            }
        });
    }
}