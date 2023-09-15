// Generated code from Butter Knife. Do not modify!
package com.aivoice.translate.Activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.aivoice.translate.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VoiceActivity_ViewBinding implements Unbinder {
  private VoiceActivity target;

  private View view7f0a0123;

  private View view7f0a012b;

  private View view7f0a010f;

  private View view7f0a010b;

  private View view7f0a0111;

  private View view7f0a011e;

  @UiThread
  public VoiceActivity_ViewBinding(VoiceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VoiceActivity_ViewBinding(final VoiceActivity target, View source) {
    this.target = target;

    View view;
    target.mETInput = Utils.findRequiredViewAsType(source, R.id.mETInput, "field 'mETInput'", EditText.class);
    target.mTxtResult = Utils.findRequiredViewAsType(source, R.id.mTxtResult, "field 'mTxtResult'", TextView.class);
    target.mTxtInput = Utils.findRequiredViewAsType(source, R.id.mTxtInput, "field 'mTxtInput'", TextView.class);
    view = Utils.findRequiredView(source, R.id.mTxtFromLang, "field 'mTxtFromLang' and method 'onViewClicked'");
    target.mTxtFromLang = Utils.castView(view, R.id.mTxtFromLang, "field 'mTxtFromLang'", TextView.class);
    view7f0a0123 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mTxtToLang, "field 'mTxtToLang' and method 'onViewClicked'");
    target.mTxtToLang = Utils.castView(view, R.id.mTxtToLang, "field 'mTxtToLang'", TextView.class);
    view7f0a012b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mIVClear = Utils.findRequiredViewAsType(source, R.id.mIVClear, "field 'mIVClear'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.mIVRecordVoice, "field 'mIVRecordVoice' and method 'onViewClicked'");
    target.mIVRecordVoice = Utils.castView(view, R.id.mIVRecordVoice, "field 'mIVRecordVoice'", ImageView.class);
    view7f0a010f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mIVText = Utils.findRequiredViewAsType(source, R.id.mIVText, "field 'mIVText'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.mIVBack, "method 'onViewClicked'");
    view7f0a010b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mIVTrans, "method 'onViewClicked'");
    view7f0a0111 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mRLTranslate, "method 'onViewClicked'");
    view7f0a011e = view;
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
    VoiceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mETInput = null;
    target.mTxtResult = null;
    target.mTxtInput = null;
    target.mTxtFromLang = null;
    target.mTxtToLang = null;
    target.mIVClear = null;
    target.mIVRecordVoice = null;
    target.mIVText = null;

    view7f0a0123.setOnClickListener(null);
    view7f0a0123 = null;
    view7f0a012b.setOnClickListener(null);
    view7f0a012b = null;
    view7f0a010f.setOnClickListener(null);
    view7f0a010f = null;
    view7f0a010b.setOnClickListener(null);
    view7f0a010b = null;
    view7f0a0111.setOnClickListener(null);
    view7f0a0111 = null;
    view7f0a011e.setOnClickListener(null);
    view7f0a011e = null;
  }
}
