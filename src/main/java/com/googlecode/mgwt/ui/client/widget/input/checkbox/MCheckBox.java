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
package com.googlecode.mgwt.ui.client.widget.input.checkbox;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasValue;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.ui.client.TouchSupport;
import com.googlecode.mgwt.ui.client.util.CssUtil;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

/**
 * A checkbox widget.
 */
public class MCheckBox extends TouchWidget implements HasValue<Boolean>, IsEditor<LeafValueEditor<Boolean>> {

  public static final MCheckBoxAppearance DEFAULT_APPEARANCE = GWT.create(MCheckBoxAppearance.class);

	private final class TouchHandlerImplementation implements TouchHandler {
		private int x_start;
		private int x_min;
		private int x_max;
	  private int offset;
		private boolean moved;
		private int now_x;
		private boolean active = false;
		
		@Override
		public void onTouchCancel(TouchCancelEvent event) {
      active = false;
			if (isReadOnly()) {
				return;
			}
			event.stopPropagation();
			event.preventDefault();
      if (TouchSupport.isTouchEventsEmulatedUsingMouseEvents()) {
				DOM.releaseCapture(getElement());
			}
			setValue(getValue());
		}

		@Override
		public void onTouchEnd(TouchEndEvent event) {
      active = false;
			if (isReadOnly()) {
				return;
			}

			event.stopPropagation();
			event.preventDefault();
      if (TouchSupport.isTouchEventsEmulatedUsingMouseEvents()) {
				DOM.releaseCapture(getElement());
			}

			if (!moved) {
				setValue(!getValue());
			} else {
				setValue(now_x >= x_start);
			}
		}

		@Override
		public void onTouchMove(TouchMoveEvent event) {
			if (!active || isReadOnly()) {
				return;
			}
			event.stopPropagation();
			event.preventDefault();
			Touch touch = event.getTouches().get(0);
			now_x = touch.getClientX();
			if (!moved) {
				if (Math.abs(now_x - x_start) < appearance.css().DRAG_DEADZONE()) {
					return;
				}
			}
			moved = true;

			int translate_x = now_x - x_start;

			if (translate_x < x_min) {
				return;
			}

			if (translate_x > x_max) {
				return;
			}

			translate(translate_x + offset);
		}

		@Override
		public void onTouchStart(TouchStartEvent event) {
			if (isReadOnly()) {
				return;
			}
			active = true;
			event.stopPropagation();
			event.preventDefault();
      if (TouchSupport.isTouchEventsEmulatedUsingMouseEvents()) {
				DOM.setCapture(getElement());
			}

			Touch touch = event.getTouches().get(0);
			x_start = touch.getClientX();
			moved = false;
			if (value) {
				x_min = appearance.css().CONTAINER_MIN_ON();
				x_max = appearance.css().CONTAINER_MAX_ON();
				offset = appearance.css().CONTAINER_OFFSET_ON();
			} else {
			  x_min = appearance.css().CONTAINER_MIN_OFF();
        x_max = appearance.css().CONTAINER_MAX_OFF();
        offset = appearance.css().CONTAINER_OFFSET_OFF();
			}
		}
	}

	private boolean value;
	@UiField
	public Element on;
	@UiField
	public Element middle;
	@UiField
	public Element off;

	private LeafValueEditor<Boolean> editor;
	private boolean readonly;
  private MCheckBoxAppearance appearance;

	public MCheckBox() {
		this(DEFAULT_APPEARANCE);
	}

	public MCheckBox(MCheckBoxAppearance appearance) {
		this.appearance = appearance;
		setElement(appearance.uiBinder().createAndBindUi(this));
		addTouchHandler(new TouchHandlerImplementation());
		setValue(true, false);
	}

	@Override
	public com.google.gwt.event.shared.HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public void setValue(Boolean value) {
		setValue(value, true);
	}

	@Override
	public void setValue(Boolean value, boolean fireEvents) {

		if (value == null) {
			throw new IllegalArgumentException("value can not be null");
		}
		boolean oldValue = this.value;
		this.value = value;

		clearStyles();
		if (value) {
			addStyleName(appearance.css().checked());
			removeStyleName(appearance.css().notChecked());
		} else {
			addStyleName(appearance.css().notChecked());
			removeStyleName(appearance.css().checked());
		}

		if (fireEvents){
		  ValueChangeEvent.fireIfNotEqual(this, oldValue, this.value);
		}
	}

	/**
	 * Should this check box be rendered as important
	 *
	 * @param important true to render the check box as important
	 */
	public void setImportant(boolean important) {
		if (important) {
			addStyleName(appearance.css().important());
		} else {
			removeStyleName(appearance.css().important());
		}
	}

	@Override
	public LeafValueEditor<Boolean> asEditor() {
		if (editor == null) {
			editor = TakesValueEditor.of(this);
		}
		return editor;
	}

	/**
	 * Should the checkbox be readonly
	 *
	 * @param readonly true to be read only
	 */
	public void setReadOnly(boolean readonly) {
		this.readonly = readonly;
	}

  /**
   * Is the checkbox currently read only?
   *
   * @return true if the checkbox is readonly
   */
	public boolean isReadOnly() {
		return readonly;
	}

	@UiFactory
	public MCheckBoxAppearance getAppearance() {
		return appearance;
	}

	private void translate(int x) {
		CssUtil.translate(on, x, 0);
		CssUtil.translate(middle, x, 0);
		CssUtil.translate(off, x, 0);
	}

	private void clearStyles() {
		middle.setAttribute("style", "");
		on.setAttribute("style", "");
		off.setAttribute("style", "");
	}
}
