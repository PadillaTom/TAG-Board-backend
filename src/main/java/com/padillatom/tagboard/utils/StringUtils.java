package com.padillatom.tagboard.utils;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String uppercaseFirstCharacter(String in) {
        if (in == null || in.isEmpty()) {
            return in;
        }
        int length = in.length();
        StringBuilder sb = new StringBuilder(length);

        sb.append(Character.toUpperCase(in.charAt(0)));
        if (length > 1) {
            String remaining = in.substring(1);
            sb.append(remaining);
        }
        return sb.toString();
    }
}
