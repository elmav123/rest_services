package com.ayo.conversion.controller;

import com.ayo.conversion.exceptions.ConversionInvalidInputException;
import com.ayo.conversion.exceptions.ConversionNoDataException;
import com.ayo.conversion.exceptions.ConversionNotFoundException;
import com.ayo.conversion.representations.ConversionRequest;
import com.ayo.conversion.representations.ConversionResponse;
import com.ayo.conversion.services.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/convert/")
public class ConversionController {

	private final ConversionService myService;

	@Autowired // inject ConversionService implementation
	public ConversionController(@Qualifier("xmlService") ConversionService myService) {
		this.myService = myService;
	}

	@GetMapping("{source_unit}/to/{target_unit}/{value}")
	public ResponseEntity<?> convert(@PathVariable("source_unit") String source_unit,
									  @PathVariable("target_unit") String target_unit,
									  @PathVariable("value") String value) {

		ConversionRequest conversionReq = new ConversionRequest();
		try {
			BigDecimal bigValue = new BigDecimal(value);
			conversionReq.setSourceQuantity(bigValue);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid value specified for conversion.");
		}
		conversionReq.setSourceUnit(source_unit);
		conversionReq.setTargetUnit(target_unit);
		ConversionResponse response = null;
		try {
			response = myService.convert(conversionReq);
		} catch (ConversionNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ConversionInvalidInputException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
