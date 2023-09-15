// Generated code from Butter Knife. Do not modify!
package com.aivoice.translate.Activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.aivoice.translate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IntroActivity_ViewBinding implements Unbinder {
  private IntroActivity target;

  private View view7f0a0129;

  private View view7f0a0127;

  @UiThread
  public IntroActivity_ViewBinding(IntroActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public IntroActivity_ViewBinding(final IntroActivity target, View source) {
    this.target = target;

    View view;
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.mViewPager, "field 'mViewPager'", ViewPager.class);
    target.mLLTop = Utils.findRequiredViewAsType(source, R.id.mLLTop, "field 'mLLTop'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.mTxtSkip, "field 'mTxtSkip' and method 'onViewClicked'");
    target.mTxtSkip = Utils.castView(view, R.id.mTxtSkip, "field 'mTxtSkip'", TextView.class);
    view7f0a0129 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mLayoutDots = Utils.findRequiredViewAsType(source, R.id.mLayoutDots, "field 'mLayoutDots'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.mTxtNext, "field 'mTxtNext' and method 'onViewClicked'");
    target.mTxtNext = Utils.castView(view, R.id.mTxtNext, "field 'mTxtNext'", TextView.class);
    view7f0a0127 = view;
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
    IntroActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.mLLTop = null;
    target.mTxtSkip = null;
    target.mLayoutDots = null;
    target.mTxtNext = null;

    view7f0a0129.setOnClickListener(null);
    view7f0a0129 = null;
    view7f0a0127.setOnClickListener(null);
    view7f0a0127 = null;
  }
}
