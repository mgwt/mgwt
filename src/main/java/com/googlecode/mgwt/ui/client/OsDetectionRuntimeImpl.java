package com.googlecode.mgwt.ui.client;

public class OsDetectionRuntimeImpl implements OsDetection {

  @Override
  public boolean isAndroid() {
    return isAndroidPhone() || isAndroidTablet();
  }

  @Override
  public boolean isIPhone() {
    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && !isWindowsPhone() && userAgent.contains("iphone") && getDevicePixelRatio() < 2) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isIPad() {
    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && userAgent.contains("ipad") && getDevicePixelRatio() < 2) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isIOs() {
    return isIPad() || isIPadRetina() || isIPhone() || isRetina();
  }

  @Override
  public boolean isRetina() {
    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && !isWindowsPhone() && userAgent.contains("iphone") && getDevicePixelRatio() >= 2) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isIPadRetina() {
    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && userAgent.contains("ipad") && getDevicePixelRatio() >= 2) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isDesktop() {
    return !isIOs() && !isAndroid() && !isWindowsPhone();
  }

  @Override
  public boolean isTablet() {
    return isIPad() || isIPadRetina() || isAndroidTablet();
  }

  @Override
  public boolean isAndroidTablet() {
    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && !isWindowsPhone() && userAgent.contains("android") && !userAgent.contains("mobile")) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isAndroidPhone() {
    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && !isWindowsPhone() && userAgent.contains("android") && userAgent.contains("mobile")) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isPhone() {
    return isIPhone() || isRetina() || isAndroidPhone() || isWindowsPhone();
  }

  @Override
  public boolean isWindowsPhone()
  {
    String userAgent = getUserAgent();
    if (userAgent.contains("windows phone 8") || userAgent.contains("windows phone 10")) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isBlackBerry() {
    return false;
  }

  @Override
  public boolean isAndroid4_4_OrHigher() {
    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && userAgent.contains("android") && userAgent.contains("chrome")) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isAndroid2x() {
    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && userAgent.contains("android 2.")) {
      return true;
    }
    return false;
  }

  native String getUserAgent() /*-{
    return $wnd.navigator.userAgent.toLowerCase();
  }-*/;

  native double getDevicePixelRatio() /*-{
    if (!$wnd.devicePixelRatio) {
      if ('deviceXDPI' in $wnd.screen) {
        $wnd.devicePixelRatio = $wnd.screen.deviceXDPI / $wnd.screen.logicalXDPI;
      }
    }
    return $wnd.devicePixelRatio || 1;
  }-*/;

  @Override
  public boolean isIOS6() {
    if(!isIOs()){
      return false;
    }

    String userAgent = getUserAgent();
    if (userAgent.contains("os 6_")) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isAndroid4_3_orLower() {
    if(isAndroid4_4_OrHigher())
    {
      return false;
    }

    String userAgent = getUserAgent();
    if (!isIEEdge(userAgent) && userAgent.contains("android")) {
      return true;
    }

    return false;
  }

  private boolean isIEEdge(String userAgent)
  {
    if (userAgent.contains("edge/12")) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isIEEdge()
  {
    return isIEEdge(getUserAgent());
  }

}
