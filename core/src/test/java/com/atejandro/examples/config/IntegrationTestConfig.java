package com.atejandro.examples.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by atejandro on 13/06/17.
 */
@Configuration
@PropertySource("classpath:integration-test.properties")
public class IntegrationTestConfig {

    /**
     * Reference for a {@link PropertySourcesPlaceholderConfigurer} to handle '.properties' files.
     */
    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Creates all the necessary configuration for the database.
     *
     * @param dbDriver   the database driver.
     * @param dbUrl      the database url.
     * @param dbUsername the database username.
     * @param dbPassword the database password.
     * @return a database configuration.
     */
    @Bean
    DatabaseConfig databaseConfig(@Value("${jdbc.it.driver}") String dbDriver,
                                  @Value("${jdbc.it.url}") String dbUrl,
                                  @Value("${jdbc.it.username}") String dbUsername,
                                  @Value("${jdbc.it.password}") String dbPassword) {
        return new DatabaseConfig(dbDriver, dbUrl, dbUsername, dbPassword);
    }

    /**
     * Creates all the necessary configuration for hibernate.
     *
     * @param dialect the database hibernate dialect.
     * @return a hibernate configuration.
     */
    @Bean
    HibernateConfig hibernateConfig(@Value("${jdbc.it.hibernate.dialect}") String dialect) {
        return new HibernateConfig(dialect, "create-drop", false, false);
    }

}
