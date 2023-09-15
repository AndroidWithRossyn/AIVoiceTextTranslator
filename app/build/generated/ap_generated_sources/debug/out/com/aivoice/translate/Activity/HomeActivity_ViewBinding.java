// Generated code from Butter Knife. Do not modify!
package com.aivoice.translate.Activity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.aivoice.translate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  private View view7f0a011d;

  private View view7f0a011c;

  private View view7f0a011b;

  private View view7f0a011a;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(final HomeActivity target, View source) {
    this.target = target;

    View view;
    target.mTxtTitle = Utils.findRequiredViewAsType(source, R.id.mTxtTitle, "field 'mTxtTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.mRLStarted, "method 'onViewClicked'");
    view7f0a011d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mRLShare, "method 'onViewClicked'");
    view7f0a011c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mRLRateUs, "method 'onViewClicked'");
    view7f0a011b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mRLPrivacy, "method 'onViewClicked'");
    view7f0a011a = view;
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
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtTitle = null;

    view7f0a011d.setOnClickListener(null);
    view7f0a011d = null;
    view7f0a011c.setOnClickListener(null);
    view7f0a011c = null;
    view7f0a011b.setOnClickListener(null);
    view7f0a011b = null;
    view7f0a011a.setOnClickListener(null);
    view7f0a011a = null;
  }
}
