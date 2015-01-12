package net.betaengine.jettyexample.heroku;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.betaengine.jettyexample.Util;

/** Create JDBC URL and retrieve username and password from Heroku DATABASE_URL environment variable. */
public class HerokuDbProperties {
    private final String url;
    private final String username;
    private final String password;
    
    public HerokuDbProperties() {
        this(Util.getMandatoryEnv("DATABASE_URL"));
    }
    
    public HerokuDbProperties(String dbUrl) {
        Pattern pattern = Pattern.compile("postgres://([^:]+):([^@]+)@(.+)");
        Matcher matcher = pattern.matcher(dbUrl);

        if (!matcher.matches()) {
            // Don't leak passwords to log by including DATABASE_URL values in message.
            throw new HerokuDbPropertiesException("could not parse DATABASE_URL");
        }

        username = matcher.group(1);
        password = matcher.group(2);
        url = "jdbc:postgresql://" + matcher.group(3);
    }
    
    public String getUrl() { return url; }

    public String getUsername() { return username; }
    
    public String getPassword() { return password; }
    
    @SuppressWarnings("serial")
    public static class HerokuDbPropertiesException extends RuntimeException {
        public HerokuDbPropertiesException(String message) { super(message); }
    }
}