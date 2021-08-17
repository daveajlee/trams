package de.davelee.trams.crm;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

/**
 * This class starts the Trams CRM application using Spring Boot.
 * @author Dave Lee
 */
@SpringBootApplication
public class TramsCrmApplication {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    /**
     * Main method to start the application.
     *
     * @param args a <code>String</code> array of arguments which are not read by the application at present.
     */
    public static void main(final String[] args) {
        SpringApplication.run(TramsCrmApplication.class, args);
    }

}
