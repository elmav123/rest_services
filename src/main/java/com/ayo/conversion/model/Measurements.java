package com.ayo.conversion.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
public class Measurements {

	private List<Category> categories;

	public List<Category> getCategory() {
		return categories;
	}

	@XmlElement(name="category")
	public void setCategory(List<Category> categories) {
		this.categories = categories;
	}


}
