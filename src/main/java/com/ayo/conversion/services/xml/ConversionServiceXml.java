package com.ayo.conversion.services.xml;

import com.ayo.conversion.exceptions.ConversionInvalidInputException;
import com.ayo.conversion.exceptions.ConversionNoDataException;
import com.ayo.conversion.exceptions.ConversionNotFoundException;
import com.ayo.conversion.representations.ConversionRequest;
import com.ayo.conversion.representations.ConversionResponse;
import com.ayo.conversion.model.Category;
import com.ayo.conversion.model.Conversion;
import com.ayo.conversion.model.Measurements;
import com.ayo.conversion.model.Unit;
import com.ayo.conversion.services.ConversionService;
import com.ayo.conversion.services.ConversionTemperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("dataService")
@Qualifier("xmlService")
public class ConversionServiceXml implements ConversionService {

    @Autowired
    private ConversionTemperature convertTemperature;

    private List<Category> categories = new ArrayList<Category>();

    public ConversionServiceXml() throws ConversionNoDataException {
        JAXBContext jaxbContext;

        Resource resource = new ClassPathResource("Conversions.xml");
        InputStream inputStream = null;

        try {

            inputStream = resource.getInputStream();
            jaxbContext = JAXBContext.newInstance(Measurements.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Measurements measurements = (Measurements) jaxbUnmarshaller.unmarshal(inputStream);
            categories = measurements.getCategory();

        } catch (JAXBException | IOException e) {
           throw new ConversionNoDataException("Unable to parse \"Conversions.xml\" file.");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public ConversionResponse convert(ConversionRequest conversionRequest) throws ConversionNotFoundException, ConversionInvalidInputException {
        ConversionResponse response = new ConversionResponse();
        String sourceUnit = conversionRequest.getSourceUnit();
        String targetUnit = conversionRequest.getTargetUnit();
        BigDecimal quantity = conversionRequest.getSourceQuantity();

        response.setSourceQuantity(conversionRequest.getSourceQuantity());
        response.setSourceUnit(conversionRequest.getSourceUnit());
        response.setTargetUnit(conversionRequest.getTargetUnit());

        if (sourceUnit == null || targetUnit == null || quantity == null) {
            throw new ConversionInvalidInputException("Invalid source and target input values received.");
        }
        response.setSourceQuantity(quantity);
        response.setSourceUnit(sourceUnit);
        response.setTargetUnit(targetUnit);
        if ( sourceUnit.trim().equalsIgnoreCase("c") || targetUnit.trim().equalsIgnoreCase("c") ||
                sourceUnit.trim().equalsIgnoreCase("f") || targetUnit.trim().equalsIgnoreCase("f") ){
            return convertTemperature.convert(conversionRequest);
        } else {
            for (Category category : categories) {
                for (Unit unit : category.getUnits()) {
                    if (unit.getSymbol() != null && unit.getSymbol().trim().equalsIgnoreCase(sourceUnit.trim())) {
                        for (Conversion conversion : unit.getConversions()) {
                            if (conversion.getTarget() != null && conversion.getTarget().trim().equalsIgnoreCase(targetUnit.trim())) {
                                if (conversion.getRate() != null) {
                                    BigDecimal result = conversion.getRate().multiply(quantity);
                                    response.setResult(result);
                                    return response;
                                }
                            }
                        }
                    }
                }
            }

        }
        throw new ConversionNotFoundException("No conversion rate found for source unit " + conversionRequest.getSourceUnit() + " and target unit " + conversionRequest.getTargetUnit());
    }

}
