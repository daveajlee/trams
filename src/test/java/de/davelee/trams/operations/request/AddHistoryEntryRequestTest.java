package de.davelee.trams.operations.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the AddHistoryEntryRequest class and ensures that its works correctly.
 * @author Dave Lee
 */
public class AddHistoryEntryRequestTest {

    /**
     * Ensure that a AddHistoryEntryRequest class can be correctly instantiated.
     */
    @Test
    public void testCreateRequest( ) {
        AddHistoryEntryRequest addHistoryEntryRequest = new AddHistoryEntryRequest();
        addHistoryEntryRequest.setCompany("Lee Buses");
        addHistoryEntryRequest.setDate("01-03-2021");
        addHistoryEntryRequest.setFleetNumber("213");
        addHistoryEntryRequest.setComment("Purchased!");
        addHistoryEntryRequest.setReason("Purchased");
        assertEquals("Purchased!", addHistoryEntryRequest.getComment());
        assertEquals("Purchased", addHistoryEntryRequest.getReason());
        assertEquals("213", addHistoryEntryRequest.getFleetNumber());
        assertEquals("01-03-2021", addHistoryEntryRequest.getDate());
        assertEquals("Lee Buses", addHistoryEntryRequest.getCompany());
    }

}
