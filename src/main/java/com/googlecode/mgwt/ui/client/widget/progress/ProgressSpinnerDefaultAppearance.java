/*
 * Copyright 2014 Katharina Fahnenbruck
 */
package com.googlecode.mgwt.ui.client.widget.progress;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class ProgressSpinnerDefaultAppearance extends ProgressSpinnerAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"progressspinner.gss"})
    ProgressSpinnerCss css();
  }

  @Override
  public ProgressSpinnerCss css() {
    return Resources.INSTANCE.css();
  }
}

