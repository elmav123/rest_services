package com.ayo.conversion.services;

import com.ayo.conversion.exceptions.ConversionInvalidInputException;
import com.ayo.conversion.exceptions.ConversionNotFoundException;
import com.ayo.conversion.representations.ConversionRequest;
import com.ayo.conversion.representations.ConversionResponse;

public interface ConversionService {

    public ConversionResponse convert(ConversionRequest session) throws ConversionNotFoundException, ConversionInvalidInputException;

}
