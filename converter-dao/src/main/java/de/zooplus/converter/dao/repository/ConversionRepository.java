package de.zooplus.converter.dao.repository;

import de.zooplus.converter.model.entity.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by dragan
 */
@Repository
public interface ConversionRepository extends JpaRepository<Conversion,Integer> {

    List<Conversion> findTop10ByUserEmailOrderByIdDesc(String email);

    List<Conversion> findFirst1ByValidOnAndSourceCurrencyAndTargetCurrency(Date date, String sourceCurrency, String targetCurrency);
}
