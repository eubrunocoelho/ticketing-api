package com.eubrunocoelho.ticketing.util;

public class ScreenLabelContext
{
    private static final ThreadLocal<String> CURRENT_SCREEN_LABEL = new ThreadLocal<>();

    public static void setLabel( String label )
    {
        CURRENT_SCREEN_LABEL.set( label );
    }

    public static String getLabel()
    {
        return CURRENT_SCREEN_LABEL.get();
    }

    public static void clear()
    {
        CURRENT_SCREEN_LABEL.remove();
    }
}
