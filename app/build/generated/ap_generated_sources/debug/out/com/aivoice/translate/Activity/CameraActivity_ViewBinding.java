// Generated code from Butter Knife. Do not modify!
package com.aivoice.translate.Activity;

import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.aivoice.translate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CameraActivity_ViewBinding implements Unbinder {
  private CameraActivity target;

  private View view7f0a0113;

  private View view7f0a0116;

  private View view7f0a0114;

  @UiThread
  public CameraActivity_ViewBinding(CameraActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CameraActivity_ViewBinding(final CameraActivity target, View source) {
    this.target = target;

    View view;
    target.cameraView = Utils.findRequiredViewAsType(source, R.id.surface_view, "field 'cameraView'", SurfaceView.class);
    view = Utils.findRequiredView(source, R.id.mIVBack, "method 'onViewClicked'");
    view7f0a0113 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mIVGallery, "method 'onViewClicked'");
    view7f0a0116 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mIVCamera, "method 'onViewClicked'");
    view7f0a0114 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CameraActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cameraView = null;

    view7f0a0113.setOnClickListener(null);
    view7f0a0113 = null;
    view7f0a0116.setOnClickListener(null);
    view7f0a0116 = null;
    view7f0a0114.setOnClickListener(null);
    view7f0a0114 = null;
  }
}
