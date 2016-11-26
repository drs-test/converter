package de.zooplus.converter.cucumber.run;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import de.zooplus.converter.dao.config.TestDatabaseEmbededConfig;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by dragan on 26-Nov-16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty"},
        glue = {"de.zooplus.converter.cucumber.steps"}, features = {"classpath:cucumber/addUser.feature"}
)
@TransactionConfiguration
@ContextConfiguration(classes = {TestDatabaseEmbededConfig.class}, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RunAddUserTest {
}
