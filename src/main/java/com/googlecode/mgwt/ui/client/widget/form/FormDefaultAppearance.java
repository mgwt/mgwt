package com.googlecode.mgwt.ui.client.widget.form;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class FormDefaultAppearance extends FormAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"form.gss"})
    FormCss css();
  }

  @Override
  public FormCss css() {
    return Resources.INSTANCE.css();
  }
}
