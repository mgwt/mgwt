/*
 * Copyright 2012 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.widget.carousel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.googlecode.mgwt.collection.shared.LightArrayInt;
import com.googlecode.mgwt.dom.client.event.orientation.OrientationChangeEvent;
import com.googlecode.mgwt.dom.client.event.orientation.OrientationChangeHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Justification;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollMoveEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollRefreshEvent;

/**
 * A carousel renders its children in a row.
 * A user can select a different child by swiping between them
 * or if the carousel indicator is displayed and supports the
 * indicators being tapped then a child maybe selected by tapping
 * one of the indicators in the carousel indicator widget
 */
public class Carousel extends Composite implements HasWidgets, HasSelectionHandlers<Integer> {

  @UiField
  public FlexPanel main;
  @UiField
  public ScrollPanel scrollPanel;
  @UiField
  public FlowPanel container;
  private FlexPanel carouselIndicatorContainer;
  private CarouselIndicator carouselIndicator;
  private boolean supportCarouselIndicatorTap = false;
  private boolean showCarouselIndicator = true;

  private int currentPage = 0;

  private Map<Widget, Widget> childToHolder;
  private HandlerRegistration refreshHandler;

  private static final CarouselImpl IMPL = GWT.create(CarouselImpl.class);

  private static final CarouselAppearance DEFAULT_APPEARANCE = GWT.create(CarouselAppearance.class);
  private final CarouselAppearance appearance;
  private boolean hasScollData;

  public Carousel() {
    this(false);
  }

  public Carousel(boolean supportCarouselIndicatorTap) {
    this(DEFAULT_APPEARANCE, supportCarouselIndicatorTap);
  }

  public Carousel(CarouselAppearance appearance) {
    this(appearance, false);
  }

  public Carousel(CarouselAppearance appearance, boolean supportCarouselIndicatorTap) {
    this.appearance = appearance;
    this.supportCarouselIndicatorTap = supportCarouselIndicatorTap;
    initWidget(this.appearance.carouselBinder().createAndBindUi(this));
    
    childToHolder = new HashMap<Widget, Widget>();

    scrollPanel.setSnap(true);
    scrollPanel.setSnapThreshold(50);
    scrollPanel.setMomentum(false);
    scrollPanel.setShowVerticalScrollBar(false);
    scrollPanel.setShowHorizontalScrollBar(false);
    scrollPanel.setScrollingEnabledY(true);
    scrollPanel.setAutoHandleResize(false);

    scrollPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {

      @Override
      public void onScrollEnd(ScrollEndEvent event) {
        int page = scrollPanel.getCurrentPageX();

        currentPage = page;
        // Selection handler updates carousel indicator, so no need to update it here
        SelectionEvent.fire(Carousel.this, currentPage);

      }
    });

    scrollPanel.addScrollMoveHandler(new ScrollMoveEvent.Handler() {

      @Override
      public void onScrollMove(ScrollMoveEvent event) {
        TouchMoveEvent moveEvent = event.getEvent();
        moveEvent.stopPropagation();
        moveEvent.preventDefault();
      }
    });

    MGWT.addOrientationChangeHandler(new OrientationChangeHandler() {

      @Override
      public void onOrientationChanged(OrientationChangeEvent event) {
        refresh();
      }
    });

    addSelectionHandler(new SelectionHandler<Integer>() {

      @Override
      public void onSelection(SelectionEvent<Integer> event) {
        if (carouselIndicator != null) {
          carouselIndicator.setIndicatorSelected(currentPage);
        }
      }
    });

    if (MGWT.getOsDetection().isDesktop()) {
      Window.addResizeHandler(new ResizeHandler() {

        @Override
        public void onResize(ResizeEvent event) {
          refresh();
        }
      });
    }

  }

  @Override
  public void add(Widget w) {

    FlowPanel widgetHolder = new FlowPanel();
    widgetHolder.addStyleName(this.appearance.cssCarousel().carouselHolder());
    widgetHolder.add(w);

    childToHolder.put(w, widgetHolder);

    container.add(widgetHolder);

    IMPL.adjust(main, container);

  }

  @Override
  public void clear() {
    removeCarouselIndicator();
    container.clear();
    childToHolder.clear();
    currentPage = 0;
  }

  @Override
  public Iterator<Widget> iterator() {
    Set<Widget> keySet = childToHolder.keySet();
    return keySet.iterator();
  }

  @Override
  public boolean remove(Widget w) {
    Widget holder = childToHolder.remove(w);
    if (holder != null) {
      return container.remove(holder);
    }
    return false;

  }

  @Override
  protected void onLoad() {
    refresh();
  }

  /**
   * refresh the carousel widget, this is necessary after changing child elements
   */
  public void refresh() {
    hasScollData = false;
    final int delay = MGWT.getOsDetection().isAndroid() ? 200 : 1;
    IMPL.adjust(main, container);
    // allow layout to happen..
    new Timer() {

      @Override
      public void run() {
        IMPL.adjust(main, container);

        scrollPanel.setScrollingEnabledX(true);
        scrollPanel.setScrollingEnabledY(false);

        scrollPanel.setShowVerticalScrollBar(false);
        scrollPanel.setShowHorizontalScrollBar(false);

        int widgetCount = container.getWidgetCount();

        if (currentPage >= widgetCount) {
          currentPage = widgetCount - 1;
        }

        refreshCarouselIndicator();

        refreshHandler = scrollPanel.addScrollRefreshHandler(new ScrollRefreshEvent.Handler() {

          @Override
          public void onScrollRefresh(ScrollRefreshEvent event) {
            // on desktop IE11 can be called twice
            if (refreshHandler != null) {
              refreshHandler.removeHandler();
              refreshHandler = null;
              LightArrayInt pagesX = scrollPanel.getPagesX();
              if (currentPage < 0) {
                currentPage = 0;
              } else if(currentPage >= pagesX.length()) {
                currentPage = pagesX.length() - 1;
              }
              scrollPanel.scrollToPage(currentPage, 0, 0);
              hasScollData = true;
            }
          }
        });
        scrollPanel.refresh();
      }

    }.schedule(delay);

  }

  public void setSelectedPage(int index) {
    setSelectedPage(index, true);
  }

  public void setSelectedPage(int index, boolean issueEvent) {
    if (isAttached() && hasScollData) {
      LightArrayInt pagesX = scrollPanel.getPagesX();
      if (index < 0 || index >= pagesX.length()) {
        throw new IllegalArgumentException("invalid value for index: " + index);
      }
      currentPage = index;
      scrollPanel.scrollToPage(index, 0, 300, issueEvent);
    } else {
      currentPage = index;
    }
    if (carouselIndicator != null) {
      carouselIndicator.setIndicatorSelected(currentPage);
    }
  }

  public int getSelectedPage() {
    return currentPage;
  }

  public void setSupportCarouselIndicatorTap(boolean supportCarouselIndicatorTap) {
    this.supportCarouselIndicatorTap = supportCarouselIndicatorTap;
    if (carouselIndicator != null) {
      carouselIndicator.setEnableTapSelection(supportCarouselIndicatorTap);
    }
  }

  public void setShowCarouselIndicator(boolean showCarouselIndicator) {
    this.showCarouselIndicator = showCarouselIndicator;
    refreshCarouselIndicator();
  }

  @Override
  public com.google.gwt.event.shared.HandlerRegistration addSelectionHandler(
      SelectionHandler<Integer> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }

  interface CarouselImpl {
    void adjust(Widget main, FlowPanel container);
  }

  // GWT rebinding
  @SuppressWarnings("unused")
  private static class CarouselImplSafari implements CarouselImpl {

    @Override
    public void adjust(Widget main, FlowPanel container) {
      int widgetCount = container.getWidgetCount();

      double scaleFactor = 100d / widgetCount;

      for (int i = 0; i < widgetCount; i++) {
        Widget w = container.getWidget(i);
        w.setWidth(scaleFactor + "%");
        w.getElement().getStyle().setLeft(i * scaleFactor, Unit.PCT);
      }

      container.setWidth((widgetCount * 100) + "%");
      container.getElement().getStyle().setHeight(main.getOffsetHeight(), Unit.PX);
    }

  }

  //GWT rebinding
  @SuppressWarnings("unused")
  private static class CarouselImplGecko implements CarouselImpl {

    @Override
    public void adjust(Widget main, FlowPanel container) {
      int widgetCount = container.getWidgetCount();
      int offsetWidth = main.getOffsetWidth();

      container.setWidth(widgetCount * offsetWidth + "px");

      for (int i = 0; i < widgetCount; i++) {
        container.getWidget(i).setWidth(offsetWidth + "px");
      }
    }
  }

  public ScrollPanel getScrollPanel() {
    return scrollPanel;
  }

  @UiFactory
  public CarouselAppearance getAppearance() {
    return appearance;
  }

  private void refreshCarouselIndicator() {
    removeCarouselIndicator();
    if (showCarouselIndicator) {
      carouselIndicatorContainer = new FlexPanel();
      carouselIndicatorContainer.setOrientation(Orientation.HORIZONTAL);
      carouselIndicatorContainer.setJustification(Justification.CENTER);
      carouselIndicatorContainer.addStyleName(appearance.cssCarousel().indicatorMain());
      carouselIndicator = new CarouselIndicator(appearance.cssCarousel());
      carouselIndicator.setEnableTapSelection(supportCarouselIndicatorTap);
      carouselIndicator.setNumberOfIndicators(container.getWidgetCount());
      carouselIndicator.setIndicatorSelected(currentPage);
      carouselIndicatorContainer.add(carouselIndicator);
      main.add(carouselIndicatorContainer);
      
      carouselIndicator.addSelectionHandler(new SelectionHandler<Integer>() {
        @Override
        public void onSelection(SelectionEvent<Integer> event) {
          setSelectedPage(event.getSelectedItem().intValue());
        }
      });
    }
  }

  private void removeCarouselIndicator()
  {
    if (carouselIndicatorContainer != null) {
      carouselIndicatorContainer.removeFromParent();
      carouselIndicatorContainer = null;
      carouselIndicator = null;
    }
  }
}
