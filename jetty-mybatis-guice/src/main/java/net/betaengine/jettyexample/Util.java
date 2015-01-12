package net.betaengine.jettyexample;

import java.util.Optional;

public class Util {
    public static RuntimeException unchecked(Exception e) {
        return (e instanceof RuntimeException) ? (RuntimeException)e : new UncheckedException(e);
    }

    @SuppressWarnings("serial")
    public static class UncheckedException extends RuntimeException {
        public UncheckedException(Exception e) {
            super(e);
        }
    }
    
    public static String getMandatoryEnv(String name) {
        return Optional.ofNullable(System.getenv(name))
        .orElseThrow(() -> new MandatoryEnvException(name + " not present"));
    }
    
    @SuppressWarnings("serial")
    public static class MandatoryEnvException extends RuntimeException {
        public MandatoryEnvException(String message) { super(message); }
    }
}
