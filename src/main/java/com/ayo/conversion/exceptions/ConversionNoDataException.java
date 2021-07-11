package com.ayo.conversion.exceptions;

public class ConversionNoDataException extends Exception
{
    private static final long serialVersionUID = 3555714415375055302L;

    public ConversionNoDataException()
    {
        super();
    }

    public ConversionNoDataException(String message)
    {
        super(message);
    }

    public ConversionNoDataException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
