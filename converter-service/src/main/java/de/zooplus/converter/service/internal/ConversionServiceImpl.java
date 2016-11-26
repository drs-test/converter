package de.zooplus.converter.service.internal;

import de.zooplus.converter.dao.repository.ConversionRepository;
import de.zooplus.converter.dao.repository.UserRepository;
import de.zooplus.converter.model.entity.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by dragan
 */
@Service
public class ConversionServiceImpl implements ConversionService{

    @Autowired
    private ConversionRepository conversionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Conversion> getAllForUser(String userEmail) {
        return conversionRepository.findTop10ByUserEmailOrderByIdDesc(userEmail);
    }

    @Cacheable(value = "myCache")
    @Override
    public Optional<Conversion> findByDateAndCurrency(Date date, String sourceCurrency, String targetCurrency) {
        return conversionRepository.findFirst1ByValidOnAndSourceCurrencyAndTargetCurrency(date, sourceCurrency, targetCurrency).stream().findFirst();
    }

    @Transactional
    @Override
    public void saveConversion(Conversion conversion, String userEmail) {
        conversion.setUser(userRepository.findByEmail(userEmail));
        conversionRepository.save(conversion);
    }
}
