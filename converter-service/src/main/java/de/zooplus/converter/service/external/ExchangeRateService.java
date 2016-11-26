package de.zooplus.converter.service.external;

import de.zooplus.converter.model.entity.Conversion;
import de.zooplus.converter.service.external.pojo.ExchangeRateResult;

import java.util.Date;

/**
 * Created by dragan
 */
public interface ExchangeRateService {

    Double getExchangeRate(String sourceCurrency, String targetCurrency, Date validOn);

    ExchangeRateResult getLatest(String sourceCurrency, String... targetCurrencies);
}
