package de.zooplus.converter.dao.repository;

import de.zooplus.converter.dao.config.TestDatabaseEmbededConfig;
import de.zooplus.converter.dao.model.builder.UserBuilder;
import de.zooplus.converter.model.entity.User;
import org.hibernate.LazyInitializationException;
import org.hibernate.PropertyValueException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Date;

/**
 * Created by Dragan Susak on 25-Nov-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@ContextConfiguration(classes = {TestDatabaseEmbededConfig.class}, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Transactional
    @Test
    public void testFindUser() throws Exception {
        User user = userRepository.findOne(1);
        Assert.assertEquals(Integer.valueOf(1), user.getId());
        Assert.assertEquals(19, user.getConversions().size());
    }

    @Test(expected = LazyInitializationException.class)
    public void testLazyException() throws Exception {
        User user = userRepository.findOne(1);
        Assert.assertEquals(19, user.getConversions().size());
    }

    @Transactional
    @Test(expected = PersistenceException.class)
    public void testSaveEmptyUser() throws Exception {
        User user = new User();
        userRepository.save(user);
        Assert.assertEquals(Integer.valueOf(2), user.getId());
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new UserBuilder().setEmail("email").setPassword("pass").setAddress("address").setZipCode("zip")
                .setCity("city").setCountry("country").setDateOfBirth(new Date()).build();
        userRepository.save(user);
        Assert.assertEquals(Integer.valueOf(2), user.getId());
    }

    @Transactional
    @Test
    public void testFindUserByEmail() throws Exception {
        User user = userRepository.findByEmail("dragan.susak@gmail.com");
        Assert.assertNotNull(user);
        Assert.assertEquals(19, user.getConversions().size());
    }

    @Test
    public void testCountUserByEmail() throws Exception {
        User user = new UserBuilder().setEmail("dragan.susak@gmail.com").setPassword("pass").setAddress("address").setZipCode("zip")
                .setCity("city").setCountry("country").setDateOfBirth(new Date()).build();
        userRepository.save(user);
        long count = userRepository.countByEmail("dragan.susak@gmail.com");
        Assert.assertEquals(2, count);
    }

}
