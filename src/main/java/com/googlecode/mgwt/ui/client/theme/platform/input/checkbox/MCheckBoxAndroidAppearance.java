/*
 * Copyright 2014 Katharina Fahnenbruck
 */
package com.googlecode.mgwt.ui.client.theme.platform.input.checkbox;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;

import com.googlecode.mgwt.ui.client.widget.input.checkbox.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.input.checkbox.MCheckBoxAppearance;

public class MCheckBoxAndroidAppearance implements MCheckBoxAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Css extends CheckBoxCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/input/checkbox/checkbox.gss", "checkbox-android.gss"})
    Css css();
  }

  @Override
  public CheckBoxCss css() {
    return Resources.INSTANCE.css();
  }

  @UiTemplate("MCheckBoxAndroidAppearance.ui.xml")
  interface Binder extends UiBinder<Element, MCheckBox> {
  }

  private static final Binder UI_BINDER = GWT.create(Binder.class);

  @Override
  public UiBinder<? extends Element, MCheckBox> uiBinder() {
    return UI_BINDER;
  }
}
