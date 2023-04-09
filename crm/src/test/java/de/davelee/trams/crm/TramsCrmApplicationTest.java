package de.davelee.trams.crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Simple integration test to ensure 100% test coverage of Spring Boot application.
 * Just to make the metric look good :)
 * @author Dave Lee
 */
@SpringBootTest(classes = TramsCrmApplication.class)
public class TramsCrmApplicationTest {

    /**
     * Test case: check main method throws no errors
     * No expected result.
     */
    @Test
    public void main() {
        TramsCrmApplication.main(new String[] {});
    }

}
