package com.example.dotlinked_proyecto.api.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

class Environment {

  /**
   * Function to get device type
   * @param paramContext Context
   * @return boolean is emulator
   */
  static Boolean isEmulator(Context paramContext) {
    Boolean isEmulator = false;
    try {
      @SuppressLint("PrivateApi") Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
      TelephonyManager localTelephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
      if (getProperty(SystemProperties, "ro.secure").equalsIgnoreCase("0"))
        isEmulator = Boolean.TRUE;
      else if (getProperty(SystemProperties, "ro.kernel.qemu").equalsIgnoreCase("1"))
        isEmulator = Boolean.TRUE;
      else if (Build.PRODUCT.contains("sdk"))
        isEmulator = Boolean.TRUE;
      else if (Build.MODEL.contains("sdk"))
        isEmulator = Boolean.TRUE;
      else {
        assert localTelephonyManager != null;
        if (localTelephonyManager.getSimOperatorName().equals("Android"))
          isEmulator = Boolean.TRUE;
        else if (localTelephonyManager.getNetworkOperatorName().equals("Android"))
          isEmulator = Boolean.TRUE;
        else
          isEmulator = Boolean.FALSE;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return isEmulator;
  }

  private static String getProperty(Class<?> myClass, String propertyName) throws Exception {
    return (String) myClass.getMethod("get", String.class).invoke(myClass, propertyName);
  }
}
