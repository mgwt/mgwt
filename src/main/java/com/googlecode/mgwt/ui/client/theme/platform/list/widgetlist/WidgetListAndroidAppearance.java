package com.googlecode.mgwt.ui.client.theme.platform.list.widgetlist;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

import com.googlecode.mgwt.ui.client.widget.list.widgetlist.WidgetListAbstractAppearance;

public class WidgetListAndroidAppearance extends WidgetListAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Css extends WidgetListCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/list/widgetlist/widgetlist.gss", "widgetlist-android.gss"})
    Css css();
  }

  @Override
  public WidgetListCss css() {
    return Resources.INSTANCE.css();
  }
}
