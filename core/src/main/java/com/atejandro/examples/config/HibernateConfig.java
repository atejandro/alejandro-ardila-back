package com.atejandro.examples.config;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by atejandro on 12/06/17.
 */
@Component
public class HibernateConfig {

    /**
     * Specifies the database hibernate dialect.
     */
    private String dialect;
    /**
     * Action invoked to the database when the Hibernate Session Factory is created.
     */
    private String initAction;
    /**
     * Determines if Hibernate writes an SQL string to the system's console each time it does a query.
     */
    private Boolean writeSqlToConsole;
    /**
     * Determines if the SQL string printed in the system's console has a format.
     */
    private Boolean formatSql;

    public HibernateConfig(String dialect, String initAction, Boolean writeSqlToConsole, Boolean formatSql) {
        this.dialect = dialect;
        this.initAction = initAction;
        this.writeSqlToConsole = writeSqlToConsole;
        this.formatSql = formatSql;
    }

    /**
     * Adds all the properties into a {@link Properties} object.
     *
     * @return all the hibernate properties.
     */
    public Properties asJpaProperties() {

        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, dialect);
        properties.put(AvailableSettings.HBM2DDL_AUTO, initAction);
        properties.put(AvailableSettings.SHOW_SQL, writeSqlToConsole);
        properties.put(AvailableSettings.FORMAT_SQL, formatSql);
        properties.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, false);
        properties.put(AvailableSettings.USE_QUERY_CACHE, false);
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "org.springframework.orm.hibernate5.SpringSessionContext");

        return properties;
    }

    public String getDialect() {
        return dialect;
    }

    public String getInitAction() {
        return initAction;
    }

    public Boolean getWriteSqlToConsole() {
        return writeSqlToConsole;
    }

    public Boolean getFormatSql() {
        return formatSql;
    }
}
