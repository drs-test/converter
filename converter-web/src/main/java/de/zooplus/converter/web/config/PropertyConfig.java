package de.zooplus.converter.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

/**
 * Created by dragan
 */
@Configuration
@PropertySource({"classpath:converter.properties"})
public class PropertyConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer applicationProperties() {
        final Properties properties = new Properties();
        properties.setProperty("systemPropertiesModeName", "SYSTEM_PROPERTIES_MODE_FALLBACK");
        properties.setProperty("searchSystemEnvironment", "true");

        final PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setProperties(properties);

        return propertyConfigurer;
    }
}
