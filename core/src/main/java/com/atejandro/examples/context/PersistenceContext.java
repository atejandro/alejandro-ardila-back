package com.atejandro.examples.context;

/**
 * Created by atejandro on 13/06/17.
 */

import com.atejandro.examples.config.DatabaseConfig;
import com.atejandro.examples.config.HibernateConfig;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Persistence context of the application.
 */
@Configuration
@ComponentScan({"com.atejandro.examples.*"})
@EnableTransactionManagement
public class PersistenceContext {

    /**
     * Establishes the data source main connection properties.
     *
     * @return the datasource.
     */
    @Bean
    DataSource dataSource(DatabaseConfig config) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(config.getDriver());
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());

        return dataSource;
    }

    /**
     * This is the most powerful way to set up a shared JPA EntityManagerFactory in a Spring application context.
     * For more information take a look to:
     * http://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa
     *
     * @param dataSource a datasource.
     * @param config     a hibernate configuration.
     * @return a Hibernate session factory.
     */
    @Bean
    LocalSessionFactoryBean sessionFactory(DataSource dataSource, HibernateConfig config) {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(config.asJpaProperties());
        sessionFactory.setPackagesToScan("com.atejandro.examples.model");

        return sessionFactory;
    }

    /**
     * Creates a {@link HibernateTransactionManager}.
     *
     * @param sessionFactory a Hibernate session factory.
     * @return the hibernate transaction manager.
     */
    @Bean
    @Autowired
    HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }


}
