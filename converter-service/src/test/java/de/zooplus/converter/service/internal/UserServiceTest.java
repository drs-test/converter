package de.zooplus.converter.service.internal;

import de.zooplus.converter.dao.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;

/**
 * Created by Dragan Susak on 25-Nov-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Test
    public void testEmailAddressUnique() throws Exception {
        Mockito.when(userRepository.countByEmail("dragan.susak@gmail.com")).thenReturn(0L);
        Mockito.when(userRepository.countByEmail("aaaa.bbb@gmail.com")).thenReturn(1L);
        final boolean result = userService.checkEmailUnique("dragan.susak@gmail.com");
        final boolean result2 = userService.checkEmailUnique("aaaa.bbb@gmail.com");
        Assert.assertTrue(result);
        Assert.assertFalse(result2);
    }
}
