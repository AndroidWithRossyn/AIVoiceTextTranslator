package com.aivoice.translate.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.aivoice.translate.AdsUtils.Interfaces.AppInterfaces;
import com.aivoice.translate.AdsUtils.Utils.Global;
import com.aivoice.translate.base.PermitConstant;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.aivoice.translate.AdsUtils.FirebaseADHandlers.AdUtils;
import com.aivoice.translate.AdsUtils.Utils.Constants;
import com.aivoice.translate.R;
import com.aivoice.translate.base.BaseActivity;
import com.aivoice.translate.utils.Utils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity {
    @BindView(R.id.surface_view)
    SurfaceView cameraView;
    CameraSource cameraSource;
    String mText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        AdUtils.showNativeAd(CameraActivity.this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), (LinearLayout) findViewById(R.id.native_small_ads), false);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(CameraActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    22);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }
                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {

                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < items.size(); i++) {
                            TextBlock item = items.valueAt(i);
                            stringBuilder.append(item.getValue());
                            stringBuilder.append("\n");
                            mText = stringBuilder.toString();
                        }
                    }
                }
            });
        }
    }
    @OnClick({R.id.mIVBack, R.id.mIVGallery, R.id.mIVCamera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIVBack:
                AdUtils.showInterstitialAd(CameraActivity.this, new AppInterfaces.InterStitialADInterface() {
                    @Override
                    public void adLoadState(boolean isLoaded) {
                        CameraActivity.super.onBackPressed();
                    }
                });
                break;
            case R.id.mIVGallery:
                String[] permissions;

                if (Global.isLatestVersion()) {
                    permissions = new String[]{PermitConstant.Manifest_READ_EXTERNAL_STORAGE};
                } else {
                    permissions = new String[]{PermitConstant.Manifest_READ_EXTERNAL_STORAGE,
                            PermitConstant.Manifest_WRITE_EXTERNAL_STORAGE};
                }
                askCompactPermissions(permissions, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        Utils.displayProgress(CameraActivity.this);
                        ImagePicker.Companion.with(CameraActivity.this).crop().galleryOnly().galleryMimeTypes(new String[]{"image/png", "image/jpg", "image/jpeg"}).maxResultSize(1080, 1920).start(102);
                    }

                    @Override
                    public void permissionDenied() {
                        Utils.showToast(CameraActivity.this, "Permission Denied..!");
                    }

                    @Override
                    public void permissionForeverDenied() {
                        Utils.showToast(CameraActivity.this, "Permission Forever Denied..!");
                    }
                });
                break;
            case R.id.mIVCamera:
                Utils.TEXTEXTRACT = mText;
                if (!Utils.isEmptyStr(Utils.TEXTEXTRACT)) {
                    startActivity(new Intent(CameraActivity.this, TextActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "There is no Data..!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 22: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Uri uri = data != null ? data.getData() : null;
            ImageView mPreviewIv = new ImageView(CameraActivity.this);
            mPreviewIv.setImageURI(uri);
//            mPath = uri.getPath();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

            if (!recognizer.isOperational()) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            } else {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = recognizer.detect(frame);
                StringBuilder sb = new StringBuilder();
                //get text from sb until there is no text
                for (int i = 0; i < items.size(); i++) {
                    TextBlock myItem = items.valueAt(i);
                    sb.append(myItem.getValue());
                    sb.append("\n");
                }
                Utils.dismissProgress();
                Utils.TEXTEXTRACT = sb.toString();
                if (!Utils.isEmptyStr(Utils.TEXTEXTRACT)) {
                    startActivity(new Intent(CameraActivity.this, TextActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "There is no Data..!", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            Utils.dismissProgress();
        }
    }

    @Override
    public void onBackPressed() {
        AdUtils.showInterstitialAd(CameraActivity.this, new AppInterfaces.InterStitialADInterface() {
            @Override
            public void adLoadState(boolean isLoaded) {
                CameraActivity.super.onBackPressed();
            }
        });
    }
}