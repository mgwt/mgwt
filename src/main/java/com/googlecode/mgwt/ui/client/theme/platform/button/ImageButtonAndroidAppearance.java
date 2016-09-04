package com.googlecode.mgwt.ui.client.theme.platform.button;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

import com.googlecode.mgwt.ui.client.widget.button.ImageButtonAbstractAppearance;

public class ImageButtonAndroidAppearance extends ImageButtonAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Css extends ImageButtonCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/button/imagebutton.gss", "imagebutton-android.gss"})
    Css css();
  }

  @Override
  public ImageButtonCss css() {
    return Resources.INSTANCE.css();
  }
}
