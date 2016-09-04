/*
 * Copyright 2014 Katharina Fahnenbruck
 */
package com.googlecode.mgwt.ui.client.theme.platform.input.checkbox;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.googlecode.mgwt.ui.client.widget.input.checkbox.MCheckBoxAbstractAppearance;

public class MCheckBoxIOSAppearance extends MCheckBoxAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface IOSCheckBoxCss extends CheckBoxCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/input/checkbox/checkbox.gss", "checkbox-ios.gss"})
    IOSCheckBoxCss css();
  }

  @Override
  public CheckBoxCss css() {
    return Resources.INSTANCE.css();
  }
}

