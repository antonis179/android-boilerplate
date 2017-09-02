package org.amoustakos.boilerplate.util;

import java.text.DecimalFormat;



public final class ConversionUtil {


    /*
     ************** Double conversions *******************
     */

    public static String formatDouble(String value) {
        Double d = getDouble(value, 0D);

        if (d != null)
            return formatDouble(d);
        else
            return value;
    }

    public static Double getDouble(String s, Double defaultNum) {
        if (!StringUtils.isEmptyString(s)) {
            String s2 = s.trim();
            if(StringUtils.isEmptyString(s2))
                return Double.parseDouble(s2);
        }

        return defaultNum;
    }

    /**
     * Formats a double to 2 decimal places.
     */
    public static String formatDouble(double value) {
        return new DecimalFormat("#.##").format(value);
    }


	/*
	 ************** Float conversions *******************
	 */

    public static String formatFloat(String value) {
        Float f = getFloat(value, 0F);

        if (f != null)
            return formatFloat(f);
        else
            return value;
    }
    public static Float getFloat(String s, Float defaultNum) {
        if (!StringUtils.isEmptyString(s)) {
            String s2 = s.trim();
            if(StringUtils.isEmptyString(s2))
                return Float.parseFloat(s2);
        }

        return defaultNum;
    }
    /**
     * Formats a float to 2 decimal places.
     */
    public static String formatFloat(Float value) {
        return new DecimalFormat("#.##").format(value);
    }



	/*
	 ************** Integer conversions *******************
	 */


    public static Integer getInt(String s, Integer defaultNum) {
        if (!StringUtils.isEmptyString(s)) {
            String s2 = s.trim();
            if (StringUtils.isNumeric(s2))
                return Integer.parseInt(s2);
        }
        return defaultNum;
    }



	/*
	 ************** Long conversions *******************
	 */


    public static Long getLong(String s, Long defaultNum) {
        if (!StringUtils.isEmptyString(s)) {
            String s2 = s.trim();
            if (StringUtils.isNumeric(s2))
                return Long.parseLong(s2);
        }
        return defaultNum;
    }



	/*
	 ************** Boolean conversions *******************
	 */


    public static Boolean getBoolean(String s, Boolean defaultBool) {
        if (!StringUtils.isEmptyString(s)) {
            String s2 = s.trim();
            return Boolean.parseBoolean(s2);
        }
        return defaultBool;
    }

    public static Boolean getBooleanFromInt(String s, Boolean defaultBool) {
        if (!StringUtils.isEmptyString(s)) {
            Integer number = getInt(s, null);
            if(number == null)
                return defaultBool;
            else if (number.equals(0))
                return Boolean.FALSE;
            else if (number.equals(1))
                return Boolean.TRUE;
        }
        return defaultBool;
    }


	/*
     ************** Percentage conversions *******************
     */

    public static int getPercent(int value, int max) {
        return (value * 100) / max;
    }
}
