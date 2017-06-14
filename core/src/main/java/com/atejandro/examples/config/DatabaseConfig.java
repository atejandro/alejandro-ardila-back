package com.atejandro.examples.config;

import org.springframework.stereotype.Component;

/**
 * Holds all the configuration concerning to the database.
 */
@Component
public class DatabaseConfig {
    /**
     * The database's driver.
     */
    private String driver;
    /**
     * The database's url.
     */
    private String url;
    /**
     * The database's username.
     */
    private String username;
    /**
     * The database's password.
     */
    private String password;

    public DatabaseConfig(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
