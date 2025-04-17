package com.cloudpos.util;

public class SystemUtil {

	private static final String LOG_TAG = "SystemUtil";

	public static final String CUSTOM_SN = "wp.customer.serialno";
	public static String getCustomSn() {
		return getProp("wp.customer.serialno", "null");
	}
	public static String getProp(String key, String defauleValue) {
		Object value = null;
		try {
			Class<?> systemProperties = Class
					.forName("android.os.SystemProperties");
			value = systemProperties
					.getMethod("get", new Class[]{String.class, String.class})
					.invoke(systemProperties, new Object[]{key, defauleValue});
		} catch (Exception e) {
			value = "";
			e.printStackTrace();
		}
		return value.toString();
	}

}
