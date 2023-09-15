// Generated code from Butter Knife. Do not modify!
package com.aivoice.translate.Activity;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.aivoice.translate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LanguageActivity_ViewBinding implements Unbinder {
  private LanguageActivity target;

  private View view7f0a010b;

  private View view7f0a0114;

  @UiThread
  public LanguageActivity_ViewBinding(LanguageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LanguageActivity_ViewBinding(final LanguageActivity target, View source) {
    this.target = target;

    View view;
    target.mETSearchInput = Utils.findRequiredViewAsType(source, R.id.mETSearchInput, "field 'mETSearchInput'", EditText.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.mRecyclerView, "field 'mRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.mIVBack, "method 'onViewClicked'");
    view7f0a010b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mIvSearch, "method 'onViewClicked'");
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
    LanguageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mETSearchInput = null;
    target.mRecyclerView = null;

    view7f0a010b.setOnClickListener(null);
    view7f0a010b = null;
    view7f0a0114.setOnClickListener(null);
    view7f0a0114 = null;
  }
}
