package com.googlecode.mgwt.ui.client.theme.platform.list.celllist;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.googlecode.mgwt.ui.client.widget.list.celllist.GroupingCellListAbstractAppearance;

public class GroupingCellListIOSAppearance extends GroupingCellListAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
    Resources.INSTANCE.groupCss().ensureInjected();
  }

  interface Css extends CellListCss {}
  interface CssGroup extends GroupingListCss {}

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"com/googlecode/mgwt/ui/client/widget/list/celllist/celllist.gss", "celllist-ios.gss", "celllist-grouping-ios.gss"})
    Css css();

    @Source({
        "com/googlecode/mgwt/ui/client/widget/list/celllist/grouping-celllist.gss",
        "grouping-celllist-ios.gss"})
    CssGroup groupCss();

    @Source("arrow.png")
    DataResource listArrow();
  }

  @Override
  public CellListCss css() {
    return Resources.INSTANCE.css();
  }

  @Override
  public GroupingListCss groupCss() {
    return Resources.INSTANCE.groupCss();
  }
}
