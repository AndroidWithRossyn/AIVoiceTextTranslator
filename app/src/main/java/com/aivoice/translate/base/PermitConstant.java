package com.aivoice.translate.base;

import android.Manifest;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class PermitConstant {

    public static final String Manifest_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    public static final String Manifest_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String Manifest_CAMERA = Manifest.permission.CAMERA;
    public static final String READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES;
    public static final String READ_MEDIA_VIDEOS = Manifest.permission.READ_MEDIA_VIDEO;
    public static final String POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS;

}
