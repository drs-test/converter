package de.zooplus.converter.service.external.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * Created by dragan
 */
public class ExchangeRateResult {

    private String base;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Map<String, Double> rates;

    public ExchangeRateResult() {
    }

    public ExchangeRateResult(String base, Date date, Map<String, Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
