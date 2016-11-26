package de.zooplus.converter.service.internal;

import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dragan
 */
@Service
public class CountryServiceImpl implements CountryService {
    @Override
    public List<Locale> getAllCountries() {
        return Stream.of(Locale.getISOCountries()).
                map(c -> new Locale("", c)).sorted((l1, l2) -> l1.getDisplayCountry().compareToIgnoreCase(l2.getDisplayCountry())).collect(Collectors.toList());
    }
}
