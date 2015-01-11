package net.betaengine.jettyexample;

import java.util.Properties;

import net.betaengine.jettyexample.mybatis.domain.User;
import net.betaengine.jettyexample.mybatis.mapper.UserMapper;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class MyGuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
      return Guice.createInjector(createServletModule(), createMyBatisModule());
    }
    
    private Module createServletModule() {
        return new ServletModule() {
            @Override
            protected void configureServlets() {
              serve("/*").with(SimpleServlet.class);
            }
        };
    }
    
    private Module createMyBatisModule() {
        return new MyBatisModule() {
            @Override
            protected void initialize() {
                bindDataSourceProviderType(PooledDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);
                
                addSimpleAlias(User.class);
                addMapperClass(UserMapper.class);

                Names.bindProperties(binder(), createProperties());
            }
        };
    }
    
    private Properties createProperties() {
        String dbUrl = System.getenv("DATABASE_URL");
        HerokuDbProperties properties = new HerokuDbProperties(dbUrl);
        
        return createProperties(properties.getUrl(), properties.getUsername(), properties.getPassword());
    }
    
    
    private Properties createProperties(String url, String username, String password) {
        Properties properties = new Properties();
        
        properties.put("mybatis.environment.id", "production");
        
        properties.put("JDBC.driver", "org.postgresql.Driver");
        properties.put("JDBC.url", url);
        properties.put("JDBC.username", username);
        properties.put("JDBC.password", password);
        
        return properties;
    }
}
