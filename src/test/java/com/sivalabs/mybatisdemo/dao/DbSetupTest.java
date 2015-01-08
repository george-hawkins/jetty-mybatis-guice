package com.sivalabs.mybatisdemo.dao;

import org.junit.Assert;

import java.util.Properties;

import org.junit.Test;

public class DbSetupTest {
    @Test
    public void dbUrlTest() {
        String dbUrl = "postgres://my.username:my.password@my.host:1234/my.path?my.query";
        
        Properties properties = MyBatisSqlSessionFactory.getJdbcProperties(dbUrl);
        
        Assert.assertEquals("org.postgresql.Driver", properties.getProperty("jdbc.driverClassName"));
        Assert.assertEquals("jdbc:postgresql://my.host:1234/my.path?my.query", properties.getProperty("jdbc.url"));
        Assert.assertEquals("my.username", properties.getProperty("jdbc.username"));
        Assert.assertEquals("my.password", properties.getProperty("jdbc.password"));
    }
}
