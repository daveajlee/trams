package de.davelee.trams.server.model;

import de.davelee.trams.server.constant.VehicleHistoryReason;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the VehicleHistoryEntry class and ensures that its works correctly.
 * @author Dave Lee
 */
public class VehicleHistoryEntryTest {

    /**
     * Ensure that a Route class can be correctly instantiated.
     */
    @Test
    public void testSetters() {
        VehicleHistoryEntry vehicleHistoryEntry = new VehicleHistoryEntry();
        vehicleHistoryEntry.setVehicleHistoryReason(VehicleHistoryReason.PURCHASED);
        vehicleHistoryEntry.setDate(LocalDateTime.of(2021,3,1,0,0));
        vehicleHistoryEntry.setComment("Purchased!");
        assertEquals(VehicleHistoryReason.PURCHASED, vehicleHistoryEntry.getVehicleHistoryReason());
        assertEquals(LocalDateTime.of(2021,3,1,0,0), vehicleHistoryEntry.getDate());
        assertEquals("Purchased!", vehicleHistoryEntry.getComment());
    }

}
