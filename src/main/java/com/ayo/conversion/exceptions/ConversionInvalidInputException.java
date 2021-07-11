package com.ayo.conversion.exceptions;

public class ConversionInvalidInputException extends Exception {

    private static final long serialVersionUID = 3555714415375055302L;

    public ConversionInvalidInputException()
    {
        super();
    }

    public ConversionInvalidInputException(String message)
    {
        super(message);
    }

    public ConversionInvalidInputException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
