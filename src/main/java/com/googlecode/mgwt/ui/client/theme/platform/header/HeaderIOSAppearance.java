package com.googlecode.mgwt.ui.client.theme.platform.header;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

import com.googlecode.mgwt.ui.client.widget.header.HeaderAbstractAppearance;

public class HeaderIOSAppearance extends HeaderAbstractAppearance {

  static {
    Resources.INSTANCE.cssPanel().ensureInjected();
    Resources.INSTANCE.cssTitle().ensureInjected();
  }

  interface CssPanel extends HeaderPanelCss {}
  interface CssTitle extends HeaderTitleCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/header/header.gss", "header-ios.gss"})
    CssPanel cssPanel();

    @Source({"com/googlecode/mgwt/ui/client/widget/header/header-title.gss", "header-title-ios.gss"})
    CssTitle cssTitle();
  }

  @Override
  public HeaderPanelCss cssPanel() {
    return Resources.INSTANCE.cssPanel();
  }

  @Override
  public HeaderTitleCss cssTitle() {
    return Resources.INSTANCE.cssTitle();
  }
}
