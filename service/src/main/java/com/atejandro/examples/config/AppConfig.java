package com.atejandro.examples.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by atejandro on 11/06/17.
 */
@Configuration
@ComponentScan(value = "com.atejandro.examples.*")
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public HibernateConfig hibernateConfig (@Value("${hibernate.dialect}") String dialect,
                                            @Value("${hibernate.hbm2ddl.auto}") String initAction,
                                            @Value("${hibernate.show.sql}") Boolean showSql,
                                            @Value("${hibernate.format.sql}") Boolean formatSql){
        return new HibernateConfig(dialect, initAction, showSql, formatSql);
    }

    @Bean
    public DatabaseConfig databaseConfig(@Value("${jdbc.driver}") String dbDriver,
                                         @Value("${jdbc.url}") String dbUrl,
                                         @Value("${jdbc.username}") String dbUsername,
                                         @Value("${jdbc.password}") String dbPassword){
        return new DatabaseConfig(dbDriver, dbUrl, dbUsername, dbPassword);
    }

}
