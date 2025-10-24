package com.eubrunocoelho.ticketing.util;

import com.eubrunocoelho.ticketing.exception.validation.InvalidEnumValueException;

public class EnumUtil
{
    public static <T extends Enum<T>> T getEnumValueOrThrow(
            String value,
            Class<T> enumType
    )
    {
        try
        {
            return Enum.valueOf( enumType, value.toUpperCase() );
        }
        catch ( IllegalArgumentException | NullPointerException ex )
        {
            throw new InvalidEnumValueException( value, enumType );
        }
    }
}
