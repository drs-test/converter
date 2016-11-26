package de.zooplus.converter.web.controller;

import de.zooplus.converter.model.entity.User;
import de.zooplus.converter.service.external.ExchangeRateService;
import de.zooplus.converter.service.external.pojo.ExchangeRateResult;
import de.zooplus.converter.service.internal.UserService;
import de.zooplus.converter.web.controller.pojo.ConversionDTO;
import de.zooplus.converter.web.controller.pojo.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dragan
 */
@RestController
@RequestMapping("/webservice")
public class RestWebserviceController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value="/conversion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultDTO> getConversion(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                          @RequestParam(value = "sourceCurrency") String sourceCurrency,
                                                          @RequestParam(value = "targetCurrency") String targetCurrency){
        return new ResponseEntity<>(createResult(sourceCurrency, targetCurrency, date), HttpStatus.OK);
    }

    @RequestMapping(value="/conversion/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultDTO> getLatestConversions(@RequestParam(value = "sourceCurrency", required = false) String sourceCurrency,
                                                          @RequestParam(value = "targetCurrency", required = false) String[] targetCurrency){
        return new ResponseEntity<>(createResult(sourceCurrency, targetCurrency), HttpStatus.OK);
    }


    private ResultDTO createResult(String sourceCurrency, String... targetCurrencies){
        ExchangeRateResult result = exchangeRateService.getLatest(sourceCurrency, targetCurrencies);
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("Found result");
        resultDTO.setConversions(result.getRates().entrySet().stream().map(es -> new ConversionDTO(result.getBase(), es.getKey(), result.getDate(), es.getValue())).collect(Collectors.toList()));
        return resultDTO;
    }

    private ResultDTO createResult(String sourceCurrency, String targetCurrency, Date date) {
        ResultDTO resultDTO;
        Double rate = exchangeRateService.getExchangeRate(sourceCurrency, targetCurrency, date);
        if (rate != null) {
            resultDTO = new ResultDTO("Found result", Collections.singletonList(new ConversionDTO(sourceCurrency, targetCurrency, date, rate)));
        } else {
            resultDTO = new ResultDTO("No result for specified parameters", null);
        }

        return resultDTO;
    }
}
