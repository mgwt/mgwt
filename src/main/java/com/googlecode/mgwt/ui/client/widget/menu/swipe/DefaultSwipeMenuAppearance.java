/*
 * Copyright 2013 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.widget.menu.swipe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

/**
 * Considered experimental and might change, use at your own risk
 */
public class DefaultSwipeMenuAppearance implements SwipeMenuAppearance {

  private static SwipeMenuUiBinder uiBinder = GWT.create(SwipeMenuUiBinder.class);

  @UiTemplate("DefaultSwipeMenu.ui.xml")
  interface SwipeMenuUiBinder extends UiBinder<Widget, SwipeMenu> {
  }

  public interface Resource extends ClientBundle {

    public static Resource INSTANCE = GWT.create(Resource.class);

    @Source("swipe-menu.gss")
    public SwipeMenuCss css();
  }

  static {
    Resource.INSTANCE.css().ensureInjected();
  }

  @Override
  public UiBinder<Widget, SwipeMenu> uiBinder() {
    return uiBinder;
  }

  @Override
  public SwipeMenuCss css() {
    return Resource.INSTANCE.css();
  }

}
