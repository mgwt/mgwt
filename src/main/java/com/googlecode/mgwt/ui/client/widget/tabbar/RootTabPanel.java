/*
 * Copyright 2010 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.widget.tabbar;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.googlecode.mgwt.dom.client.event.animation.TransitionEndEvent;
import com.googlecode.mgwt.dom.client.event.animation.TransitionEndHandler;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.util.CssUtil;
import com.googlecode.mgwt.ui.client.widget.animation.AnimatableDisplay;
import com.googlecode.mgwt.ui.client.widget.animation.AnimationWidget;
import com.googlecode.mgwt.ui.client.widget.panel.flex.RootFlexPanel;


/**
 * This TabPanel has support for content that can be animated.
 *
 * This is intended to be used as a root element of an app where the content of
 * the tab panel is switched according to state.
 *
 * It does not store the content of all tabs in it like {@link TabPanel}. It
 * only has a AnimateAble display that can change content with animations.
 *
 * You can use this panel with an {@link AnimatingActivityManager} or an
 * {@link AnimationHelper}. Simply register for
 * {@link #addSelectionHandler(SelectionHandler)} and trigger the animation you
 * want
 *
 * @author Daniel Kurka
 */
public class RootTabPanel extends Composite implements HasSelectionHandlers<Integer> {

  private RootFlexPanel container;

  private FlowPanel animatableDisplayContainer;

  private TabPanel.TabBar tabBar;

  private final AnimatableDisplay animatableDisplay;

  private HandlerRegistration hideTabBarHandler;

  private HandlerRegistration showTabBarHandler;

  public RootTabPanel() {
    this(new AnimationWidget());
  }

  public RootTabPanel(AnimatableDisplay display) {
    this.animatableDisplay = display;
    container = new RootFlexPanel();
    initWidget(container);
    animatableDisplayContainer = new FlowPanel();
    animatableDisplayContainer.getElement().getStyle().setPosition(Position.RELATIVE);
    animatableDisplayContainer.add(animatableDisplay);
    container.add(animatableDisplayContainer, 1);
    tabBar = new TabPanel.TabBar();
    tabBar.getElement().getStyle().setProperty("WebkitTransformProperty", "opacity");
    tabBar.getElement().getStyle().setProperty("MozTransformProperty", "opacity");
    container.add(tabBar);
  }

  public void showTabBar(boolean show) {

    if (!CssUtil.hasTransistionEndEvent()) {
      if (show) {
        tabBar.setVisible(true);
      } else {
        tabBar.setVisible(false);
      }
      return;
    }

    if (show) {
      CssUtil.setTransitionDuration(tabBar.getElement(), 200);
      CssUtil.setOpacity(tabBar.getElement(), 0);
      showTabBarHandler = tabBar.addDomHandler(new TransitionEndHandler() {

        @Override
        public void onTransitionEnd(TransitionEndEvent event) {
          if (showTabBarHandler != null) {
            showTabBarHandler.removeHandler();
            tabBar.setVisible(true);
            showTabBarHandler = null;
          }

        }
      }, TransitionEndEvent.getType());
    } else {
      CssUtil.setTransitionDuration(tabBar.getElement(), 200);
      CssUtil.setOpacity(tabBar.getElement(), 0);
      hideTabBarHandler = tabBar.addDomHandler(new TransitionEndHandler() {
        @Override
        public void onTransitionEnd(TransitionEndEvent event) {
          if (hideTabBarHandler != null) {
            hideTabBarHandler.removeHandler();
            tabBar.setVisible(false);
            hideTabBarHandler = null;
          }
        }
      }, TransitionEndEvent.getType());
    }
  }

  public AnimatableDisplay getAnimatableDisplay() {
    return animatableDisplay;
  }

  public void setSelectedChild(int index) {
    tabBar.setSelectedButton(index, true);
  }
  
  public void setSelectedChild(int index, boolean suppressEvent) {
  	tabBar.setSelectedButton(index, suppressEvent);
  }
  

  public void add(TabBarButtonBase button) {
    tabBar.add(button);
  }

  public void remove(int index) {
    tabBar.remove(index);
  }

  public void remove(TabBarButtonBase w) {
    int childIndex = tabBar.getIndexForWidget(w);
    tabBar.remove(childIndex);
  }

  @Override
  public com.google.gwt.event.shared.HandlerRegistration addSelectionHandler(
      SelectionHandler<Integer> handler) {
    return tabBar.addSelectionHandler(handler);
  }
}
