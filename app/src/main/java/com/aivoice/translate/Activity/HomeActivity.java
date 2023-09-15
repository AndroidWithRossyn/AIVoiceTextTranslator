package com.aivoice.translate.Activity;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.aivoice.translate.AdsUtils.Utils.Global;
import com.aivoice.translate.BuildConfig;
import com.aivoice.translate.R;
import com.aivoice.translate.base.BaseActivity;
import com.aivoice.translate.base.PermitConstant;
import com.aivoice.translate.dialogs.RateDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.aivoice.translate.utils.Utils.getShader;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.mTxtTitle)
    TextView mTxtTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
       mCheckPermission();
        mTxtTitle.getPaint().setShader(getShader(mTxtTitle));
    }


    private void mCheckPermission() {
        String[] permissions;

        if (Global.isLatestVersion()) {
            permissions = new String[]{PermitConstant.Manifest_CAMERA, PermitConstant.READ_MEDIA_IMAGES,PermitConstant.READ_MEDIA_VIDEOS, PermitConstant.POST_NOTIFICATIONS};
        } else {
            permissions = new String[]{PermitConstant.Manifest_CAMERA, PermitConstant.Manifest_READ_EXTERNAL_STORAGE,  PermitConstant.Manifest_WRITE_EXTERNAL_STORAGE};

        }
        if (!isPermissionsGranted(HomeActivity.this, permissions)) {
            askCompactPermissions(permissions, new PermissionResult() {
                @Override
                public void permissionGranted() {
                }

                @Override
                public void permissionDenied() {
//                    Utils.showToast(HomeActivity.this, "Permission Denied..!");
                }

                @Override
                public void permissionForeverDenied() {
//                    Utils.showToast(HomeActivity.this, "Permission Forever Denied..!");
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HomeActivity.this, ExitActivity.class));
    }

    @OnClick({R.id.mRLStarted, R.id.mRLShare, R.id.mRLRateUs, R.id.mRLPrivacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mRLStarted:
                startActivity(new Intent(HomeActivity.this, StartActivity.class));
                break;
            case R.id.mRLShare:
                mShareApp();
                break;
            case R.id.mRLRateUs:
                mRateApp();
                break;
            case R.id.mRLPrivacy:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/OmaPrakash"));
                startActivity(browserIntent);
                break;

        }
    }

    private void mShareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mRateApp() {
        new RateDialog(new RateDialog.OnClickListener() {

            @Override
            public void OnRate() {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("market://details?id=");
                    sb.append(getPackageName());
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                } catch (ActivityNotFoundException e) {
                    Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
                    startActivity(rateIntent);
                }
            }

            @Override
            public void OnCancel() {

            }
        }).show(getSupportFragmentManager(), "");

    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}