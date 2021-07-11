package com.ayo.conversion.junit;

import com.ayo.conversion.exceptions.ConversionInvalidInputException;
import com.ayo.conversion.exceptions.ConversionNoDataException;
import com.ayo.conversion.exceptions.ConversionNotFoundException;
import com.ayo.conversion.representations.ConversionRequest;
import com.ayo.conversion.representations.ConversionResponse;
import com.ayo.conversion.services.ConversionTemperature;
import com.ayo.conversion.services.xml.ConversionServiceXml;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;

class ConversionUnitTests {

    private ConversionServiceXml conversionServiceXml;

    private ConversionTemperature convertTemperature;

    @BeforeEach
    void initUseCase() throws ConversionNoDataException {
        conversionServiceXml = new ConversionServiceXml();
        convertTemperature = new ConversionTemperature();
    }

    @Test
    void testMetricToImperialConversions() throws ConversionNotFoundException, ConversionInvalidInputException {
        ConversionRequest conversionReq = new ConversionRequest();
        conversionReq.setSourceQuantity(new BigDecimal("1"));
        conversionReq.setSourceUnit("mm");
        conversionReq.setTargetUnit("in");
        ConversionResponse response = conversionServiceXml.convert(conversionReq);

        Assert.notNull(response, "Failed to convert 'mm' to 'in' for quantity 1.");
        Assertions.assertEquals(0.0393700787, response.getResult().doubleValue());
    }

    @Test
    void testMetricToMetricConversions() throws ConversionNotFoundException, ConversionInvalidInputException {
        ConversionRequest conversionReq = new ConversionRequest();
        conversionReq.setSourceQuantity(new BigDecimal("1"));
        conversionReq.setSourceUnit("mm");
        conversionReq.setTargetUnit("m");
        ConversionResponse response = conversionServiceXml.convert(conversionReq);

        Assert.notNull(response, "Failed to convert 'mm' to 'm' for quantity 1.");
        Assertions.assertEquals(0.001, response.getResult().doubleValue());
    }

    @Test
    void testImperialToImperialConversions() throws ConversionNotFoundException, ConversionInvalidInputException {
        ConversionRequest conversionReq = new ConversionRequest();
        conversionReq.setSourceQuantity(new BigDecimal("1"));
        conversionReq.setSourceUnit("in");
        conversionReq.setTargetUnit("yd");
        ConversionResponse response = conversionServiceXml.convert(conversionReq);

        Assert.notNull(response, "Failed to convert 'in' to 'yd' for quantity 1.");
        Assertions.assertEquals(0.0277777778, response.getResult().doubleValue());
    }

    @Test
    void testImperialToMetricConversions() throws ConversionNotFoundException, ConversionInvalidInputException {
        ConversionRequest conversionReq = new ConversionRequest();
        conversionReq.setSourceQuantity(new BigDecimal("1"));
        conversionReq.setSourceUnit("ft");
        conversionReq.setTargetUnit("km");
        ConversionResponse response = conversionServiceXml.convert(conversionReq);

        Assert.notNull(response, "Failed to convert 'ft' to 'km' for quantity 1.");
        Assertions.assertEquals(0.0003048, response.getResult().doubleValue());

    }

    @Test
    void testCelciusToFarConversions() throws ConversionNotFoundException, ConversionInvalidInputException {
        ConversionRequest conversionReq = new ConversionRequest();
        conversionReq.setSourceQuantity(new BigDecimal("1"));
        conversionReq.setSourceUnit("c");
        conversionReq.setTargetUnit("f");
        ConversionResponse response = convertTemperature.convert(conversionReq);

        Assert.notNull(response, "Failed to convert 'c' to 'f' for quantity 1.");
        Assertions.assertEquals(33.8, response.getResult().doubleValue());
    }

    @Test
    void testFarToCelciusConversions() throws ConversionNotFoundException, ConversionInvalidInputException {
        ConversionRequest conversionReq = new ConversionRequest();
        conversionReq.setSourceQuantity(new BigDecimal("1"));
        conversionReq.setSourceUnit("f");
        conversionReq.setTargetUnit("c");
        ConversionResponse response = convertTemperature.convert(conversionReq);

        Assert.notNull(response, "Failed to convert 'f' to 'c' for quantity 1.");
        Assertions.assertEquals(-17.22, response.getResult().doubleValue());
    }

}

