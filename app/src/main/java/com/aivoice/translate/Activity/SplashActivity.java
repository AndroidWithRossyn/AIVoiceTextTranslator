package com.aivoice.translate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.aivoice.translate.R;
import com.aivoice.translate.base.BaseActivity;
import com.aivoice.translate.utils.Utils;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.mIvLogo)
    ImageView mIvLogo;
    @BindView(R.id.mIvTitle)
    TextView mIvTitle;
    public static SplashActivity splashActivity;

    public static SplashActivity getInstance() {
        return splashActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        splashActivity = this;


        mStartAct();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void mStartAct() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.isNetworkAvailable(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                } else {
                    Toast.makeText(SplashActivity.this, "Turn On Internet..!", Toast.LENGTH_SHORT).show();
                }
            }
        }, 2000);

    }
}