package com.googlecode.mgwt.ui.client.widget.panel.scroll;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class ScrollPanelDefaultAppearance implements ScrollPanelAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"scrollpanel.gss"})
    ScrollPanelCss css();
  }
  
  @Override
  public ScrollPanelCss css() {
    return Resources.INSTANCE.css();
  }
}
