package com.eubrunocoelho.ticketing.util;

public class EnumUtil {

    public static <T extends Enum<T>> T getEnumValueOrNull(Class<T> enumType, String value) {
        if (value == null) return null;

        try {
            return Enum.valueOf(enumType, value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
