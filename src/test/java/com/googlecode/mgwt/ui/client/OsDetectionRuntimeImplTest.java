package com.googlecode.mgwt.ui.client;

import com.googlecode.mgwt.linker.server.propertyprovider.test.UserAgents;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test for {@link OsDetectionRuntimeImpl}.
 * @author dankurka
 */
@RunWith(MockitoJUnitRunner.class)
public class OsDetectionRuntimeImplTest {

  private OsDetectionRuntimeImpl osDetection;
  private String userAgent;
  private double devicePixelRatio;

  @Before
  public void before() {
    osDetection = new OsDetectionRuntimeImpl() {
      @Override
      String getUserAgent() {
        return userAgent.toLowerCase();
      }

      @Override
      double getDevicePixelRatio() {
        return devicePixelRatio;
      }
    };
  }

  @Test
  public void testNexus5() {
    userAgent = UserAgents.ANDROID_4_4_PHONE_NEXUS_5__USERAGENT;
    devicePixelRatio = 3;

    Assert.assertTrue(osDetection.isAndroid());
    Assert.assertTrue(osDetection.isAndroid4_4_OrHigher());
    Assert.assertTrue(osDetection.isAndroidPhone());
    Assert.assertTrue(osDetection.isPhone());

    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIOs());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testNexus7() {
    userAgent = UserAgents.ANDROID_4_4_TABLET_NEXUS_7_USERAGENT;
    devicePixelRatio = 2;

    Assert.assertTrue(osDetection.isAndroid());
    Assert.assertTrue(osDetection.isAndroid4_4_OrHigher());
    Assert.assertTrue(osDetection.isAndroidTablet());
    Assert.assertTrue(osDetection.isTablet());

    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIOs());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testIphoneIOS7() {
    userAgent = UserAgents.IOS_IPHONE_7_1;
    devicePixelRatio = 2;

    Assert.assertTrue(osDetection.isIOs());
    Assert.assertTrue(osDetection.isPhone());
    Assert.assertTrue(osDetection.isRetina());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIOS6());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testIPadMiniIOS7() {
    userAgent = UserAgents.IOS_IPAD_MINI_7_1;
    devicePixelRatio = 2;

    Assert.assertTrue(osDetection.isIOs());
    Assert.assertTrue(osDetection.isTablet());
    Assert.assertTrue(osDetection.isIPadRetina());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isIOS6());
    Assert.assertFalse(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testIPadIOS7() {
    userAgent = UserAgents.IOS_IPAD_7_1;
    devicePixelRatio = 2;

    Assert.assertTrue(osDetection.isIOs());
    Assert.assertTrue(osDetection.isTablet());
    Assert.assertTrue(osDetection.isIPadRetina());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testIPadIOS6() {
    userAgent = UserAgents.IOS_IPAD_6_0;
    devicePixelRatio = 2;

    Assert.assertTrue(osDetection.isIOs());
    Assert.assertTrue(osDetection.isTablet());
    Assert.assertTrue(osDetection.isIPadRetina());
    Assert.assertTrue(osDetection.isIOS6());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testIPhoneIOS6() {
    userAgent = UserAgents.IOS_IPHONE_6_0;
    devicePixelRatio = 2;

    Assert.assertTrue(osDetection.isIOs());
    Assert.assertTrue(osDetection.isPhone());
    Assert.assertTrue(osDetection.isRetina());
    Assert.assertTrue(osDetection.isIOS6());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testDesktopIE10() {
    userAgent = UserAgents.DESKTOP_IE10;
    devicePixelRatio = 2;

    Assert.assertFalse(osDetection.isIOs());
    Assert.assertFalse(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isIOS6());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertTrue(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }
  
  @Test
  public void testDesktopIE11() {
    userAgent = UserAgents.DESKTOP_IE11;
    devicePixelRatio = 2;

    Assert.assertFalse(osDetection.isIOs());
    Assert.assertFalse(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isIOS6());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertTrue(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testMobileIE10() {
    userAgent = UserAgents.WP8_IE10;
    devicePixelRatio = 2;

    Assert.assertFalse(osDetection.isIOs());
    Assert.assertTrue(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isIOS6());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertTrue(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testMobileIE11() {
    userAgent = UserAgents.WP81_IE11;
    devicePixelRatio = 2;

    Assert.assertFalse(osDetection.isIOs());
    Assert.assertTrue(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isIOS6());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertTrue(osDetection.isWindowsPhone());
    Assert.assertFalse(osDetection.isIEEdge());
  }

  @Test
  public void testDesktopIEEdge() {
    userAgent = UserAgents.DESKTOP_IE_EDGE;
    devicePixelRatio = 2;

    Assert.assertFalse(osDetection.isIOs());
    Assert.assertFalse(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isIOS6());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertTrue(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertFalse(osDetection.isWindowsPhone());
    Assert.assertTrue(osDetection.isIEEdge());
  }

  @Test
  public void testMobileIEEdge() {
    userAgent = UserAgents.MOBILE_IE_EDGE;
    devicePixelRatio = 2;

    Assert.assertFalse(osDetection.isIOs());
    Assert.assertTrue(osDetection.isPhone());
    Assert.assertFalse(osDetection.isRetina());
    Assert.assertFalse(osDetection.isIOS6());

    Assert.assertFalse(osDetection.isAndroid());
    Assert.assertFalse(osDetection.isAndroid4_4_OrHigher());
    Assert.assertFalse(osDetection.isAndroidTablet());
    Assert.assertFalse(osDetection.isTablet());
    Assert.assertFalse(osDetection.isAndroid2x());
    Assert.assertFalse(osDetection.isAndroidPhone());
    Assert.assertFalse(osDetection.isBlackBerry());
    Assert.assertFalse(osDetection.isDesktop());
    Assert.assertFalse(osDetection.isIPad());
    Assert.assertFalse(osDetection.isIPadRetina());
    Assert.assertFalse(osDetection.isIPhone());
    Assert.assertTrue(osDetection.isWindowsPhone());
    Assert.assertTrue(osDetection.isIEEdge());
  }
}
