package com.mybatis.jpa.util;

/**
 * @author svili
 */
public class ColumnNameUtil {

    /**
     * camel to underline
     *
     * @param text
     * @return camel
     */
    public static String camelToUnderline(String text) {
        if (text == null || "".equals(text.trim())) {
            return "";
        }
        StringBuilder result = new StringBuilder(text.length() + 1);
        result.append(text.substring(0, 1));
        for (int i = 1; i < text.length(); i++) {
            if (!Character.isLowerCase(text.charAt(i))) {
                result.append('_');
            }
            result.append(text.substring(i, i + 1));
        }
        return result.toString().toLowerCase();
    }

    /**
     * underline to camel
     *
     * @param text
     * @return underline
     */
    public static String underlineToCamel(String text) {
        if (text == null || "".equals(text.trim())) {
            return "";
        }
        int length = text.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if (c == '_') {
                if (++i < length) {
                    result.append(Character.toUpperCase(text.charAt(i)));
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

}
