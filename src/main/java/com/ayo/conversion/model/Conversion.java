package com.ayo.conversion.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
public class Conversion {

    private String target;
    private BigDecimal rate;

    public String getTarget() {
        return target;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @XmlElement
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @XmlElement
    public void setTarget(String target) { this.target = target; }

}
