/*
 * Copyright 2014 Daniel Kurka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.mgwt.ui.client.theme.platform.dialog.options;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

import com.googlecode.mgwt.ui.client.widget.dialog.options.OptionsDialogAppearance;
import com.googlecode.mgwt.ui.client.widget.dialog.overlay.DialogOverlayDefaultAppearance;

public class OptionsDialogIOSAppearance extends DialogOverlayDefaultAppearance implements OptionsDialogAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Css extends OptionsDialogCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/dialog/options/options-dialog.gss", "options-dialog-ios.gss"})
    Css css();
  }

  @Override
  public OptionsDialogCss css() {
    return Resources.INSTANCE.css();
  }
}
