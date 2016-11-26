package de.zooplus.converter.web.controller.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by dragan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionDTO {
    private String sourceCurrency;

    private String targetCurrency;

    private Date date;

    private Double rate;

    public ConversionDTO() {
    }

    public ConversionDTO(String sourceCurrency, String targetCurrency, Date date, Double rate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.date = date;
        this.rate = rate;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
