// Generated by view binder compiler. Do not edit!
package com.aivoice.translate.databinding;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.aivoice.translate.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCameraBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView mIVBack;

  @NonNull
  public final ImageView mIVCamera;

  @NonNull
  public final ImageView mIVGallery;

  @NonNull
  public final Toolbar mToolbar;

  @NonNull
  public final TextView mTxtTitle;

  @NonNull
  public final SurfaceView surfaceView;

  private ActivityCameraBinding(@NonNull LinearLayout rootView, @NonNull ImageView mIVBack,
      @NonNull ImageView mIVCamera, @NonNull ImageView mIVGallery, @NonNull Toolbar mToolbar,
      @NonNull TextView mTxtTitle, @NonNull SurfaceView surfaceView) {
    this.rootView = rootView;
    this.mIVBack = mIVBack;
    this.mIVCamera = mIVCamera;
    this.mIVGallery = mIVGallery;
    this.mToolbar = mToolbar;
    this.mTxtTitle = mTxtTitle;
    this.surfaceView = surfaceView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCameraBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCameraBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_camera, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCameraBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.mIVBack;
      ImageView mIVBack = ViewBindings.findChildViewById(rootView, id);
      if (mIVBack == null) {
        break missingId;
      }

      id = R.id.mIVCamera;
      ImageView mIVCamera = ViewBindings.findChildViewById(rootView, id);
      if (mIVCamera == null) {
        break missingId;
      }

      id = R.id.mIVGallery;
      ImageView mIVGallery = ViewBindings.findChildViewById(rootView, id);
      if (mIVGallery == null) {
        break missingId;
      }

      id = R.id.mToolbar;
      Toolbar mToolbar = ViewBindings.findChildViewById(rootView, id);
      if (mToolbar == null) {
        break missingId;
      }

      id = R.id.mTxtTitle;
      TextView mTxtTitle = ViewBindings.findChildViewById(rootView, id);
      if (mTxtTitle == null) {
        break missingId;
      }

      id = R.id.surface_view;
      SurfaceView surfaceView = ViewBindings.findChildViewById(rootView, id);
      if (surfaceView == null) {
        break missingId;
      }

      return new ActivityCameraBinding((LinearLayout) rootView, mIVBack, mIVCamera, mIVGallery,
          mToolbar, mTxtTitle, surfaceView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}