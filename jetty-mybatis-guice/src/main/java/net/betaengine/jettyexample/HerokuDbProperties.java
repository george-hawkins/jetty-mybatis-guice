package net.betaengine.jettyexample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HerokuDbProperties {
    private final String url;
    private final String username;
    private final String password;
    
    public HerokuDbProperties(String dbUrl) {
        Pattern pattern = Pattern.compile("postgres://([^:]+):([^@]+)@(.+)");
        Matcher matcher = pattern.matcher(dbUrl);

        if (!matcher.matches()) {
            throw new RuntimeException("could not parse DATABASE_URL");
        }

        username = matcher.group(1);
        password = matcher.group(2);
        url = "jdbc:postgresql://" + matcher.group(3);
    }
    
    public String getUrl() { return url; }

    public String getUsername() { return username; }
    
    public String getPassword() { return password; }
}