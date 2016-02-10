/*
 * Copyright 2010 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.widget.input.slider;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.ui.client.TouchSupport;
import com.googlecode.mgwt.ui.client.util.CssUtil;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidgetImpl;

/**
 * The mgwt pointer widget.
 *
 * The pointer element is moved along the bar element to represent the value of the Slider
 */
public class Slider extends Widget implements HasValue<Integer>, LeafValueEditor<Integer> {

  private boolean active = false;
  
  private class SliderTouchHandler implements TouchHandler {

    @Override
    public void onTouchStart(TouchStartEvent event) {
      setValueContrained(event.getTouches().get(0).getClientX());
      if (TouchSupport.isTouchEventsEmulatedUsingMouseEvents()) {
        DOM.setCapture(getElement());
      }
      active = true;
      event.stopPropagation();
      event.preventDefault();
    } 

    @Override
    public void onTouchMove(TouchMoveEvent event) {
      if (active) {
        setValueContrained(event.getTouches().get(0).getClientX());
        event.stopPropagation();
        event.preventDefault();
      }
    }

    @Override
    public void onTouchEnd(TouchEndEvent event) {
      if (TouchSupport.isTouchEventsEmulatedUsingMouseEvents()) {
        DOM.releaseCapture(getElement());
      }
      event.stopPropagation();
      event.preventDefault();
      active = false;
    }

    @Override
    public void onTouchCancel(TouchCancelEvent event) {
      if (TouchSupport.isTouchEventsEmulatedUsingMouseEvents()) {
        DOM.releaseCapture(getElement());
      }
      active = false;
    }
  }

  private static final SliderAppearance DEFAULT_APPEARANCE = GWT.create(SliderAppearance.class);

  private static final TouchWidgetImpl TOUCH_WIDGET_IMPL = GWT.create(TouchWidgetImpl.class);

  private int value;
  private int max;
  private final SliderAppearance apperance;

  @UiField
  public Element pointer;
  @UiField
  public Element bar;

  public Slider() {
    this(DEFAULT_APPEARANCE);
  }

  public Slider(SliderAppearance apperance) {
    this.apperance = apperance;
    setElement(this.apperance.uiBinder().createAndBindUi(this));
    TOUCH_WIDGET_IMPL.addTouchHandler(this, new SliderTouchHandler());
    max = 100;
    value = 0;
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  /**
   * Set the maximum of the pointer
   *
   * @param max the maximum to use
   */
  public void setMax(int max) {
    if (max <= 0) {
      throw new IllegalArgumentException("max > 0");
    }
    this.max = max;
  }

  /**
   * get the maximum of the pointer
   *
   * @return the maximum of the pointer
   */
  public int getMax() {
    return max;
  }

  @Override
  public Integer getValue() {
    return value;
  }

  @Override
  public void setValue(Integer value) {
    setValue(value, true);
  }

  @Override
  protected void onAttach() {
    super.onAttach();
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        setSliderPos(value);
      }
    });
  }

  @Override
  public void setValue(Integer value, boolean fireEvents) {
    setValue(value, fireEvents, true);
  }

  @UiFactory
  public SliderAppearance getApperance() {
	  return apperance;
  }

  protected void setValue(Integer value, boolean fireEvents, boolean updateSlider) {
    if (value == null) {
      throw new IllegalArgumentException("value can not be null");
    }

    if (value < 0) {
      throw new IllegalArgumentException("value >= 0");
    }

    if (value >= max) {
      throw new IllegalArgumentException("value >= max");
    }

    int oldValue = this.value;
    this.value = value;
    if (updateSlider) {
      setSliderPos(value);
    }

    if (fireEvents) {
      ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
    }
  }

  private void setSliderPos(int value) {

    if (!isAttached()) {
      return;
    }

    int width = bar.getOffsetWidth();
    int sliderPos = value * width / max;
    setPos(sliderPos);

  }

  private void setValueContrained(int x) {
    x = x - Slider.this.getAbsoluteLeft();
    int width = bar.getOffsetWidth();

    if (x < 0) {
      x = 0;
    }

    if (x > (width - 1)) {
      x = width - 1;
    }

    // scale it to max
    int componentValue = x * max / width;
    setValue(componentValue, true, false);

    setPos(x);
  }

  private void setPos(int x) {
    CssUtil.translate(pointer, x, 0);
  }
}
