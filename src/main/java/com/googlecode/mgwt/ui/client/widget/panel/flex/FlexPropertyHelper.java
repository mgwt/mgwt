/*
 * Copyright 2014 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.widget.panel.flex;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;

public abstract class FlexPropertyHelper {

  private static final FlexPropertyHelper impl = GWT.create(FlexPropertyHelper.class);
  private static final FlexPropertyHelper std = GWT.create(FlexPropertyHelperStandard.class);
  
  public static enum Alignment {
    START, END, CENTER, STRETCH, BASELINE, NONE;
  }

  public static enum AlignmentSelf {
    START, END, CENTER, STRETCH, BASELINE, AUTO;
  }

  public static enum Justification {
    START, END, CENTER, SPACE_BETWEEN, SPACE_AROUND, NONE;
  }
  
  public static enum Orientation {
    HORIZONTAL, HORIZONTAL_REVERSE, VERTICAL, VERTICAL_REVERSE;
  }

  public static enum FlexWrap {
    NOWRAP, WRAP, WRAP_REVERSE;
  }

  public static void setElementAsFlexContainer(Element el)
  {
    setElementAsFlexContainer(el, null);
  }
  
  public static void setElementAsFlexContainer(Element el, Orientation orientation)
  {
    if (orientation == null)
    {
      orientation = Orientation.HORIZONTAL; // the default
    }
    impl._setElementAsFlexContainer(el, orientation);
    std._setElementAsFlexContainer(el, orientation);
  }

  public static void setFlex(Element el, double grow) {
    setFlex(el, grow, "0%");
  }

  public static void setFlex(Element el, double grow, double shrink) {
    setFlex(el, grow, shrink, "0%");
  }

  public static void setFlex(Element el, double grow, double shrink, String basis) {
    impl._setFlex(el, grow, shrink, basis);
    std._setFlex(el, grow, shrink, basis);
  }

  public static void setFlex(Element el, double grow, String basis) {
    impl._setFlex(el, grow, basis);
    std._setFlex(el, grow, basis);
  }

  public static void setFlexOrder(Element el, int order) {
    impl._setFlexOrder(el, order);
    std._setFlexOrder(el, order);
  }

  public static void setAlignment(Element el, Alignment alignment) {
    impl._setAlignmentProperty(el, alignment);
    std._setAlignmentProperty(el, alignment);
  }

  public static void setAlignmentSelf(Element el, AlignmentSelf alignmentSelf) {
    impl._setAlignmentSelfProperty(el, alignmentSelf);
    std._setAlignmentSelfProperty(el, alignmentSelf);
  }

  public static void setOrientation(Element el, Orientation orientation) {
    impl._setOrientationProperty(el, orientation);
    std._setOrientationProperty(el, orientation);
  }

  public static void setJustification(Element el, Justification justification) {
    impl._setJustificationProperty(el, justification);
    std._setJustificationProperty(el, justification);
  }

  public static void setFlexWrap(Element el, FlexWrap flexWrap) {
    impl._setFlexWrapProperty(el, flexWrap);
    std._setFlexWrapProperty(el, flexWrap);
  }

  public static void clearAlignment(Element el) {
    impl._setAlignmentProperty(el,Alignment.NONE);
    std._setAlignmentProperty(el,Alignment.NONE);
  }

  public static void clearJustification(Element el) {
    impl._setJustificationProperty(el,Justification.NONE);
    std._setJustificationProperty(el,Justification.NONE);
  }
  
  protected void setStyleProperty(Element el, String property, String value) {
    el.getStyle().setProperty(property, value);
  }

  protected abstract void _setElementAsFlexContainer(Element el, Orientation orientation);
  protected abstract void _setFlex(Element el, double grow, String basis);
  protected abstract void _setFlex(Element el, double grow, double shrink, String basis);
  protected abstract void _setFlexOrder(Element el, int order);
  protected abstract void _setAlignmentProperty(Element el, Alignment alignment);
  protected abstract void _setAlignmentSelfProperty(Element el, AlignmentSelf alignmentSelf);
  protected abstract void _setOrientationProperty(Element el, Orientation orientation);
  protected abstract void _setJustificationProperty(Element el, Justification justification);
  protected abstract void _setFlexWrapProperty(Element el, FlexWrap flexWrap);
}
