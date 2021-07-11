package com.ayo.conversion.model;

import javax.xml.bind.annotation.*;
import java.util.List;

public class Category {

	private String name;

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}


	private List<Unit> units;

	public List<Unit> getUnits() {
		return units;
	}

	@XmlElement(name="unit")
	public void setUnits(List<Unit> units) {
		this.units = units;
	}

}
