package com.aivoice.translate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.aivoice.translate.AdsUtils.FirebaseADHandlers.AdUtils;
import com.aivoice.translate.AdsUtils.Interfaces.AppInterfaces;
import com.aivoice.translate.AdsUtils.Utils.Constants;
import com.aivoice.translate.R;
import com.aivoice.translate.base.BaseActivity;
import com.aivoice.translate.translateApi.TranslateApi;
import com.aivoice.translate.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.aivoice.translate.utils.Utils.WHATIS;

public class TextActivity extends BaseActivity {
    @BindView(R.id.mETInput)
    EditText mETInput;
    @BindView(R.id.mIVRecordVoice)
    ImageView mIVRecordVoice;
    @BindView(R.id.mIVText)
    ImageView mIVText;
    @BindView(R.id.mTxtResult)
    TextView mTxtResult;
    @BindView(R.id.mTxtTitle)
    TextView mTxtTitle;
    @BindView(R.id.mTxtFromLang)
    TextView mTxtFromLang;
    @BindView(R.id.mTxtInput)
    TextView mTxtInput;
    @BindView(R.id.mTxtToLang)
    TextView mTxtToLang;
    @BindView(R.id.mIVClear)
    ImageView mIVClear;
    private String mLanguageCodeFrom = "en";
    private String mLanguageCodeTo = "en";
    boolean IsFrom = true;
    public static TextActivity textActivity;

    public static TextActivity getInstance() {
        return textActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_voice;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        AdUtils.showNativeAd(TextActivity.this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), (LinearLayout) findViewById(R.id.native_ads1), true);

        textActivity = this;
        mIVRecordVoice.setVisibility(View.GONE);
        mIVText.setVisibility(View.VISIBLE);
        mTxtTitle.setText("" + getResources().getText(R.string.text_tran));
        if (!Utils.isEmptyStr(Utils.TEXTEXTRACT)) {
            mTxtResult.setText("");
            mTxtInput.setText("" + Utils.TEXTEXTRACT);
//            mETInput.setSelection(mETInput.getText().length());
            mTxtInput.setVisibility(View.VISIBLE);
            mETInput.setVisibility(View.GONE);
            mIVClear.setVisibility(View.GONE);
        } else {
            mTxtResult.setText(getResources().getString(R.string.textInfo));
            mTxtInput.setVisibility(View.GONE);
            mETInput.setVisibility(View.VISIBLE);
            mIVClear.setVisibility(View.VISIBLE);
        }
//        AdUtils.showNativeAd(StartActivity.this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), findViewById(R.id.native_ads), true);
    }

    @OnClick({R.id.mIVBack, R.id.mIVTrans, R.id.mRLTranslate, R.id.mTxtFromLang, R.id.mTxtToLang, R.id.mIVClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIVBack:
                AdUtils.showInterstitialAd(TextActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        TextActivity.super.onBackPressed();
                    }
                });
                break;
            case R.id.mIVTrans:
                mTransLanguage();
                break;
            case R.id.mRLTranslate:
                mTranslateText();
                break;
            case R.id.mTxtFromLang:
                IsFrom = true;
                WHATIS = "text";
                startActivity(new Intent(textActivity, LanguageActivity.class));
                break;
            case R.id.mTxtToLang:
                IsFrom = false;
                WHATIS = "text";
                startActivity(new Intent(textActivity, LanguageActivity.class));
                break;
            case R.id.mIVClear:
                mETInput.setText("");
                mTxtResult.setText("");
                mETInput.setSelection(0);
                break;

        }
    }

    private void mTransLanguage() {
        String mTmp = mLanguageCodeFrom;
        mLanguageCodeFrom = mLanguageCodeTo;
        mLanguageCodeTo = mTmp;

        String mLanTmp = mTxtFromLang.getText().toString();
        mTxtFromLang.setText(mTxtToLang.getText().toString());
        mTxtToLang.setText(mLanTmp);
    }

    private void mTranslateText() {
        Utils.hideKeyboard(mETInput);
        String input;
        if (!Utils.isEmptyStr(Utils.TEXTEXTRACT)) {
            input = mTxtInput.getText().toString();
        } else {
            input = mETInput.getText().toString();
        }
        Log.e("TAG", "mTranslateText:=====" + mLanguageCodeFrom + "===" + mLanguageCodeTo);
        TranslateApi translate = new TranslateApi();
        translate.setOnTranslationCompleteListener(new TranslateApi.OnTranslationCompleteListener() {
            @Override
            public void onStartTranslation() {
                // here you can perform initial work before translated the text like displaying progress bar
            }

            @Override
            public void onCompleted(String text) {
                // "text" variable will give you the translated text
                mTxtResult.setText(text);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        translate.execute(input, mLanguageCodeFrom, mLanguageCodeTo);
    }

    public void mGetSelctedLang(String lang, String str) {
        if (IsFrom) {
            mLanguageCodeFrom = str;
            mTxtFromLang.setText("" + lang);
        } else {
            mLanguageCodeTo = str;
            mTxtToLang.setText("" + lang);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.TEXTEXTRACT = "";
    }

    @Override
    public void onBackPressed() {
        AdUtils.showInterstitialAd(TextActivity.this, new AppInterfaces.InterStitialADInterface() {
            @Override
            public void adLoadState(boolean isLoaded) {
                Utils.TEXTEXTRACT = "";
                finish();
            }
        });

    }
}