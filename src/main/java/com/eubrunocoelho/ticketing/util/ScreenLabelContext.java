package com.eubrunocoelho.ticketing.util;

public class ScreenLabelContext {

    private static final ThreadLocal<String> currentScreenLabel = new ThreadLocal<>();

    public static void setLabel(String label) {
        currentScreenLabel.set(label);
    }

    public static String getLabel() {
        return currentScreenLabel.get();
    }

    public static void clear() {
        currentScreenLabel.remove();
    }
}
