package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the builder, getter and setter methods of the <code>EmployDriverRequest</code> class.
 */
public class EmployDriverRequestTest {

    /**
     * Test the builder and ensure variables are set together using the getter methods.
     */
    @Test
    public void testBuilder ( ) {
        EmployDriverRequest employDriverRequest = EmployDriverRequest.builder()
                .company("Mustermann GmbH")
                .name("Max Mustermann")
                .contractedHours(35)
                .startDate("29-06-2024 00:00")
                .build();
        assertEquals("Mustermann GmbH", employDriverRequest.getCompany());
        assertEquals("Max Mustermann", employDriverRequest.getName());
        assertEquals(35, employDriverRequest.getContractedHours());
        assertEquals("29-06-2024 00:00", employDriverRequest.getStartDate());
    }

    /**
     * Test the setter methods and ensure variables are set together using the getter methods.
     */
    @Test
    public void testSettersAndGetters() {
        EmployDriverRequest employDriverRequest = new EmployDriverRequest();
        employDriverRequest.setCompany("Mustermann GmbH");
        employDriverRequest.setName("Max Mustermann");
        employDriverRequest.setContractedHours(40);
        employDriverRequest.setStartDate("30-06-2024 00:00");
        assertEquals("Mustermann GmbH", employDriverRequest.getCompany());
        assertEquals("Max Mustermann", employDriverRequest.getName());
        assertEquals(40, employDriverRequest.getContractedHours());
        assertEquals("30-06-2024 00:00", employDriverRequest.getStartDate());
    }

}
