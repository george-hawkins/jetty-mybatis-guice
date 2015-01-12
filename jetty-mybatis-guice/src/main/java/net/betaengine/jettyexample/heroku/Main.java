package net.betaengine.jettyexample.heroku;

import java.util.Optional;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**  Launch the web application in an embedded Jetty container. */
public class Main {
    private final static String WEBAPP_PATH = "src/main/webapp/";
    private final static String WEBXMPL_PATH = WEBAPP_PATH + "/WEB-INF/web.xml";
    private final static String PORT_ENV = "PORT";
    private final static int DEFAULT_PORT = 8080;
    
    public static void main(String[] args) throws Exception {
        try {
            WebAppContext root = createRoot();
            Server server = new Server(getPort());
            
            server.setHandler(root);
            server.start();
            
            if (!root.isAvailable()) {
                // Container will have already logged the reason.
                System.exit(1);
            }
            
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1); // Without an explicit exit non-daemon threads will keep JVM alive.
        }
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
    
    /** Returns the port specified by the environment variable PORT or DEFAULT_PORT if not present. */
    private static int getPort() {
        Optional<String> envPort = Optional.ofNullable(System.getenv(PORT_ENV));
        
        return envPort.map(Integer::parseInt).orElse(DEFAULT_PORT);
    }
}