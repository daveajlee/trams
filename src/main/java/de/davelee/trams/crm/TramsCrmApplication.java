package de.davelee.trams.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class starts the Trams CRM application using Spring Boot.
 * @author Dave Lee
 */
@SpringBootApplication
public class TramsCrmApplication {

    /**
     * Main method to start the application.
     *
     * @param args a <code>String</code> array of arguments which are not read by the application at present.
     */
    public static void main(final String[] args) {
        SpringApplication.run(TramsCrmApplication.class, args);
    }

}
