/*
 *
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
package com.googlecode.mgwt.ui.client.theme.platform.tabbar;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarAbstractAppearance;

public class TabBarAndroidAppearance extends TabBarAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
    Resources.INSTANCE.barCss().ensureInjected();
  }

  interface CssBar extends TabBarCss {}

  interface CssButton extends TabBarButtonCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/tabbar/tabbar.gss", "tabbar-android.gss"})
    CssBar barCss();
    @Source({"com/googlecode/mgwt/ui/client/widget/tabbar/tabbar-button.gss", "tabbar-button-android.gss"})
    CssButton css();
  }

  @Override
  public TabBarButtonCss css() {
    return Resources.INSTANCE.css();
  }

  @Override
  public TabBarCss barCss() {
    return Resources.INSTANCE.barCss();
  }
}
