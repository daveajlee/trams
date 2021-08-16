package de.davelee.trams.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class starts the Trams Operations application using Spring Boot.
 * @author Dave Lee
 */
@SpringBootApplication
public class TramsBusinessApplication {

    /**
     * Main method to start the application.
     *
     * @param args a <code>String</code> array of arguments which are not read by the application at present.
     */
    public static void main(final String[] args) {
        SpringApplication.run(TramsBusinessApplication.class, args);
    }

}
