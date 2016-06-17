package com.googlecode.mgwt.ui.client.theme.platform.input.listbox;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

import com.googlecode.mgwt.ui.client.widget.input.listbox.MListBoxAppearance;

public class MListBoxAndroidAppearance implements MListBoxAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Css extends MListBoxCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/input/listbox/mlistbox.gss", "mlistbox-android.gss"})
    Css css();
  }

  @Override
  public MListBoxCss css() {
    return Resources.INSTANCE.css();
  }

}
