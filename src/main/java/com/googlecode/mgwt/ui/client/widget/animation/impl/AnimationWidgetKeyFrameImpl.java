/*
 * Copyright 2011 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.widget.animation.impl;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.googlecode.mgwt.dom.client.event.animation.AnimationEndEvent;
import com.googlecode.mgwt.dom.client.event.animation.AnimationEndHandler;
import com.googlecode.mgwt.ui.client.widget.animation.Animation;
import com.googlecode.mgwt.ui.client.widget.animation.AnimationEndCallback;

/**
 * Considered internal
 *
 * @author Daniel Kurka
 */
public class AnimationWidgetKeyFrameImpl implements AnimationWidgetImpl {
  static {
    Bundle.INSTANCE.css().ensureInjected();
  }

  interface Bundle extends ClientBundle {
    Bundle INSTANCE = GWT.create(Bundle.class);

    @Source("animation-display.gss")
    DisplayCss css();
  }

  interface DisplayCss extends CssResource {

    String display();

    String displayContainer();

  }

  protected class AnimationEndListener implements AnimationEndHandler {

    private Animation animation;

    public AnimationEndListener(Animation animation) {
      this.animation = animation;
    }

    @Override
    public void onAnimationEnd(AnimationEndEvent event) {
      AnimationWidgetKeyFrameImpl.this.onAnimationEnd(animation);
    }
  }

  private boolean animationRunning;

  private Animation currentAnimation;

  private FlowPanel main;

  private SimplePanel first;

  private SimplePanel second;

  private boolean lastDir;

  private boolean showFirst;

  private HandlerRegistration animationEnd;

  private AnimationEndListener listener;

  private AnimationEndCallback lastCallback;

  public AnimationWidgetKeyFrameImpl() {

    main = new FlowPanel() {
      protected void onDetach() {
        super.onDetach();

        if (animationRunning) {
          onAnimationEnd(currentAnimation);
        }

      };
    };

    main.setStylePrimaryName(Bundle.INSTANCE.css().display());

    first = new SimplePanel();
    first.addStyleName(Bundle.INSTANCE.css().displayContainer());

    second = new SimplePanel();
    second.addStyleName(Bundle.INSTANCE.css().displayContainer());

    main.add(first);

    main.add(second);

  }

  @Override
  public void animate(Animation animation, boolean currentIsFirst, AnimationEndCallback callback) {

    if (animationRunning) {
      throw new RuntimeException("Animation is already running");
    }

    this.currentAnimation = animation;

    lastCallback = callback;
    blurBeforeAnimation();

    showFirst = currentIsFirst;

    if (animation == null) {
      if (showFirst) {
        first.getElement().getStyle().setDisplay(Display.BLOCK);

      } else {
        second.getElement().getStyle().setDisplay(Display.BLOCK);
      }
      onAnimationEnd(animation);
      return;
    }
    animationRunning = true;

    if (animationEnd != null) {
      animationEnd.removeHandler();
      animationEnd = null;
    }

    animationEnd =
        main.addDomHandler(new AnimationEndListener(animation), AnimationEndEvent.getType());

    lastDir = animation.isReversed();
    // backwards
    if (animation.isReversed()) {
      first.addStyleName(animation.css().reverse());
      second.addStyleName(animation.css().reverse());

    }
    if (currentIsFirst) {
      first.addStyleName(animation.css().in());
      second.addStyleName(animation.css().out());

    } else {
      first.addStyleName(animation.css().out());
      second.addStyleName(animation.css().in());

    }

    first.getElement().getStyle().setDisplay(Display.BLOCK);
    second.getElement().getStyle().setDisplay(Display.BLOCK);
  }

  @Override
  public Widget asWidget() {
    return main;
  }

  @Override
  public void setFirstWidget(IsWidget w) {
    first.setWidget(w);
  }

  @Override
  public void setSecondWidget(IsWidget w) {
    second.setWidget(w);
  }

  private native void blurBeforeAnimation() /*-{
  	var node = $doc.querySelector(":focus");
  	if ((node != null) && !((node.nodeType === 1) && (node.nodeName === "BODY"))) {
  		if (typeof (node.blur) == "function") {
  			node.blur();
  		}
  	}
  }-*/;

  private void onAnimationEnd(Animation animation) {
    animationRunning = false;
    if (showFirst) {
      second.getElement().getStyle().setDisplay(Display.NONE);
      second.clear();
    } else {
      first.getElement().getStyle().setDisplay(Display.NONE);
      first.clear();
    }

    if(animation != null) {
      removeAllStyles(animation);
    }

    if (animationEnd != null) {
      animationEnd.removeHandler();
      animationEnd = null;
    }

    if (lastCallback != null) {
      lastCallback.onAnimationEnd();
      lastCallback = null;
    }
  }

  private void removeAllStyles(Animation animation) {
    first.removeStyleName(animation.css().in());
    first.removeStyleName(animation.css().out());
    first.removeStyleName(animation.css().reverse());

    second.removeStyleName(animation.css().in());
    second.removeStyleName(animation.css().out());
    second.removeStyleName(animation.css().reverse());
  }
}
