// Generated code from Butter Knife. Do not modify!
package com.aivoice.translate.Activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.aivoice.translate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StartActivity_ViewBinding implements Unbinder {
  private StartActivity target;

  private View view7f0a010b;

  private View view7f0a0112;

  private View view7f0a0110;

  private View view7f0a010c;

  @UiThread
  public StartActivity_ViewBinding(StartActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StartActivity_ViewBinding(final StartActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.mIVBack, "method 'onViewClicked'");
    view7f0a010b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mIVVoice, "method 'onViewClicked'");
    view7f0a0112 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mIVText, "method 'onViewClicked'");
    view7f0a0110 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mIVCamera, "method 'onViewClicked'");
    view7f0a010c = view;
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
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view7f0a010b.setOnClickListener(null);
    view7f0a010b = null;
    view7f0a0112.setOnClickListener(null);
    view7f0a0112 = null;
    view7f0a0110.setOnClickListener(null);
    view7f0a0110 = null;
    view7f0a010c.setOnClickListener(null);
    view7f0a010c = null;
  }
}
