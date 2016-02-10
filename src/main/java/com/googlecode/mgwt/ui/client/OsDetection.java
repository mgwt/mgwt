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
package com.googlecode.mgwt.ui.client;

/**
 * This interface provides information on which platform mgwt is currently
 * running.
 *
 * The instance of OsDetection is provided by a gwt generator: This means that
 * e.g.:
 *
 * Consider a code fragment like this:
 *
 * <pre>
 * if (MGWT.getOsDetection().isAndroid) {
 * 	// do something
 * }
 * </pre>
 *
 * if <b>not</b> running on android (because of deferred binding), this code
 * will look like:
 *
 * <pre>
 * if (false) {
 * 	// do something
 * }
 * </pre>
 *
 * So the code would be removed from non android scripts by the GWT Compiler.
 *
 *
 * @author Daniel Kurka
 */
public interface OsDetection {
	/**
	 * Are we running on an android device
	 *
	 * @return true if running on android, otherwise false
	 */
	public boolean isAndroid();

	/**
	 * Are we running on an iphone or ipod touch
	 *
	 * @return true if running on iphone or ipod touch, otherwise false
	 */
	public boolean isIPhone();

	/**
	 * Are we running on an ipad
	 *
	 * @return true if running on ipad, otherwise false
	 */
	public boolean isIPad();

	/**
	 * Are we running on an ios device
	 *
	 * @return true if running on ios device, otherwise false
	 */
	public boolean isIOs();

	/**
	 * Are we running on the iphone retina display
	 *
	 * @return true if runnning on retina display
	 */
	public boolean isRetina();

	/**
	 * Are we running on an iPad with a retina display
	 *
	 * @return true if runnning on retina display
	 */
	public boolean isIPadRetina();

	/**
	 * Are we running in a desktop browser (chrome, safari or similiar)
	 *
	 * @return true if running on desktop browser
	 */
	public boolean isDesktop();

	/**
	 * Are we running on a tablet device
	 *
	 * @return true if we are on a tablet device (ipad, android tablet,
	 *         desktop), otherwise false
	 */
	public boolean isTablet();

	/**
	 * Are we running on an android tablet
	 *
	 * @return true if we are running on an android tablet, otherwise false
	 */
	public boolean isAndroidTablet();

	/**
	 * Are we running on an android phone
	 *
	 * @return true if we are running on an android phone, otherwise false
	 */
	public boolean isAndroidPhone();

	/**
	 * Are we running on a phone
	 *
	 * @return true if we are running on any kind of phone (iphone, ipod touch,
	 *         android phone, blackberry), otherwise false
	 */
	public boolean isPhone();

	/**
	 * Are we running on Windows Phone 8/8.1/10
	 * @return
	 */
	public boolean isWindowsPhone();
	
	/**
	 * Are we running on a blackberry device
	 *
	 * @return true if running on a blackberry device, otherwise false
	 */
	@Deprecated
	public boolean isBlackBerry();

	boolean isAndroid4_4_OrHigher();

	boolean isAndroid2x();

  boolean isIOS6();

  boolean isAndroid4_3_orLower();
  
  boolean isIEEdge();
}
