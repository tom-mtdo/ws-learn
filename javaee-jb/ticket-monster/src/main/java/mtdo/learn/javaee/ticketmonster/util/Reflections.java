package mtdo.learn.javaee.ticketmonster.util;

import java.net.URL;

public class Reflections {

    private Reflections() {
    }

    /**
     * Get a resource by name, first searching the thread context classloader, and then the classloader of the Reflections
     * class.
     */
    public static URL getResource(String name) {
        if (Thread.currentThread().getContextClassLoader() != null) {
            return Thread.currentThread().getContextClassLoader().getResource(name);
        } else {
            return Reflections.class.getClassLoader().getResource(name);
        }
    }

}
