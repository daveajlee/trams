package de.davelee.trams.server.utils;

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
     * This method converts the Map of a TicketRequest into the correct Map format for saving as a Ticket.
     * @param priceList a <code>Map</code> object to convert
     * @return a <code>Map</code> object that has been converted.
     */
    public static Map<String, BigDecimal> convertPriceListToBigDecimal (final Map<String, Double> priceList) {
        Map<String, BigDecimal> convertedPriceList = new HashMap<>();
        Iterator<Map.Entry<String, Double>> iterator = priceList.entrySet().stream().iterator();
        while ( iterator.hasNext() ) {
            Map.Entry<String, Double> thisEntry = iterator.next();
            convertedPriceList.put(thisEntry.getKey(), BigDecimal.valueOf(thisEntry.getValue()));
        }
        return convertedPriceList;
    }

    /**
     * This method converts the Map of a Ticket into the correct Map format for returning as a TicketResponse.
     * @param priceList a <code>Map</code> object to convert
     * @return a <code>Map</code> object that has been converted.
     */
    public static Map<String, Double> convertPriceListToDouble (final Map<String, BigDecimal> priceList) {
        Map<String, Double> convertedPriceList = new HashMap<>();
        Iterator<Map.Entry<String, BigDecimal>> iterator = priceList.entrySet().stream().iterator();
        while ( iterator.hasNext() ) {
            Map.Entry<String, BigDecimal> thisEntry = iterator.next();
            convertedPriceList.put(thisEntry.getKey(), thisEntry.getValue().doubleValue());
        }
        return convertedPriceList;
    }

}
