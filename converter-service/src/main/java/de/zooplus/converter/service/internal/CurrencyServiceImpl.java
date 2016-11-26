package de.zooplus.converter.service.internal;

import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dragan
 */
@Service
public class CurrencyServiceImpl implements CurrencyService{
    @Override
    public List<Currency> getAllCurrencies() {
        return Currency.getAvailableCurrencies().stream().sorted((c1, c2) -> c1.getCurrencyCode().compareToIgnoreCase(c2.getCurrencyCode())).collect(Collectors.toList());
    }
}
