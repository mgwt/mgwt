/*
 * Copyright 2013 Google Inc.
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
package com.googlecode.mgwt.dom.client.event.pointer;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * Abstract class representing MsPointer events.
 *
 * @param <H> handler type
 *
 */
public abstract class MsPointerEvent<H extends EventHandler> extends MouseEvent<H> {

  private native static boolean isIE10PointerEventModel() /*-{
    return (!$wnd.navigator.pointerEnabled);
  }-*/;

  public static final String MSPOINTERDOWN;
  public static final String MSPOINTERMOVE;
  public static final String MSPOINTEROUT;
  public static final String MSPOINTEROVER;
  public static final String MSPOINTERUP;
  public static final String MSPOINTERCANCEL;

  static {
    if (isIE10PointerEventModel()) {
      MSPOINTERDOWN = "MSPointerDown";
      MSPOINTERMOVE = "MSPointerMove";
      MSPOINTEROUT = "MSPointerOut";
      MSPOINTEROVER = "MSPointerOver";
      MSPOINTERUP = "MSPointerUp";
      MSPOINTERCANCEL = "MSPointerCancel";
    }
    else {
      MSPOINTERDOWN = "pointerdown";
      MSPOINTERMOVE = "pointermove";
      MSPOINTEROUT = "pointerout";
      MSPOINTEROVER = "pointerover";
      MSPOINTERUP = "pointerup";
      MSPOINTERCANCEL = "pointercancel";
    }
  }
  
  public final native int getPointerId() /*-{
    var e = this.@com.google.gwt.event.dom.client.DomEvent::nativeEvent;
    return e.pointerId;
  }-*/;

}
