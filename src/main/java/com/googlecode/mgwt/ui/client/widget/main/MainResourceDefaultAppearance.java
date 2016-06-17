/*
 * Copyright 2014 Daniel Kurka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.mgwt.ui.client.widget.main;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public class MainResourceDefaultAppearance implements MainResourceAppearance {

  private static class MyUtilCss implements UtilCss {

    private String text;

    public MyUtilCss(String text) {
      this.text = text;
    }

    private boolean injected;

    @Override
    public boolean ensureInjected() {
      if(injected) {
        return false;
      }
      injected = true;
      StyleInjector.inject(getText());
      return true;
    }

    @Override
    public String getText() {
      return text;
    }

    @Override
    public String getName() {
      return "";
    }

    @Override
    public String landscapeonly() {
      return "landscapeonly";
    }

    @Override
    public String portraitonly() {
      return "portraitonly";
    }

    @Override
    public String portrait() {
      return "portrait";
    }

    @Override
    public String landscape() {
      return "landscape";
    }

  }

  private static MyUtilCss utilCss;

  static {
    utilCss = new MyUtilCss(Resources.INSTANCE.utilTextResource().getText());
  }

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"main.gss"})
    MainCss css();

    @Source({"selection.gss"})
    SelectionCss selectionCss();

    // This is a very nasty workaround because GWT CssResource does not support
    // @media correctly!
    @Source("util.gss")
    TextResource utilTextResource();
  }

  @Override
  public MainCss css() {
    return Resources.INSTANCE.css();
  }

  @Override
  public SelectionCss selectionCss() {
    return Resources.INSTANCE.selectionCss();
  }

  @Override
  public UtilCss utilCss() {
    return utilCss;
  }
}
