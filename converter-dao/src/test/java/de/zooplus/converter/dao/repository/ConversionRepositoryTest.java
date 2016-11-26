package de.zooplus.converter.dao.repository;

import de.zooplus.converter.dao.config.TestDatabaseEmbededConfig;
import de.zooplus.converter.model.entity.Conversion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

/**
 * Created by Dragan Susak on 25-Nov-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@ContextConfiguration(classes = {TestDatabaseEmbededConfig.class}, loader = AnnotationConfigContextLoader.class)
public class ConversionRepositoryTest {

    @Autowired
    private ConversionRepository conversionRepository;

    @Test
    public void testGetAllConversions() throws Exception {

        List<Conversion> conversionList = conversionRepository.findAll();

        Assert.assertEquals(19, conversionList.size());
        Assert.assertEquals(Integer.valueOf(1), conversionList.get(0).getUser().getId());
    }

}
