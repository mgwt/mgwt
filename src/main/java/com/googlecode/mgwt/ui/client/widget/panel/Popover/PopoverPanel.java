/*
 * Copyright 2014 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.widget.panel.Popover;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.HasTouchHandlers;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.dom.client.recognizer.longtap.HasLongTapHandlers;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapHandler;
import com.googlecode.mgwt.dom.client.recognizer.pinch.HasPinchHandlers;
import com.googlecode.mgwt.dom.client.recognizer.pinch.PinchHandler;
import com.googlecode.mgwt.dom.client.recognizer.swipe.HasSwipeHandlers;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeEndHandler;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeMoveHandler;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeStartHandler;
import com.googlecode.mgwt.ui.client.widget.touch.TouchPanel;

import java.util.Iterator;

public class PopoverPanel extends Composite implements HasTouchHandlers, HasTapHandlers, HasPinchHandlers, HasSwipeHandlers, HasLongTapHandlers, InsertPanel.ForIsWidget, HasWidgets
{

	private static final PopoverPanelAppearance DEFAULT_APPERANCE = GWT.create(PopoverPanelAppearance.class);

	private PopoverPanelAppearance apperance;

	@UiField
	public TouchPanel container;

	public PopoverPanel(Widget widget)
	{
		this(DEFAULT_APPERANCE, widget);
	}

	public PopoverPanel(PopoverPanelAppearance apperance, Widget widget)
	{
		this.apperance = apperance;
		initWidget(this.apperance.uiBinder().createAndBindUi(this));

		int Xposition = 0;
		int Yposition = widget.getElement().getAbsoluteTop() + widget.getElement().getClientHeight();

		this.getElement().getStyle().setTop(Yposition, Unit.PX);
		this.getElement().getStyle().setLeft(Xposition, Unit.PX);

	}

	public void setRound(boolean round)
	{
		if (round)
		{
			addStyleName(this.apperance.css().PopoverPanel());
		} else
		{
			removeStyleName(this.apperance.css().PopoverPanelRound());
		}
	}

	
	public void show()
	{
		RootPanel.get().add(this);
	}

	public void hide()
	{
		RootPanel.get().remove(this);
	}

	@Override
	public HandlerRegistration addLongTapHandler(LongTapHandler handler)
	{
		return container.addLongTapHandler(handler);
	}

	@Override
	public HandlerRegistration addSwipeStartHandler(SwipeStartHandler handler)
	{
		return container.addSwipeStartHandler(handler);
	}

	@Override
	public HandlerRegistration addSwipeMoveHandler(SwipeMoveHandler handler)
	{
		return container.addSwipeMoveHandler(handler);
	}

	@Override
	public HandlerRegistration addSwipeEndHandler(SwipeEndHandler handler)
	{
		return container.addSwipeEndHandler(handler);
	}

	@Override
	public HandlerRegistration addPinchHandler(PinchHandler handler)
	{
		return container.addPinchHandler(handler);
	}

	@Override
	public HandlerRegistration addTapHandler(TapHandler handler)
	{
		return container.addTapHandler(handler);
	}

	@Override
	public HandlerRegistration addTouchStartHandler(TouchStartHandler handler)
	{
		return container.addTouchStartHandler(handler);
	}

	@Override
	public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler)
	{
		return container.addTouchMoveHandler(handler);
	}

	@Override
	public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler)
	{
		return container.addTouchCancelHandler(handler);
	}

	@Override
	public HandlerRegistration addTouchEndHandler(TouchEndHandler handler)
	{
		return container.addTouchEndHandler(handler);
	}

	@Override
	public HandlerRegistration addTouchHandler(TouchHandler handler)
	{
		return container.addTouchHandler(handler);
	}

	@Override
	public void add(Widget w)
	{
		container.add(w);
	}

	@Override
	public void insert(Widget w, int beforeIndex)
	{
		container.insert(w, beforeIndex);
	}

	@Override
	public Widget getWidget(int index)
	{
		return container.getWidget(index);
	}

	@Override
	public int getWidgetCount()
	{
		return container.getWidgetCount();
	}

	@Override
	public int getWidgetIndex(Widget child)
	{
		return container.getWidgetIndex(child);
	}

	@Override
	public boolean remove(int index)
	{
		return container.remove(index);
	}

	@Override
	public int getWidgetIndex(IsWidget child)
	{
		return container.getWidgetIndex(child);
	}

	@Override
	public void add(IsWidget w)
	{
		container.add(w);
	}

	@Override
	public void insert(IsWidget w, int beforeIndex)
	{
		container.insert(w, beforeIndex);
	}

	@UiFactory
	public PopoverPanelAppearance getApperance()
	{
		return apperance;
	}

	@Override
	public void clear()
	{
		container.clear();
	}

	@Override
	public Iterator<Widget> iterator()
	{
		return container.iterator();
	}

	@Override
	public boolean remove(Widget w)
	{
		return container.remove(w);
	}
}
