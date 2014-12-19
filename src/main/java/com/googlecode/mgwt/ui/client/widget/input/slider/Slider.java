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
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.util.CssUtil;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidgetImpl;

/**
 * The mgwt pointer widget.
 *
 * The pointer element is moved along the bar element to represent the value of the Slider
 */
public class Slider extends Widget implements HasValue<Integer>, LeafValueEditor<Integer> {

  private class SliderTouchHandler implements TouchHandler {

  	private boolean touchingPointer = false;
  	
    @Override
    public void onTouchStart(TouchStartEvent event) {
    	if (isDragOnly()) {
    		int x =  event.getTouches().get(0).getClientX();
    		int absL = pointer.getAbsoluteLeft();
    		int width = pointer.getClientWidth();
    		if ( x >= (absL - RESTRICT_PADDING) &&
    				 x <= (absL + width + RESTRICT_PADDING) ) {
    			touchingPointer = true;
    		}    		
    	}
    	else {
        setValueContrained(event.getTouches().get(0).getClientX());    		
    	}
      if (MGWT.getFormFactor().isDesktop()) {
        DOM.setCapture(getElement());
      }
      event.stopPropagation();
      event.preventDefault();
    }

    @Override
    public void onTouchMove(TouchMoveEvent event) {
    	if ( (isDragOnly()  && touchingPointer) || !isDragOnly() )
    	{
        setValueContrained(event.getTouches().get(0).getClientX());    		    		
    	}
      event.stopPropagation();
      event.preventDefault();
    }

    @Override
    public void onTouchEnd(TouchEndEvent event) {
      if (MGWT.getFormFactor().isDesktop()) {
        DOM.releaseCapture(getElement());
      }
      touchingPointer = false;
      event.stopPropagation();
      event.preventDefault();
    }

    @Override
    public void onTouchCancel(TouchCancelEvent event) {
      if (MGWT.getFormFactor().isDesktop()) {
        DOM.releaseCapture(getElement());
      }
      touchingPointer = false;
    }
  }

  private static final SliderAppearance DEFAULT_APPEARANCE = GWT.create(SliderAppearance.class);

  private static final TouchWidgetImpl TOUCH_WIDGET_IMPL = GWT.create(TouchWidgetImpl.class);

  private int value;
  private int max;
  private final SliderAppearance apperance;
	private HandlerRegistration touchHandler;
	private boolean readOnly;
	private boolean restrictDrag;
	private final static int RESTRICT_PADDING = 15;

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
    this.restrictDrag = false;
    this.readOnly = false;
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
        if (!isReadOnly())
        	setTouchHandler();
      }
    });
    if (!isAttached() && touchHandler != null) {
    	removeTouchHandler();
    }
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
  
	/**
	 * Should the slider be read only
	 *
	 * @param readonly true to be read only
	 */
	public void setReadOnly(boolean readonly) {
		if (readonly) {
			removeTouchHandler();
		}
		else {
			setTouchHandler();
		}
		this.readOnly = readonly;
	}

  /**
   * Is the slider currently read only?
   *
   * @return true if the slider is readonly
   */
	public boolean isReadOnly() {
		return readOnly;
	}
	
	/**
	 * Restrict the slider to change value on if dragged.
	 * 
	 * @param onlyDrag true if restricted to dragging.
	 */
	public void setDragOnly(boolean onlyDrag) {
		this.restrictDrag = onlyDrag;
	}
	
	/**
	 * Is the slider restricted to dragging only?
	 * 
	 * @return true if restricted.  If false, then touching
	 * anywhere in the slider track will move the slider handle
	 * to that location and trigger associated value change event.
	 * 
	 */
	public boolean isDragOnly() {
		return this.restrictDrag;
	}

	private void setTouchHandler() {
		if (touchHandler == null) {
			touchHandler = TOUCH_WIDGET_IMPL.addTouchHandler(this, new SliderTouchHandler());			
		}
	}
	
	private void removeTouchHandler() {
		if (touchHandler != null) {
			touchHandler.removeHandler();
			touchHandler = null;
		}
	}
	
}
