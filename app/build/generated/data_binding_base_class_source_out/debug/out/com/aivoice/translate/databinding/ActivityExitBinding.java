// Generated by view binder compiler. Do not edit!
package com.aivoice.translate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.aivoice.translate.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityExitBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout bottomlayout;

  @NonNull
  public final LinearLayout btn;

  @NonNull
  public final TextView exitetxt;

  @NonNull
  public final LinearLayout nativeAds;

  @NonNull
  public final LinearLayout nativeAds1;

  @NonNull
  public final TextView no;

  @NonNull
  public final TextView rateus;

  @NonNull
  public final RelativeLayout rl1;

  @NonNull
  public final RelativeLayout rl2;

  @NonNull
  public final TextView yes;

  private ActivityExitBinding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout bottomlayout, @NonNull LinearLayout btn, @NonNull TextView exitetxt,
      @NonNull LinearLayout nativeAds, @NonNull LinearLayout nativeAds1, @NonNull TextView no,
      @NonNull TextView rateus, @NonNull RelativeLayout rl1, @NonNull RelativeLayout rl2,
      @NonNull TextView yes) {
    this.rootView = rootView;
    this.bottomlayout = bottomlayout;
    this.btn = btn;
    this.exitetxt = exitetxt;
    this.nativeAds = nativeAds;
    this.nativeAds1 = nativeAds1;
    this.no = no;
    this.rateus = rateus;
    this.rl1 = rl1;
    this.rl2 = rl2;
    this.yes = yes;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityExitBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityExitBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_exit, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityExitBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomlayout;
      RelativeLayout bottomlayout = ViewBindings.findChildViewById(rootView, id);
      if (bottomlayout == null) {
        break missingId;
      }

      id = R.id.btn;
      LinearLayout btn = ViewBindings.findChildViewById(rootView, id);
      if (btn == null) {
        break missingId;
      }

      id = R.id.exitetxt;
      TextView exitetxt = ViewBindings.findChildViewById(rootView, id);
      if (exitetxt == null) {
        break missingId;
      }

      id = R.id.native_ads;
      LinearLayout nativeAds = ViewBindings.findChildViewById(rootView, id);
      if (nativeAds == null) {
        break missingId;
      }

      id = R.id.native_ads1;
      LinearLayout nativeAds1 = ViewBindings.findChildViewById(rootView, id);
      if (nativeAds1 == null) {
        break missingId;
      }

      id = R.id.no;
      TextView no = ViewBindings.findChildViewById(rootView, id);
      if (no == null) {
        break missingId;
      }

      id = R.id.rateus;
      TextView rateus = ViewBindings.findChildViewById(rootView, id);
      if (rateus == null) {
        break missingId;
      }

      id = R.id.rl1;
      RelativeLayout rl1 = ViewBindings.findChildViewById(rootView, id);
      if (rl1 == null) {
        break missingId;
      }

      id = R.id.rl2;
      RelativeLayout rl2 = ViewBindings.findChildViewById(rootView, id);
      if (rl2 == null) {
        break missingId;
      }

      id = R.id.yes;
      TextView yes = ViewBindings.findChildViewById(rootView, id);
      if (yes == null) {
        break missingId;
      }

      return new ActivityExitBinding((RelativeLayout) rootView, bottomlayout, btn, exitetxt,
          nativeAds, nativeAds1, no, rateus, rl1, rl2, yes);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}