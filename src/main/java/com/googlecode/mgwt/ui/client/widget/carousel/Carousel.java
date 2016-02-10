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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
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
import com.googlecode.mgwt.ui.client.widget.carousel.CarouselAppearance.CarouselCss;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Justification;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollMoveEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollRefreshEvent;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A carousel renders its children in a row.
 * A user can select a different child by swiping between them.
 *
 */
public class Carousel extends Composite implements HasWidgets, HasSelectionHandlers<Integer> {

  private static class CarouselIndicatorContainer extends Composite {
    private FlexPanel main;
    private final CarouselCss css;
    private ArrayList<CarouselIndicator> indicators;
    private int selectedIndex;

    public CarouselIndicatorContainer(CarouselCss css, int numberOfPages) {
      if (numberOfPages < 0) {
        throw new IllegalArgumentException();
      }
      this.css = css;
      main = new FlexPanel();
      initWidget(main);
      main.setOrientation(Orientation.HORIZONTAL);
      main.setJustification(Justification.CENTER);

      main.addStyleName(this.css.indicatorMain());

      FlexPanel container = new FlexPanel();
      container.addStyleName(this.css.indicatorContainer());
      container.setOrientation(Orientation.HORIZONTAL);
      main.add(container);

      indicators = new ArrayList<Carousel.CarouselIndicator>(numberOfPages);
      selectedIndex = 0;

      for (int i = 0; i < numberOfPages; i++) {
        CarouselIndicator indicator = new CarouselIndicator(css);
        indicators.add(indicator);
        container.add(indicator);
      }

      setSelectedIndex(selectedIndex);
    }

    public void setSelectedIndex(int index) {
      if (indicators.isEmpty()) {
        selectedIndex = -1;
        return;
      }
      if (selectedIndex != -1) {
        indicators.get(selectedIndex).setActive(false);
      }
      selectedIndex = index;
      if (selectedIndex != -1) {
        indicators.get(selectedIndex).setActive(true);

      }
    }
  }

  private static class CarouselIndicator extends TouchWidget {
    private final CarouselCss css;

    public CarouselIndicator(CarouselCss css) {
      this.css = css;
      setElement(Document.get().createDivElement());

      addStyleName(css.indicator());

    }

    public void setActive(boolean active) {
      if (active) {
        addStyleName(css.indicatorActive());
      } else {
        removeStyleName(css.indicatorActive());
      }
    }
  }

  @UiField
  public FlexPanel main;
  @UiField
  public ScrollPanel scrollPanel;
  @UiField
  public FlowPanel container;
  private CarouselIndicatorContainer carouselIndicatorContainer;
  private boolean isVisibleCarouselIndicator = true;

  private int currentPage;

  private Map<Widget, Widget> childToHolder;
  private HandlerRegistration refreshHandler;

  private static final CarouselImpl IMPL = GWT.create(CarouselImpl.class);

  private static final CarouselAppearance DEFAULT_APPEARANCE = GWT.create(CarouselAppearance.class);
  private final CarouselAppearance appearance;
  private boolean hasScollData;

  public Carousel() {
    this(DEFAULT_APPEARANCE);
  }

  public Carousel(CarouselAppearance appearance) {

    this.appearance = appearance;
    initWidget(this.appearance.carouselBinder().createAndBindUi(this));
    childToHolder = new HashMap<Widget, Widget>();

    scrollPanel.setSnap(true);
    scrollPanel.setSnapThreshold(50);
    scrollPanel.setMomentum(false);
    scrollPanel.setShowVerticalScrollBar(false);
    scrollPanel.setShowHorizontalScrollBar(false);
    scrollPanel.setScrollingEnabledY(true);
    scrollPanel.setAutoHandleResize(false);

    currentPage = 0;

    scrollPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {

      @Override
      public void onScrollEnd(ScrollEndEvent event) {
        int page = scrollPanel.getCurrentPageX();

        carouselIndicatorContainer.setSelectedIndex(page);
        currentPage = page;
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

        carouselIndicatorContainer.setSelectedIndex(currentPage);

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
    container.clear();
    childToHolder.clear();
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

        if (carouselIndicatorContainer != null) {
          carouselIndicatorContainer.removeFromParent();

        }

        int widgetCount = container.getWidgetCount();

        carouselIndicatorContainer = new CarouselIndicatorContainer(appearance.cssCarousel(), widgetCount);

        if(isVisibleCarouselIndicator){
          main.add(carouselIndicatorContainer);
        }

        if (currentPage >= widgetCount) {
          currentPage = widgetCount - 1;
        }

        carouselIndicatorContainer.setSelectedIndex(currentPage);

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
  }

  public int getSelectedPage() {
    return currentPage;
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

  /**
   * Set if carousel indicator is displayed.
   */
  public void setShowCarouselIndicator(boolean isVisibleCarouselIndicator) {
    if (!isVisibleCarouselIndicator && carouselIndicatorContainer != null) {
      carouselIndicatorContainer.removeFromParent();
    }
    this.isVisibleCarouselIndicator = isVisibleCarouselIndicator;
  }

  public ScrollPanel getScrollPanel() {
    return scrollPanel;
  }

  @UiFactory
  public CarouselAppearance getAppearance() {
	  return appearance;
  }
}
