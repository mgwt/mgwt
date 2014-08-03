package com.googlecode.mgwt.ui.client.widget.input.search;

import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

import com.googlecode.mgwt.ui.client.widget.button.ButtonBaseAppearance;
import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox.MSearchBoxButton;

public interface MSearchBoxAppearance extends ButtonBaseAppearance {
  interface MSearchBoxCss extends ButtonBaseCss {

    @ClassName("mgwt-SearchBox")
    public String searchBox();

    @ClassName("mgwt-SearchBox-round")
    public String round();

    @Override
    @ClassName("mgwt-SearchBox-clear")
    public String button();

    @Override
    @ClassName("mgwt-SearchBox-clear-active")
    public String active();

    @ClassName("mgwt-SearchBox-input")
    public String input();

    @ClassName("mgwt-SearchBox-icon")
    public String searchIcon();
  }

  MSearchBoxCss css();

  UiBinder<Widget, MSearchBox> uiBinderBox();

  @Override
  UiBinder<Element, MSearchBoxButton> uiBinder();
}
