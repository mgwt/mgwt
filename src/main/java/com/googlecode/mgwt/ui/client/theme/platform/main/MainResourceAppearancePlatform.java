package com.googlecode.mgwt.ui.client.theme.platform.main;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

import com.googlecode.mgwt.ui.client.widget.main.MainResourceDefaultAppearance;

public class MainResourceAppearancePlatform extends MainResourceDefaultAppearance {

  interface Css extends MainCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/main/main.gss", "main.gss"})
    Css css();
  }

  @Override
  public MainCss css() {
    return Resources.INSTANCE.css();
  }
}
