package com.aivoice.translate.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aivoice.translate.AdsUtils.FirebaseADHandlers.AdUtils;
import com.aivoice.translate.AdsUtils.Interfaces.AppInterfaces;
import com.aivoice.translate.AdsUtils.Utils.Constants;
import com.aivoice.translate.R;
import com.aivoice.translate.base.BaseActivity;
import com.aivoice.translate.base.PermitConstant;
import com.aivoice.translate.utils.Utils;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

import butterknife.OnClick;

public class StartActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        AdUtils.showNativeAd(StartActivity.this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), (LinearLayout) findViewById(R.id.native_ads), true);
    }

    @OnClick({R.id.mIVBack, R.id.mIVVoice, R.id.mIVText, R.id.mIVCamera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIVBack:
                AdUtils.showInterstitialAd(StartActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        StartActivity.super.onBackPressed();
                    }
                });
                break;
            case R.id.mIVVoice:
                mCheckVoicePermission();
                break;
            case R.id.mIVText:
                AdUtils.showInterstitialAd(StartActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        startActivity(new Intent(StartActivity.this, TextActivity.class));
                    }
                });
                break;
            case R.id.mIVCamera:
                CheckPermission();
                break;

        }
    }
    private void mCheckVoicePermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                AdUtils.showInterstitialAd(StartActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        startActivity(new Intent(StartActivity.this, VoiceActivity.class));
                    }
                });
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(StartActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT)
                        .show();
            }

        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage(R.string.rationale_message_voice)
                .setDeniedTitle("Permission denied")
                .setDeniedMessage(
                        "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("Settings")
                .setPermissions(Manifest.permission.RECORD_AUDIO)
                .check();

    }
    private void CheckPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                AdUtils.showInterstitialAd(StartActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        startActivity(new Intent(StartActivity.this, CameraActivity.class));
                    }
                });
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(StartActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT)
                        .show();
            }

        };
        if (Utils.isLatestVersion()) {
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setRationaleTitle(R.string.rationale_title)
                    .setRationaleMessage(R.string.rationale_message)
                    .setDeniedTitle("Permission denied")
                    .setDeniedMessage(
                            "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setGotoSettingButtonText("Settings")
                    .setPermissions(Manifest.permission.CAMERA, PermitConstant.Manifest_READ_EXTERNAL_STORAGE)
                    .check();
        } else {
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setRationaleTitle(R.string.rationale_title)
                    .setRationaleMessage(R.string.rationale_message)
                    .setDeniedTitle("Permission denied")
                    .setDeniedMessage(
                            "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setGotoSettingButtonText("Settings")
                    .setPermissions(Manifest.permission.CAMERA,PermitConstant.Manifest_READ_EXTERNAL_STORAGE, PermitConstant.Manifest_WRITE_EXTERNAL_STORAGE)
                    .check();
        }

       /* String[] permissions;

        if (Global.isLatestVersion()) {
            permissions = new String[]{PermitConstant.Manifest_READ_EXTERNAL_STORAGE};
        } else {
            permissions = new String[]{PermitConstant.Manifest_READ_EXTERNAL_STORAGE,
                    PermitConstant.Manifest_WRITE_EXTERNAL_STORAGE};
        }
        if (!isPermissionsGranted(StartActivity.this, permissions)) {
            askCompactPermissions(permissions, new PermissionResult() {
                @Override
                public void permissionGranted() {
                    AdUtils.showInterstitialAd(StartActivity.this, new AppInterfaces.InterStitialADInterface() {
                        @Override
                        public void adLoadState(boolean isLoaded) {
                            startActivity(new Intent(StartActivity.this, CameraActivity.class));
                        }
                    });
                }

                @Override
                public void permissionDenied() {
                    Utils.showToast(StartActivity.this, "Permission Denied..!");
                }

                @Override
                public void permissionForeverDenied() {
                    Utils.showToast(StartActivity.this, "Permission Forever Denied..!");
                }
            });
        }*/
    }

    @Override
    public void onBackPressed() {
        AdUtils.showInterstitialAd(StartActivity.this, new AppInterfaces.InterStitialADInterface() {
            @Override
            public void adLoadState(boolean isLoaded) {
                StartActivity.super.onBackPressed();
            }
        });
    }
}