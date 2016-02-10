/*
 * Copyright 2012 Daniel Kurka
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
package com.googlecode.mgwt.dom.client.recognizer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.shared.HasHandlers;

import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchCopy;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;

/**
 * A recognizer that recognizes Tap events
 *
 * A Tap is the mobile equivalent of a click
 *
 * @author Daniel Kurka
 */
public class TapRecognizer implements TouchHandler {

  public static final int DEFAULT_DISTANCE = 15;

  private final int distance;

  private boolean touchCanceled;

  private boolean hasMoved;

  private TouchCopy touchStartCopy;

  private Element targetElement;

  private final HasHandlers source;

  private EventPropagator eventPropagator;

  private static EventPropagator DEFAULT_EVENT_PROPAGATOR;

  public TapRecognizer(HasHandlers source) {
    this(source, DEFAULT_DISTANCE);
  }

  public TapRecognizer(HasHandlers source, int distance) {
    if (source == null)
      throw new IllegalArgumentException("source can not be null");
    if (distance < 0)
      throw new IllegalArgumentException("distance has to be greater than zero");
    this.source = source;
    this.distance = distance;
  }

  @Override
  public void onTouchStart(TouchStartEvent event) {
    touchCanceled = false;
    hasMoved = false;
    if(event.getNativeEvent() != null){
      targetElement = event.getNativeEvent().getEventTarget().<Element>cast();
    }else {
      targetElement = null;
    }
    Touch touch = event.getTouches().get(0);
    touchStartCopy = TouchCopy.copy(touch);
  }

  @Override
  public void onTouchMove(TouchMoveEvent event) {
    if (touchStartCopy != null) {
      Touch touch = event.getTouches().get(0);
      if (Math.abs(touch.getPageX() - touchStartCopy.getPageX()) > distance || Math.abs(touch.getPageY() - touchStartCopy.getPageY()) > distance) {
        hasMoved = true;
        touchStartCopy = null;
      }
    }
  }

  @Override
  public void onTouchEnd(TouchEndEvent event) {
    if (!hasMoved && !touchCanceled && (touchStartCopy != null)) {
      TapEvent tapEvent = new TapEvent(source, targetElement, touchStartCopy);
      getEventPropagator().fireEvent(source, tapEvent);
    }
    touchStartCopy = null;
  }

  @Override
  public void onTouchCancel(TouchCancelEvent event) {
    touchCanceled = true;
  }

  public int getDistance() {
    return distance;
  }

  protected EventPropagator getEventPropagator() {
    if (eventPropagator == null) {
      if (DEFAULT_EVENT_PROPAGATOR == null) {
        DEFAULT_EVENT_PROPAGATOR = GWT.create(EventPropagator.class);
      }
      eventPropagator = DEFAULT_EVENT_PROPAGATOR;
    }
    return eventPropagator;
  }

  public Element getTargetElement() {
    return targetElement;
  }
}
