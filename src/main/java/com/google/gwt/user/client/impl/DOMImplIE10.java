/*
 * Copyright 2013 Google Inc.
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
package com.google.gwt.user.client.impl;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * IE10 implementation of {@link com.google.gwt.user.client.impl.DOMImplStandard}.
 */
public class DOMImplIE10 extends DOMImplIE9 {
  
  static {
    DOMImplStandard.addCaptureEventDispatchers(getCaptureEventDispatchers());
    DOMImplStandard.addBitlessEventDispatchers(getBitlessEventDispatchers());
    capturePointerEvents();
  }
  
  /**
   * Lets have the same behaviour as IOS where the target element continues to receive Pointer events
   * even when the pointer has moved off the element up until MSPointerUp has occurred.
   * 
   *  Do not do pointer capture on input or textarea elements, all sorts of problems arise if you do!
   *  For example if you type into a password field you cannot set the cursor to the end of
   *  the text when re-entering it and so you cannot edit your password
   */
  private native static void capturePointerEvents() /*-{
    if ($wnd.navigator.pointerEnabled) {
      $wnd.addEventListener('pointerdown',
        $entry(function(evt) {
          if ((evt.target.tagName !== 'INPUT') && (evt.target.tagName !== 'TEXTAREA'))  {
            evt.target.setPointerCapture(evt.pointerId);
          }
        }), true);
    }
    else {
      $wnd.addEventListener('MSPointerDown',
        $entry(function(evt) {
          if ((evt.target.tagName !== 'INPUT') && (evt.target.tagName !== 'TEXTAREA'))  {
            evt.target.msSetPointerCapture(evt.pointerId);
          }
        }), true);
    }
  }-*/;

  
  public static native JavaScriptObject getCaptureEventDispatchers() /*-{
    if ($wnd.navigator.pointerEnabled) {
      return {
        pointerdown:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
        pointerup:     @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
        pointermove:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
        pointercancel: @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*)
      };
    }
    else {
      return {
        MSPointerDown:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
        MSPointerUp:     @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
        MSPointerMove:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
        MSPointerCancel: @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*)
      };
    }
  }-*/;

  public static native JavaScriptObject getBitlessEventDispatchers() /*-{
    if ($wnd.navigator.pointerEnabled) {
      return {
        pointerdown:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
        pointerup:     @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
        pointermove:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
        pointercancel: @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*)
      };
    }
    else {
      return {
        MSPointerDown:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
        MSPointerUp:     @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
        MSPointerMove:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
        MSPointerCancel: @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*)
      };
    }
  }-*/;

}
