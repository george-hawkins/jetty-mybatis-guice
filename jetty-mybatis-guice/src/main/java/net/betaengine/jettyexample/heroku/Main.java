package net.betaengine.jettyexample.heroku;

import java.util.Optional;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * This class launches the web application in an embedded Jetty container.
 */
public class Main {
    private final static String WEBAPP_PATH = "src/main/webapp/";
    private final static String WEBXMPL_PATH = WEBAPP_PATH + "/WEB-INF/web.xml";
    private final static String PORT_ENV = "PORT";
    private final static int DEFAULT_PORT = 8080;
    
    public static void main(String[] args) throws Exception {
        Server server = new Server(getPort());
        
        server.setHandler(createRoot());

        server.start();
        server.join();
    }
    
    private static WebAppContext createRoot() {
        WebAppContext root = new WebAppContext();

        // Use standard JSE class loading priority rather than prioritizing WEB-INF/[lib|classes].
        // See http://www.eclipse.org/jetty/documentation/current/jetty-classloading.html
        root.setParentLoaderPriority(true);

        root.setDescriptor(WEBXMPL_PATH);
        root.setResourceBase(WEBAPP_PATH);
        
        return root;
    }
    
    private static int getPort() {
        // Listen on the port specified by the environment variable PORT or if not present use DEFAULT_PORT.
        Optional<String> envPort = Optional.ofNullable(System.getenv(PORT_ENV));
        
        return envPort.map(Integer::parseInt).orElse(DEFAULT_PORT);
    }
}