package de.davelee.trams.server.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the Driver class and ensures that its works correctly.
 * @author Dave Lee
 */
public class DriverTest {

    /**
     * Ensure that a Driver class can be correctly instantiated.
     */
    @Test
    public void testBuilderGetterSetterToString ( ) {
        //Test builder
        Driver driver = Driver.builder()
                .name("Max Mustermann")
                .company("Lee Buses")
                .startDate(LocalDateTime.of(2021,3,25,0,0))
                .contractedHours(35)
                .build();
        //Verify the builder functionality through getter methods
        assertEquals("Max Mustermann", driver.getName());
        assertEquals(LocalDateTime.of(2021,3,25,0,0), driver.getStartDate());
        assertEquals(35, driver.getContractedHours());
        assertEquals("Lee Buses", driver.getCompany());
        //Verify the toString method
        assertEquals("Driver(id=null, name=Max Mustermann, company=Lee Buses, contractedHours=35, startDate=2021-03-25T00:00)", driver.toString());
        //Now use the setter methods
        driver.setName("Erica Mustermann");
        driver.setContractedHours(40);
        driver.setStartDate(LocalDateTime.of(2021,3,31,0,0));
        driver.setCompany("Lee Buses 2");
        //And verify again through the toString methods
        assertEquals("Driver(id=null, name=Erica Mustermann, company=Lee Buses 2, contractedHours=40, startDate=2021-03-31T00:00)", driver.toString());
    }

}
