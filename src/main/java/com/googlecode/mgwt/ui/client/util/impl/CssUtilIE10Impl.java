package com.googlecode.mgwt.ui.client.util.impl;

import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.dom.client.Element;

/**
 * No idea why but there is a slight difference between Windows Phone 8.1 and
 * Windows Phone 8.1 Update. When using translate3d with z=0 getComputedStyle returns
 * a 3D matrix on Windows Phone 8.1 but for Windows Phone 8.1 Update it returns
 * a 2D matrix. So we check which matrix is returned before using the relevant elements
 */
public class CssUtilIE10Impl implements CssUtilImpl {

  public CssUtilIE10Impl() {
  }

  @Override
  public void translate(Element el, int x, int y) {
    String cssText = "translate3d(" + x + "px," + y + "px,0px)";
    _translate(el, cssText);
  }
  
  @Override
  public native void setDelay(Element el, int milliseconds) /*-{
    el.style.transitionDelay = milliseconds + "ms";
  }-*/;

  @Override
  public native void setOpacity(Element el, double opacity) /*-{
    el.style.opacity = opacity;
  }-*/;

  @Override
  public native void setDuration(Element el, int time) /*-{
    el.style.transitionDuration = time + "ms";
  }-*/;

  private native void _translate(Element el, String css) /*-{
    el.style.transform = css;
  }-*/;

  @Override
  public void rotate(Element el, int degree) {
    el.getStyle().setProperty("transform", "rotateZ(" + degree + "deg)");
  }

  @Override
  public boolean hasTransform() {
    return true;
  }

  @Override
  public boolean hasTransistionEndEvent() {
    return true;
  }

  @Override
  public boolean has3d() {
    return true;
  }

  @Override
  public String getTransformProperty() {
    return "transform";
  }

  @Override
  public int[] getPositionFromTransForm(Element element) {
    JsArrayInteger array = getPositionFromTransform(element);
    return new int[] {array.get(0), array.get(1)};
  }

  private native JsArrayInteger getPositionFromTransform(Element el) /*-{
    var matrix = getComputedStyle(el, null)['transform'].replace(
        /[^0-9-.,]/g, '').split(',');
    if (matrix.length === 6) {
      var x = matrix[4] * 1;
      var y = matrix[5] * 1;
      return [ x, y ];
    }
    else {
      var x = matrix[12] * 1;
      var y = matrix[13] * 1;
      return [ x, y ];
    }
  }-*/;

  @Override
  public native int getTopPositionFromCssPosition(Element element) /*-{
    return getComputedStyle(element, null).top.replace(/[^0-9-]/g, '') * 1;
  }-*/;

  @Override
  public native int getLeftPositionFromCssPosition(Element element) /*-{
    return getComputedStyle(element, null).left.replace(/[^0-9-]/g, '') * 1;
  }-*/;

  @Override
  public native void resetTransform(Element el) /*-{
    el.style.transform = "";
  }-*/;

  @Override
  public native void setTransistionProperty(Element element, String string) /*-{
    element.transitionProperty = string;
  }-*/;

  @Override
  public native void setTransFormOrigin(Element el, int x, int y) /*-{
    el.transformOrigin = x + " " + y;
  }-*/;

  @Override
  public native void setTransistionTimingFunction(Element element, String string) /*-{
    el.transitionTimingFunction = string;
  }-*/;

  @Override
  public void setTranslateAndZoom(Element el, int x, int y, double scale) {
    String cssText = "translate3d(" + x + "px, " + y + "px,0px) scale(" + scale + ")";
    el.getStyle().setProperty("transform", cssText);
  }

  @Override
  public void translatePercent(Element el, double x, double y) {
    String cssText = "translate3d(" + x + "%, " + y + "%,0)";
    _translate(el, cssText);
  }

}
