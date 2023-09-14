package com.aivoice.translate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.aivoice.translate.AdsUtils.FirebaseADHandlers.AdUtils;
import com.aivoice.translate.AdsUtils.Interfaces.AppInterfaces;
import com.aivoice.translate.AdsUtils.Utils.Constants;
import com.aivoice.translate.R;
import com.aivoice.translate.base.BaseActivity;
import com.aivoice.translate.translateApi.TranslateApi;
import com.aivoice.translate.utils.Utils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.aivoice.translate.utils.Utils.WHATIS;

public class VoiceActivity extends BaseActivity {
    @BindView(R.id.mETInput)
    EditText mETInput;
    @BindView(R.id.mTxtResult)
    TextView mTxtResult;
    @BindView(R.id.mTxtInput)
    TextView mTxtInput;
    @BindView(R.id.mTxtFromLang)
    TextView mTxtFromLang;
    @BindView(R.id.mTxtToLang)
    TextView mTxtToLang;
    @BindView(R.id.mIVClear)
    ImageView mIVClear;
    @BindView(R.id.mIVRecordVoice)
    ImageView mIVRecordVoice;
    @BindView(R.id.mIVText)
    ImageView mIVText;
    private String mLanguageCodeFrom = "en";
    private String mLanguageCodeTo = "en";
    boolean IsFrom = true;
    public static VoiceActivity voiceActivity;

    public static VoiceActivity getInstance() {
        return voiceActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_voice;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        AdUtils.showNativeAd(VoiceActivity.this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), (LinearLayout) findViewById(R.id.native_ads1), true);
        voiceActivity = this;
        mTxtInput.setText("Click Microphone for recording your voice");
        mTxtInput.setVisibility(View.VISIBLE);
        mETInput.setVisibility(View.GONE);
        mIVRecordVoice.setVisibility(View.VISIBLE);
        mIVText.setVisibility(View.GONE);
        mIVClear.setVisibility(View.GONE);

//        AdUtils.showNativeAd(StartActivity.this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), findViewById(R.id.native_ads), true);
    }

    @OnClick({R.id.mIVBack, R.id.mIVRecordVoice, R.id.mIVTrans, R.id.mRLTranslate, R.id.mTxtFromLang, R.id.mTxtToLang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIVBack:
                AdUtils.showInterstitialAd(VoiceActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        VoiceActivity.super.onBackPressed();
                    }
                });
                break;
            case R.id.mIVRecordVoice:
                mRecordVoice();
                break;
            case R.id.mIVTrans:
                mTransLanguage();
                break;
            case R.id.mRLTranslate:
                mTranslateText();
                break;
            case R.id.mTxtFromLang:
                IsFrom = true;
                WHATIS="voice";
                startActivity(new Intent(voiceActivity, LanguageActivity.class));
                break;
            case R.id.mTxtToLang:
                IsFrom = false;
                WHATIS="voice";
                startActivity(new Intent(voiceActivity, LanguageActivity.class));
                break;

        }
    }

    private void mTransLanguage() {
        String mTmp = mLanguageCodeFrom;
        mLanguageCodeFrom = mLanguageCodeTo;
        mLanguageCodeTo = mTmp;

        String mLanTmp= mTxtFromLang.getText().toString();
        mTxtFromLang.setText(mTxtToLang.getText().toString());
        mTxtToLang.setText(mLanTmp);
    }

    private void mTranslateText() {
        Utils.hideKeyboard(mETInput);
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
        translate.execute(mTxtInput.getText().toString(), mLanguageCodeFrom, mLanguageCodeTo);
    }

    public void mRecordVoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        try {
            startActivityForResult(intent, 22);
        } catch (Exception e) {
            Toast.makeText(VoiceActivity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mTxtInput.setText(Objects.requireNonNull(result).get(0));
            }
        }
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
    public void onBackPressed() {
        AdUtils.showInterstitialAd(VoiceActivity.this, new AppInterfaces.InterStitialADInterface() {
            @Override
            public void adLoadState(boolean isLoaded) {
                VoiceActivity.super.onBackPressed();
            }
        });
    }
}