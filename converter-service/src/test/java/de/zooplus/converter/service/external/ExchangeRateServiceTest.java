package de.zooplus.converter.service.external;

import de.zooplus.converter.service.external.pojo.ExchangeRateResult;
import de.zooplus.converter.service.external.util.ExchangeRateUriBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

/**
 * Created by dragan
 */
@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl();

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ExchangeRateUriBuilder uriBuilder;


    @Test
    public void getExchangeRate() throws Exception {
        Mockito.when(uriBuilder.buildUri(any(), any(), any())).thenReturn(new URI("http://localhost/exchange"));
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.1d);
        rates.put("AUD", 1.2d);
        rates.put("CHF", 1.3d);
        Mockito.when(restTemplate.getForObject(any(), any())).
                thenReturn(new ExchangeRateResult("EUR", new Date(), rates));

        Double result = exchangeRateService.getExchangeRate("EUR", "USD", new Date());
        Assert.assertEquals(result, Double.valueOf(1.1d));

        Double result2 = exchangeRateService.getExchangeRate("EUR", "AUD", new Date());
        Assert.assertEquals(result2, Double.valueOf(1.2d));


        Double result3 = exchangeRateService.getExchangeRate("EUR", "CHF", new Date());
        Assert.assertEquals(result3, Double.valueOf(1.3d));
    }

    @Test
    public void getLatest() throws Exception {
        Mockito.when(uriBuilder.buildLatestUri(any(), any())).thenReturn(new URI("http://localhost/exchange/latest"));
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.1d);
        rates.put("AUD", 1.2d);
        rates.put("CHF", 1.3d);

        Mockito.when(restTemplate.getForObject(any(), any())).
                thenReturn(new ExchangeRateResult("EUR", new Date(), rates));

        Double result = exchangeRateService.getExchangeRate("EUR", "USD", new Date());
        Assert.assertEquals(result, Double.valueOf(1.1d));

        Double result2 = exchangeRateService.getExchangeRate("EUR", "AUD", new Date());
        Assert.assertEquals(result2, Double.valueOf(1.2d));


        Double result3 = exchangeRateService.getExchangeRate("EUR", "CHF", new Date());
        Assert.assertEquals(result3, Double.valueOf(1.3d));
    }

}