package de.zooplus.converter.cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.zooplus.converter.dao.config.TestDatabaseEmbededConfig;
import de.zooplus.converter.dao.model.builder.UserBuilder;
import de.zooplus.converter.dao.repository.UserRepository;
import de.zooplus.converter.model.entity.User;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

/**
 * Created by dragan on 26-Nov-16.
 */
@ContextConfiguration(classes = TestDatabaseEmbededConfig.class)
public class AddUserNotPossibleStep {

    @Autowired
    private UserRepository userRepository;

    private long countedUsers;

    @Given("^there is one user with '(.*)' email address$")
    public void addUserNoPossible(String email) throws Throwable {
        User user = new UserBuilder().setEmail(email).setPassword("pass").setAddress("address").setZipCode("zip")
                .setCity("city").setCountry("country").setDateOfBirth(new Date()).build();
        userRepository.save(user);
    }

    @When("^try to count for users with  '(.*)' email address$")
    public void checkUserNotPossible(String email) throws Throwable {
        countedUsers = userRepository.countByEmail(email);
    }

    @Then("^count returns '(\\d+)'$")
    public void getResultNotPossible(long expectedUsers) throws Throwable {
        Assert.assertEquals(Long.valueOf(expectedUsers), Long.valueOf(countedUsers));
    }

}
