package com.eubrunocoelho.ticketing.context;

public final class ScreenLabelRequestContext
{
    private static final ThreadLocal<String> SCREEN_LABEL =
            new ThreadLocal<>();

    private ScreenLabelRequestContext()
    {
    }

    public static void setScreenLabel( String label )
    {
        SCREEN_LABEL.set( label );
    }

    public static String getScreenLabel()
    {
        return SCREEN_LABEL.get();
    }

    public static void clear()
    {
        SCREEN_LABEL.remove();
    }
}
