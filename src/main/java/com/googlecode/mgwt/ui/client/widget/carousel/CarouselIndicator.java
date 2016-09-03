package com.googlecode.mgwt.ui.client.widget.carousel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.carousel.CarouselAppearance.CarouselCss;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

/**
 * The CarouselIndicator widget that holds multiple indicators horizontally where you can only select
 * one indicator at a time similar to radio buttons
 */
public class CarouselIndicator extends Composite implements HasSelectionHandlers<Integer> {

  private FlexPanel container;
  private final CarouselCss css;
  private int selectedIndex = 0;
  private List<Indicator> indicators = new ArrayList<Indicator>();
  private boolean enableTapSelection = true;
  
  /**
   * Add a SelectionHandler to inform you when a specific indicator has been tapped
   * if {@link #enableTapSelection} == true
   */
  @Override
  public HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }

  public CarouselIndicator(CarouselCss css) {
    this.css = css;
    container = new FlexPanel();
    container.setOrientation(Orientation.HORIZONTAL);
    container.addStyleName(this.css.indicatorContainer());
    initWidget(container);
  }
  
  public void setNumberOfIndicators(int numberOfIndicators) {
    container.clear();
    indicators.clear();
    for (int i = 0; i < numberOfIndicators; i++) {
      Indicator indicator = new Indicator(css, i);
      container.add(indicator);
      indicators.add(indicator);
    }
    selectedIndex = -1;
    setIndicatorSelected(0);
  }

  public void setIndicatorSelected(int index) {
    setIndicatorSelected(index, false);
  }

  private void setIndicatorSelected(int index, boolean fireEvent) {
    if ((index < 0) ||
        (index >= indicators.size()) ||
        (selectedIndex == index)) {
      return; // do nothing
    }
    selectedIndex = index;
    Iterator<Indicator> i = indicators.iterator();
    while (i.hasNext()) {
      Indicator indicator = i.next();
      indicator.setActive(indicator.getIndex() == index);
    }
    if (fireEvent) {
      SelectionEvent.fire(CarouselIndicator.this, index);
    }
  }

  public void setEnableTapSelection(boolean enableTapSelection)
  {
    this.enableTapSelection = enableTapSelection;
  }

  /**
   * A single indicator 
   */
  private class Indicator extends TouchWidget {
    private final CarouselCss css;
    private final int index;
  
    public Indicator(CarouselCss css, int index) {
      this.css = css;
      this.index = index;
      setElement(Document.get().createDivElement());
      addStyleName(css.indicator());
      addTapHandler();
    }
  
    public void setActive(boolean active) {
      if (active) {
        addStyleName(css.indicatorActive());
      } else {
        removeStyleName(css.indicatorActive());
      }
    }
    
    private com.google.gwt.event.shared.HandlerRegistration addTapHandler() {
      return addTapHandler(new TapHandler() {
        @Override
        public void onTap(TapEvent event) {
          if (CarouselIndicator.this.enableTapSelection) {
            // set indicator and fire selection event
            setIndicatorSelected(index, true);
          }
        }
      });
    }
    
    public int getIndex() {
      return this.index;
    }
  }

}