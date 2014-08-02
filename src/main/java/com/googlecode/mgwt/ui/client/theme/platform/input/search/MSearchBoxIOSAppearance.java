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
package com.googlecode.mgwt.ui.client.theme.platform.input.search;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBoxAbstractAppearance;

public class MSearchBoxIOSAppearance extends MSearchBoxAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Resources extends ClientBundle {
    Resources INSTANCE = GWT.create(Resources.class);
    @Source({"com/googlecode/mgwt/ui/client/widget/input/search/searchbox.css", "searchbox-ios.css"})
    MSearchBoxCss css();

    @Source("com/googlecode/mgwt/ui/client/widget/input/search/search_mdpi.png")
    ImageResource searchSearchImage();

    @Source("com/googlecode/mgwt/ui/client/widget/input/search/clear_mdpi.png")
    ImageResource searchClearImage();
  }

  @Override
  public MSearchBoxCss css() {
    return Resources.INSTANCE.css();
  }
}
