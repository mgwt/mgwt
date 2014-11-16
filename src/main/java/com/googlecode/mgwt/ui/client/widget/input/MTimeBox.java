/*
 * Copyright 2011 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.widget.input;


import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.googlecode.mgwt.ui.client.util.Time;
import com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * An input element that handles time
 *
 * @author Guillaume Rebesche
 */
public class MTimeBox extends MValueBoxBase<Time> {

	private static final DateTimeFormat TIME_FORMAT = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_SHORT);

	/**
	 * Using ValueBoxBase as a base class for our input element.
	 *
	 * @author Guillaume Rebesche
	 */
	private static class DateValueBoxBase extends ValueBoxBase<Time> implements HasSource {

		private Object source;

		protected DateValueBoxBase(DateRenderer dateRenderer, DateParser dateParser) {
			super(DOM.createInputText(), dateRenderer, dateParser);
		}

		@Override
		protected HandlerManager createHandlerManager() {
			return new HandlerManager(source);
		}

		public void setSource(Object source) {
			this.source = source;
		}
	}

	public static class DateRenderer implements Renderer<Time> {

		@Override
		public String render(Time object) {
			if (object == null) {
				return "";
			}
			Date date = new Date();
			date.setHours(object.getHours());
			date.setMinutes(object.getMinutes());
			return TIME_FORMAT.format(date);
		}

		@Override
		public void render(Time object, Appendable appendable) throws IOException {
			if (object != null) {
				Date date = new Date();
				date.setHours(object.getHours());
				date.setMinutes(object.getMinutes());
				appendable.append(TIME_FORMAT.format(date));
			}
		}
	}

	public static class DateParser implements Parser<Time> {

		@Override
		public Time parse(CharSequence text) throws ParseException {
			String string = text.toString();
			try {
				Date parsed = TIME_FORMAT.parse(string);
				return new Time(parsed.getHours(), parsed.getMinutes());
			} catch (Exception e) {
				return null;
			}
		}
	}

	public MTimeBox() {
		this(InputAppearanceHolder.DEFAULT_APPEARANCE);
	}

	public MTimeBox(InputAppearance appearance) {
		super(appearance, new DateValueBoxBase(new DateRenderer(), new DateParser()));
		addStyleName(appearance.css().textBox());
		impl.setType(box.getElement(), "time");
	}
}
