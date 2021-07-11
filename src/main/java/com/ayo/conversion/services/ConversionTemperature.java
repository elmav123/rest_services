package com.ayo.conversion.services;

import com.ayo.conversion.exceptions.ConversionInvalidInputException;
import com.ayo.conversion.exceptions.ConversionNoDataException;
import com.ayo.conversion.exceptions.ConversionNotFoundException;
import com.ayo.conversion.model.Category;
import com.ayo.conversion.model.Conversion;
import com.ayo.conversion.model.Measurements;
import com.ayo.conversion.model.Unit;
import com.ayo.conversion.representations.ConversionRequest;
import com.ayo.conversion.representations.ConversionResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

@Component
public class ConversionTemperature {

    public ConversionResponse convert(ConversionRequest conversionRequest) throws ConversionInvalidInputException {
        ConversionResponse response = new ConversionResponse();
        String sourceUnit = conversionRequest.getSourceUnit();
        String targetUnit = conversionRequest.getTargetUnit();
        BigDecimal quantity = conversionRequest.getSourceQuantity();

        if (sourceUnit == null || targetUnit == null || quantity == null) {
            throw new ConversionInvalidInputException("Invalid source and target input values received.");
        }
        if (sourceUnit.trim().equalsIgnoreCase("c") && !(targetUnit.trim().equalsIgnoreCase("c") ||
                targetUnit.trim().equalsIgnoreCase("f"))) {
            throw new ConversionInvalidInputException("Invalid source and target input values received. Can only convert 'c' to 'c' or 'f'." );
        }
        if (sourceUnit.trim().equalsIgnoreCase("f") && !(targetUnit.trim().equalsIgnoreCase("f") ||
                targetUnit.trim().equalsIgnoreCase("c"))) {
            throw new ConversionInvalidInputException("Invalid source and target input values received. Can only convert 'f' to 'c' or 'f'." );
        }
        response.setSourceQuantity(quantity);
        response.setSourceUnit(sourceUnit);
        response.setTargetUnit(targetUnit);
        if ( (sourceUnit.trim().equalsIgnoreCase("c") && targetUnit.trim().equalsIgnoreCase("c")) ||
                (sourceUnit.trim().equalsIgnoreCase("f") && targetUnit.trim().equalsIgnoreCase("f"))) {
            response.setResult(new BigDecimal(1));
        } else  if ( sourceUnit.trim().equalsIgnoreCase("c") && targetUnit.trim().equalsIgnoreCase("f")) {
            BigDecimal result = quantity.multiply(new BigDecimal(1.8)).add(new BigDecimal(32));
            response.setResult(result);
        } else  if ( sourceUnit.trim().equalsIgnoreCase("f") && targetUnit.trim().equalsIgnoreCase("c")) {
            BigDecimal result = quantity.subtract(new BigDecimal(32));
            BigDecimal division = new BigDecimal(1.8000);
            result = result.divide(division, 2, RoundingMode.HALF_EVEN);
            response.setResult(result);
        }

        return response;
    }

}
