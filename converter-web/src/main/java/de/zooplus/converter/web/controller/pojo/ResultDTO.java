package de.zooplus.converter.web.controller.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by dragan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDTO{

    private String message;

    private List<ConversionDTO> conversions;

    public ResultDTO() {
    }

    public ResultDTO(String message, List<ConversionDTO> conversions) {
        this.message = message;
        this.conversions = conversions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ConversionDTO> getConversions() {
        return conversions;
    }

    public void setConversions(List<ConversionDTO> conversions) {
        this.conversions = conversions;
    }
}
