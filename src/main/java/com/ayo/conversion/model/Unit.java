package com.ayo.conversion.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Unit {

    private String name;
    private String symbol;
    private String type;

    private List<Conversion> conversions;


    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    @XmlElement
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public List<Conversion> getConversions() {
        return conversions;
    }

    @XmlElement(name="conversion")
    public void setConversions(List<Conversion> conversions) {
        this.conversions = conversions;
    }
}
