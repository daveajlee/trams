package de.davelee.trams.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Simple integration test to ensure 100% test coverage of Spring Boot application.
 * Just to make the metric look good :)
 * @author Dave Lee
 */
@SpringBootTest(classes = TramsServerApplication.class)
public class TramsServerApplicationTest {

    /**
     * Test case: check main method throws no errors
     * No expected result.
     */
    @Test
    public void main() {
        TramsServerApplication.main(new String[] {});
    }

}
