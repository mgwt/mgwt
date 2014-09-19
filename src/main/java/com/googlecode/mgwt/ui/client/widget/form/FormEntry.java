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
package com.googlecode.mgwt.ui.client.widget.form;

import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.base.MTextBoxBase;

import java.util.Iterator;

/**
 * A {@link FormEntry} entry renders a widget together with a describing label
 *
 * @author Daniel Kurka
 *
 */
public class FormEntry extends Composite implements HasWidgets, HasHTML {

  @UiField
  public HTML label;
  @UiField
  public SimplePanel widgetContainer;

  private FormAppearance appearance;
  private boolean valid = true;

  /**
   * Construct an empty {@link FormEntry}
   */
	public FormEntry() {
		this("", null);

	}

  /**
   * Construct a {@link FormEntry} with a label and a widget
   *
   * @param fieldName the text of the label
   * @param w the widget
   */
	public FormEntry(String fieldName, Widget w) {
		this(Form.DEFAULT_APPEARANCE, fieldName, w);
	}

  /**
   * Construct a {@link FormEntry} with a label, a widget and a given appearance
   *
   * @param fieldName the text of the label
   * @param w the widget
   * @param appearance the appearance to use
   */
	public FormEntry(FormAppearance appearance, String fieldName, Widget w) {
	  this.appearance = appearance;
		initWidget(this.appearance.uiBinderEntry().createAndBindUi(this));
		setWidget(fieldName, w);
	}

  /**
   * set the label and the widget
   *
   * @param fieldName the text of the label
   * @param w the widget
   */
	public void setWidget(String fieldName, Widget w) {
		label.setHTML(fieldName);
		if (w instanceof MTextBoxBase) {

		}
		widgetContainer.setWidget(w);
	}

	@Override
	public String getText() {
		return label.getText();
	}

	@Override
	public void setText(String text) {
	  label.setText(text);
	}

	@Override
	public String getHTML() {
		return label.getHTML();
	}

	@Override
	public void setHTML(String html) {
	  label.setHTML(html);
	}

	// only for uibinder
	public void add(Widget w) {
		widgetContainer.setWidget(w);
	}

	@Override
	public void clear() {
		widgetContainer.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return widgetContainer.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return widgetContainer.remove(w);
	}

	public void setValid(boolean valid) {
	  this.valid = valid;
	  if(valid) {
	    removeStyleName(this.appearance.css().formEntryInvalid());
	  } else {
	    addStyleName(this.appearance.css().formEntryInvalid());
	  }
	}
	
	public void removeFieldName(){
		label.removeFromParent();
	}

  public boolean isValid() {
    return valid;
  }

  @UiFactory
  public FormAppearance getAppearance() {
	  return appearance;
  }
}
