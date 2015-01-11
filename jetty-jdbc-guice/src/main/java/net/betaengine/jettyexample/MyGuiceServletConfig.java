package net.betaengine.jettyexample;

import static net.betaengine.jettyexample.Util.unchecked;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class MyGuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
      return Guice.createInjector(createServletModule(), createMainModule());
    }
    
    private Module createServletModule() {
        return new ServletModule() {
            @Override
            protected void configureServlets() {
              serve("/*").with(SimpleServlet.class);
            }
        };
    }
    
    private Module createMainModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(Connection.class).toProvider(ConnectionProvider.class);
            }
        };
    }

    // This isn't terribly sensible - see http://stackoverflow.com/a/1531103/245602
    private static class ConnectionProvider implements Provider<Connection> {
        @Override
        public Connection get() {
            try {
                String dbUrl = System.getenv("DATABASE_URL");
                HerokuDbProperties properties = new HerokuDbProperties(dbUrl);

                return DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
            } catch (SQLException e) {
                throw unchecked(e);
            }
        }
    }
}
