package org.amoustakos.boilerplate.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public final class StringUtils {

    public static final String NULL_STRING = "null";
    public static final String EMPTY_STRING = " ";



    public static String compileUrl(String protocol, String ip, String port){
        return protocol+"://"+ip+":"+port;
    }


	/**
	 * Splits the provided {@link String} into words and makes the first letter of each one uppercase
	 */
	public static String toCamelCase(@NonNull String text) {
        String myText = text.toLowerCase();
        String[] arr = myText.split("\\s+");
        StringBuilder sb = new StringBuilder();

	    for (String anArr : arr)
		    sb.append(Character.toUpperCase(anArr.charAt(0)))
				    .append(anArr.substring(1)).append(" ");


        return sb.toString().trim();
    }


	/**
	 * Checks if the text is: <br />
	 * 1. null or "null" <br />
	 * 2. "" <br />
	 * 3. " " <br />
	 */
	public static boolean isEmptyString(String text) {
        return (TextUtils.isEmpty(text) ||
                text.trim().equals(NULL_STRING) ||
                text.equals(EMPTY_STRING));
    }

    /**
     * <p>Checks if the CharSequence contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns false.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains digits, and is non-null
     * @since 3.0 Changed signature from isNumeric(String) to isNumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
