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
public class AddUserPossibleStep {

    @Autowired
    private UserRepository userRepository;

    private long countedUsers;

    @Given("^there is '(\\d+)' user with '(.*)' email address$")
    public void checkUserByEmail(long expectedUsers, String email) throws Throwable {
        Assert.assertEquals(Long.valueOf(expectedUsers), Long.valueOf(userRepository.countByEmail(email)));
    }


    @When("^try to add new user with  '(.*)' email address$")
    public void addUser(String email) throws Throwable {
        User user = new UserBuilder().setEmail(email).setPassword("pass").setAddress("address").setZipCode("zip")
                .setCity("city").setCountry("country").setDateOfBirth(new Date()).build();
        userRepository.save(user);
        countedUsers = userRepository.countByEmail(email);
    }

    @Then("^new count returns '(\\d+)'$")
    public void getResult(long expectedUsers) throws Throwable {
        Assert.assertEquals(Long.valueOf(expectedUsers), Long.valueOf(countedUsers));
    }



}
