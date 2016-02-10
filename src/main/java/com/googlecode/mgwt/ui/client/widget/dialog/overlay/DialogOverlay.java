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
package com.googlecode.mgwt.ui.client.widget.dialog.overlay;

import java.util.Iterator;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.mouse.HandlerRegistrationCollection;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.HasTouchHandlers;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.ui.client.util.MGWTUtil;
import com.googlecode.mgwt.ui.client.widget.animation.Animation;
import com.googlecode.mgwt.ui.client.widget.animation.AnimationEndCallback;
import com.googlecode.mgwt.ui.client.widget.animation.AnimationWidget;
import com.googlecode.mgwt.ui.client.widget.dialog.Dialog;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Alignment;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Justification;
import com.googlecode.mgwt.ui.client.widget.panel.flex.RootFlexPanel;
import com.googlecode.mgwt.ui.client.widget.touch.TouchDelegate;

/**
 * Baseclass for creating dialogs that are animated
 *
 * @author Daniel Kurka
 */
public abstract class DialogOverlay implements HasWidgets, HasTouchHandlers, HasTapHandlers,
    Dialog {
  
  private class HideAnimationEndCallback implements AnimationEndCallback {
    public void onAnimationEnd() {
      HasWidgets panel = getPanelToOverlay();
      panel.remove(display.asWidget());
      // see issue 247 => http://code.google.com/p/mgwt/issues/detail?id=247
      MGWTUtil.forceFullRepaint();

      transitionState = TransitionState.NOTVISIBLE;
      if (requestShow) {
        requestShow = false;
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            show();
          }
        });
      }
    }
  }

  private class ShowAnimationEndCallback implements AnimationEndCallback {
    public void onAnimationEnd() {
      transitionState = TransitionState.VISIBLE;
      if (requestHide) {
        requestHide = false;
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            hide();
          }
        });
      }
    }
  }
  
  private class InternalTouchHandler implements TouchHandler {
    private final Element shadow;
    private Element startTarget;

    private InternalTouchHandler(Element shadow) {
      this.shadow = shadow;
    }

    @Override
    public void onTouchCancel(TouchCancelEvent event) {
      startTarget = null;
    }

    @Override
    public void onTouchEnd(TouchEndEvent event) {
      EventTarget eventTarget = event.getNativeEvent().getEventTarget();
      if (eventTarget != null) {
        // no textnode or element node
        if (Node.is(eventTarget)) {
          if (Element.is(eventTarget)) {
            Element endTarget = eventTarget.cast();

            if (endTarget == shadow && startTarget == shadow) {
              maybeHide();
            }

          }
        }
      }
      startTarget = null;
    }

    @Override
    public void onTouchMove(TouchMoveEvent event) {
    }

    @Override
    public void onTouchStart(TouchStartEvent event) {
      EventTarget eventTarget = event.getNativeEvent().getEventTarget();
      if (eventTarget != null) {
        // no textnode or element node
        if (Node.is(eventTarget)) {
          if (Element.is(eventTarget)) {
            startTarget = eventTarget.cast();
          }
        }
      }
    }
  }

  private final AnimationEndCallback SHOW_ANIMATION_CALLBACK = new ShowAnimationEndCallback();
  private final AnimationEndCallback HIDE_ANIMATION_CALLBACK = new HideAnimationEndCallback();

  public static final DialogOverlayAppearance DEFAULT_APPEARANCE = GWT
      .create(DialogOverlayAppearance.class);

  protected final DialogOverlayAppearance appearance;

  private RootFlexPanel container;
  private HasWidgets panelToOverlay;
  private AnimationWidget display;

  private boolean centerChildren;
  private boolean autoHide;
  private TouchDelegate touchDelegateForDisplay;
  private enum TransitionState {
    HIDING, SHOWING, VISIBLE, NOTVISIBLE;
  }
  private TransitionState transitionState = TransitionState.NOTVISIBLE;
  private boolean requestHide = false;
  private boolean requestShow = false;

  public DialogOverlay() {
    this(DEFAULT_APPEARANCE);
  }

  public DialogOverlay(DialogOverlayAppearance appearance) {
    this.appearance = appearance;
    display = new AnimationWidget();
    display.addStyleName(appearance.overlayCss().dialogOverlay());
    touchDelegateForDisplay = new TouchDelegate(display);
    display.addStyleName(appearance.overlayCss().animationContainerShadow());

    container = new RootFlexPanel();


    addTouchHandler(new InternalTouchHandler(container.getElement()));

  }

  @Override
  public void add(Widget w) {
    container.add(w);
  }

  @Override
  public HandlerRegistration addTouchStartHandler(TouchStartHandler handler) {
    return touchDelegateForDisplay.addTouchStartHandler(handler);
  }

  @Override
  public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler) {
    return touchDelegateForDisplay.addTouchMoveHandler(handler);
  }

  @Override
  public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler) {
    return touchDelegateForDisplay.addTouchCancelHandler(handler);
  }

  @Override
  public HandlerRegistration addTouchEndHandler(TouchEndHandler handler) {
    return touchDelegateForDisplay.addTouchEndHandler(handler);
  }

  @Override
  public HandlerRegistration addTouchHandler(TouchHandler handler) {
    HandlerRegistrationCollection handlerRegistrationCollection =
        new HandlerRegistrationCollection();
    handlerRegistrationCollection.addHandlerRegistration(addTouchCancelHandler(handler));
    handlerRegistrationCollection.addHandlerRegistration(addTouchStartHandler(handler));
    handlerRegistrationCollection.addHandlerRegistration(addTouchEndHandler(handler));
    handlerRegistrationCollection.addHandlerRegistration(addTouchMoveHandler(handler));
    return handlerRegistrationCollection;
  }

  public HandlerRegistration addTapHandler(TapHandler handler) {
    return touchDelegateForDisplay.addTapHandler(handler);
  }

  /**
   * Show the dialog centered
   */
  public void center() {
    centerChildren = true;
    show();
  }

  @Override
  public void clear() {
    container.clear();
  }

  /**
   * get the panel that the dialog overlays
   *
   * @return the panel that is overlayed by this dialog
   */
  public HasWidgets getPanelToOverlay() {
    if (panelToOverlay == null) {
      panelToOverlay = RootPanel.get();
    }
    return panelToOverlay;
  }

  /**
   * hide the dialog if it is visible
   */
  public void hide() {
    if (transitionState == TransitionState.SHOWING) {
      // not finished showing yet so request to hide once show complete
      requestHide = true;
    }
    else if (transitionState == TransitionState.VISIBLE) {
      requestHide = false;
      transitionState = TransitionState.HIDING;
      display.animate(getHideAnimation(), false, HIDE_ANIMATION_CALLBACK);
    }
    else if (transitionState == TransitionState.HIDING) {
      // not finished hiding yet so remove requestShow if set
      requestShow = false;
    }
  }

  /**
   * Should the dialog hide itself if there is a tap outside the dialog
   *
   * @return true if the dialog automatically hides, otherwise false
   */
  public boolean isHideOnBackgroundClick() {
    return autoHide;
  }

  @Override
  public Iterator<Widget> iterator() {
    return container.iterator();
  }

  @Override
  public boolean remove(Widget w) {
    return container.remove(w);
  }

  /**
   * Should the content of the dialog be centered
   *
   * @param centerContent true to center content
   */
  public void setCenterContent(boolean centerContent) {
    this.centerChildren = centerContent;
  }

  /**
   * Should the dialog hide itself if there is a tap outside the dialog
   *
   * @param hideOnBackgroundClick true if the dialog automatically hides, otherwise false
   */
  public void setHideOnBackgroundClick(boolean hideOnBackgroundClick) {
    this.autoHide = hideOnBackgroundClick;
  }

  /**
   * set the panel that should be overlayed by the dialog
   *
   * @param panel the area to be overlayed
   */
  public void setPanelToOverlay(HasWidgets panel) {
    this.panelToOverlay = panel;
  }

  /**
   * should the dialog add a shadow over the area that it covers
   *
   * @param shadow true to add a shadow
   */
  public void setShadow(boolean shadow) {
    if (shadow) {
      display.asWidget().addStyleName(appearance.overlayCss().animationContainerShadow());
    } else {
      display.asWidget().removeStyleName(appearance.overlayCss().animationContainerShadow());
    }
  }

  public void show() {
    if (transitionState == TransitionState.HIDING) {
      // not finished hiding yet so request to show once hide complete
      requestShow = true;
    }
    else if (transitionState == TransitionState.NOTVISIBLE) {
      requestShow = false;
      transitionState = TransitionState.SHOWING;
      // add overlay to DOM
      HasWidgets panel = getPanelToOverlay();
      panel.add(display.asWidget());

      if (centerChildren) {
        container.setAlignment(Alignment.CENTER);
        container.setJustification(Justification.CENTER);
      } else {
        container.clearAlignment();
        container.clearJustification();
      }

      display.setFirstWidget(container);

      // and animiate
      display.animate(getShowAnimation(), true, SHOW_ANIMATION_CALLBACK);
    }
    else if (transitionState == TransitionState.SHOWING) {
      // not finished showing yet so remove requestHide if set
      requestHide = false;
    }
  }

  protected abstract Animation getShowAnimation();

  protected abstract Animation getHideAnimation();

  protected void maybeHide() {
    if (autoHide) {
      hide();
    }
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
    display.fireEvent(event);
  }
}
