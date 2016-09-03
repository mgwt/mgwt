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
package com.googlecode.mgwt.ui.client.widget.dialog;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.dialog.overlay.PopinDialogOverlay;
import com.googlecode.mgwt.ui.client.widget.dialog.panel.DialogPanel;
import com.googlecode.mgwt.ui.client.widget.dialog.panel.DialogPanelAppearance;

/**
 * A simple alert dialog with an ok button.
 *
 * @author Daniel Kurka
 */
public class AlertDialog implements HasText, HasTitleText, HasTapHandlers, Dialog, HasHTML {

  private HTML textLabel;
  private PopinDialogOverlay popinDialog;
  private DialogPanel dialogPanel;

  /**
   * Construct an alert dialog
   */
  public AlertDialog() {
    this(DialogPanel.DEFAULT_APPEARANCE, null, null);
  }

  /**
   * Construct an alert dialog
   *
   * @param title - the title of the dialog
   * @param text - the text of the dialog
   */
  public AlertDialog(String title, String text) {
    this(DialogPanel.DEFAULT_APPEARANCE, title, text);
  }

  /**
   * Construct an alert dialog
   *
   * @param css - the css to use
   * @param title - the title of the dialog
   * @param text - the text of the dialog
   */
  public AlertDialog(DialogPanelAppearance appearance, String title, String text) {
    this(appearance, title, text, "Ok");
  }

  /**
   * Construct an alert dialog
   *
   * @param css - the css to use
   * @param title - the title of the dialog
   * @param text - the text of the dialog
   * @param okButtonText the text of the button of the dialog
   */
  public AlertDialog(DialogPanelAppearance appearance, String title, String text, String okButtonText) {
    popinDialog = new PopinDialogOverlay(appearance);
    dialogPanel = new DialogPanel(appearance);
    dialogPanel.showCancelButton(false);
    dialogPanel.showOkButton(true);

    textLabel = new HTML();
    dialogPanel.getContent().add(textLabel);
    popinDialog.add(dialogPanel);

    dialogPanel.getOkButton().addTapHandler(new TapHandler() {

      @Override
      public void onTap(TapEvent event) {
        popinDialog.hide();
      }
    });

    setText(text);
    setTitleText(title);
    dialogPanel.setOkButtonText(okButtonText);
  }

  @Override
  public String getText() {
    return textLabel.getText();
  }

  @Override
  public void setText(String text) {
    textLabel.setText(text);
  }

  @Override
  public void setTitleText(String text) {
    dialogPanel.getDialogTitle().setText(text);

  }

  public void setButtonText(String buttonText) {
	 dialogPanel.setOkButtonText(buttonText);
  }

  @Override
  public String getTitleText() {
    return dialogPanel.getDialogTitle().getText();
  }

  @Override
public void show() {
    popinDialog.center();
  }

  @Override
  public HandlerRegistration addTapHandler(TapHandler handler) {
    return dialogPanel.getOkButton().addTapHandler(handler);
  }

  @Override
  public String getHTML() {
    return textLabel.getHTML();
  }

  @Override
  public void setHTML(String html) {
    textLabel.setHTML(html);
  }

  @Override
  public void hide() {
    popinDialog.hide();
  }
}
