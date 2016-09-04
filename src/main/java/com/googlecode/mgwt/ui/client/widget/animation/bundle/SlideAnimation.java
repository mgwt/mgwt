package com.googlecode.mgwt.ui.client.widget.animation.bundle;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

import com.googlecode.mgwt.ui.client.widget.animation.Animation;

public class SlideAnimation extends AnimationBase {

  interface Bundle extends ClientBundle {
    @Source({"slide.gss"})
    CSS css();
  }

  public interface CSS extends Animation.AnimationCss, CssResource {
    @Override
    String in();

    @Override
    String out();

    @Override
    String reverse();
  }

  private static final Bundle bundle = GWT.create(Bundle.class);

  static {
    bundle.css().ensureInjected();
  }

  public SlideAnimation(boolean reversed) {
    super("mgwt-slide", reversed);
  }

  @Override
  public CSS css() {
    return bundle.css();
  }
}
