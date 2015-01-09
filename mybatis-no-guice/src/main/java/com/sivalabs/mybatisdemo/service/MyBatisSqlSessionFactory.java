package com.sivalabs.mybatisdemo.service;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;

public class MyBatisSqlSessionFactory {
    private final static Logger log = LoggerFactory.getLogger(MyBatisSqlSessionFactory.class);
    
    private static SqlSessionFactory factory;

    private MyBatisSqlSessionFactory() { }

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            
            factory = new SqlSessionFactoryBuilder().build(reader, getJdbcProperties());
        } catch (IOException e) {
            throw unchecked(e);
        }
//        
//        Configuration configuration = factory.getConfiguration();
//        
//        configuration.getTypeAliasRegistry().registerAliases("my.package");
//        configuration.addMappers("my.package");
    }

    public static SqlSessionFactory getFactory() {
        return factory;
    }
    
    private static Properties getJdbcProperties() throws IOException {
        String dbUrl = System.getenv("DATABASE_URL");
        
        if (dbUrl != null) {
            log.info("using DATABASE_URL for JDBC properties");
            return getJdbcProperties(dbUrl);
        } else {
            // Normally you'd specify the properties file directly in the XML configuration file.
            // But here we want to be able to chose between it and a constructed alternative.
            log.info("using jdbc.properties for JDBC properties");
            return Resources.getResourceAsProperties("jdbc.properties");
        }
    }
    
    @VisibleForTesting
    static Properties getJdbcProperties(String dbUrl) {
        Pattern pattern = Pattern.compile("postgres://([^:]+):([^@]+)@(.+)");
        Matcher matcher = pattern.matcher(dbUrl);
        
        if (!matcher.matches()) {
            throw new RuntimeException("could not parse DATABASE_URL");
        }
        
        Properties properties = new Properties();
        
        properties.put("jdbc.driverClassName", "org.postgresql.Driver");
        properties.put("jdbc.url", "jdbc:postgresql://" + matcher.group(3));
        properties.put("jdbc.username", matcher.group(1));
        properties.put("jdbc.password", matcher.group(2));
        
        return properties;
    }
    
    private static RuntimeException unchecked(Exception e) {
        return (e instanceof RuntimeException) ? (RuntimeException)e : new UncheckedException(e);
    }
    
    @SuppressWarnings("serial")
    private static class UncheckedException extends RuntimeException {
        public UncheckedException(Exception e) {
            super(e);
        }
    }
}