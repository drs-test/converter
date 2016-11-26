package de.zooplus.converter.service.external.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dragan on 26-Nov-16.
 */
@Component
public class ExchangeRateUriBuilder {


    @Value("${EXCHANGE_REST_BASE_URL}")
    private URI baseUrl;

    @Value("${EXCHANGE_REST_BASE_URL_LATEST}")
    private URI baseUrlLatest;

    public URI buildUri (String sourceCurrency, String targetCurrency, Date validOn){
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(baseUrl.resolve(new SimpleDateFormat("yyyy-MM-dd").format(validOn)));
        uriComponentsBuilder.queryParam("base", sourceCurrency);
        uriComponentsBuilder.queryParam("symbols", targetCurrency);

       return uriComponentsBuilder.build().toUri();
    }

    public URI buildLatestUri(String sourceCurrency, String... targetCurrencies){
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(baseUrlLatest);
        if (StringUtils.isNotEmpty(sourceCurrency)) {
            uriComponentsBuilder.queryParam("base", sourceCurrency);
        }
        if (ArrayUtils.isNotEmpty(targetCurrencies)) {
            uriComponentsBuilder.queryParam("symbols", StringUtils.join(targetCurrencies, ','));
        }
        return uriComponentsBuilder.build().toUri();
    }
}
