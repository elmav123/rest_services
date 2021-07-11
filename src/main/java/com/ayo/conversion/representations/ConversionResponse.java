package com.ayo.conversion.representations;

import java.math.BigDecimal;

public class ConversionResponse {

	private BigDecimal sourceQuantity;
	private String sourceUnit;
	private String targetUnit;
	private BigDecimal result;

	public BigDecimal getSourceQuantity() { return sourceQuantity; }

	public void setSourceQuantity(BigDecimal sourceQuantity) {
		this.sourceQuantity = sourceQuantity;
	}

	public String getSourceUnit() {
		return sourceUnit;
	}

	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	public String getTargetUnit() {
		return targetUnit;
	}

	public void setTargetUnit(String targetUnit) {
		this.targetUnit = targetUnit;
	}

	public BigDecimal getResult() { return result; }

	public void setResult(BigDecimal result) { this.result = result; }
}
