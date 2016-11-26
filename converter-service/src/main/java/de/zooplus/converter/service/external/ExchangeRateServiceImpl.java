package de.zooplus.converter.service.external;

import de.zooplus.converter.service.external.pojo.ExchangeRateResult;
import de.zooplus.converter.service.external.util.ExchangeRateUriBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dragan
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExchangeRateUriBuilder uriBuilder;

    @Cacheable("myCache")
    @Override
    public Double getExchangeRate(String sourceCurrency, String targetCurrency, Date validOn) {

        ExchangeRateResult result = restTemplate.getForObject(uriBuilder.buildUri(sourceCurrency, targetCurrency, validOn), ExchangeRateResult.class);

        return result.getRates().get(targetCurrency);
    }

    @Cacheable("myCache")
    @Override
    public ExchangeRateResult getLatest(String sourceCurrency, String... targetCurrencies) {

        return restTemplate.getForObject(uriBuilder.buildLatestUri(sourceCurrency, targetCurrencies), ExchangeRateResult.class);
    }
}
