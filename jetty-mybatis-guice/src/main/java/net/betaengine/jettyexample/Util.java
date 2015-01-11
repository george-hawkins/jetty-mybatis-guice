package net.betaengine.jettyexample;

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
}
