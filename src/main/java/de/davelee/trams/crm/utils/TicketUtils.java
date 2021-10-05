package de.davelee.trams.crm.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class provides utility methods for processing related to /ticket endpoints in the TicketController.
 * @author Dave Lee
 */
public class TicketUtils {

    /**
     * This method converts the Map of a TicketResponse into the correct Map format for saving as a Ticket.
     * @param priceList a <code>Map</code> object to convert
     * @return a <code>Map</code> object that has been converted.
     */
    public static Map<String, BigDecimal> convertPriceList (final Map<String, Double> priceList) {
        Map<String, BigDecimal> convertedPriceList = new HashMap<>();
        Iterator<Map.Entry<String, Double>> iterator = priceList.entrySet().stream().iterator();
        while ( iterator.hasNext() ) {
            Map.Entry<String, Double> thisEntry = iterator.next();
            convertedPriceList.put(thisEntry.getKey(), BigDecimal.valueOf(thisEntry.getValue()));
        }
        return convertedPriceList;
    }

}
