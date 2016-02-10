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
package com.googlecode.mgwt.ui.client.util;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.googlecode.mgwt.image.client.ImageConverter;
import com.googlecode.mgwt.image.client.ImageConverter.ImageConverterCallback;
import com.googlecode.mgwt.ui.client.MGWT;

public class IconHandler {

  static {
    if (MGWT.getOsDetection().isAndroid4_3_orLower() || MGWT.getOsDetection().isIEEdge()) {
      ICON_HANDLER = new IconHandlerEmulatedImpl();
    } else {
      ICON_HANDLER = GWT.create(IconHandlerImpl.class);
    }
  }


  private interface IconHandlerImpl {
    public void setIcons(Element element, ImageResource icon, String color);
  }

  private static class IconHandlerNativeImpl implements IconHandlerImpl {

    protected static class Dimension {
      private int width;
      private int height;

      public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
      }
    }

    @Override
    public void setIcons(Element element, ImageResource icon, String color) {
      if (icon == null) {
        return;
      }

      Dimension dimensions = calculateDimensions(icon);
      element.getStyle().setProperty("WebkitMaskBoxImage",
          "url(" + icon.getSafeUri().asString() + ")");
      element.getStyle().setWidth(dimensions.width, Unit.PX);
      element.getStyle().setHeight(dimensions.height, Unit.PX);
      element.getStyle().setProperty("WebkitMaskSize",
          dimensions.width + "px, " + dimensions.height + "px");
      element.getStyle().setBackgroundColor(color);
    }

    protected Dimension calculateDimensions(ImageResource icon) {
      int iconWidth = icon.getWidth();
      int iconHeight = icon.getHeight();

      if (MGWT.getDeviceDensity().isHighDPI()) {
        iconWidth /= 1.5;
        iconHeight /= 1.5;
      } else if (MGWT.getDeviceDensity().isXHighDPI()) {
        iconWidth /= 2;
        iconHeight /= 2;
      }
      return new Dimension(iconWidth, iconHeight);
    }
  }

  private static class IconHandlerEmulatedImpl extends IconHandlerNativeImpl {

    private static final ImageConverter converter = new ImageConverter();

    @Override
    public void setIcons(final Element element, ImageResource icon, String color) {
      if (icon == null) {
        return;
      }

      converter.convert(icon, color, new ImageConverterCallback() {

        @Override
        public void onSuccess(ImageResource convertImageResource) {
          element.getStyle().setBackgroundColor("transparent");
          Dimension dimensions = calculateDimensions(convertImageResource);
          element.getStyle().setWidth(dimensions.width, Unit.PX);
          element.getStyle().setHeight(dimensions.height, Unit.PX);
          element.getStyle().setBackgroundImage(
              "url(" + convertImageResource.getSafeUri().asString() + ")");
          element.getStyle().setProperty("backgroundSize",
              dimensions.width + "px " + dimensions.height + "px");
        }
        
      });
    }
  }

  private static final IconHandlerImpl ICON_HANDLER;

  public static void setIcons(Element element, ImageResource icon, String color) {
    ICON_HANDLER.setIcons(element, icon, color);
  }
}
