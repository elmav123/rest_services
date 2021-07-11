package com.ayo.conversion.exceptions;

public class ConversionNotFoundException extends Exception {

    private static final long serialVersionUID = 3555714415375055302L;

    public ConversionNotFoundException()
    {
        super();
    }

    public ConversionNotFoundException(String message)
    {
        super(message);
    }

    public ConversionNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
