package de.davelee.trams.server.constant;

/**
 * This enum contains the various allowed reasons for something to be written into the vehicle's history.
 * @author Dave Lee
 */
public enum VehicleHistoryReason {

    /**
     * Vehicle has been purchased but not yet delivered.
     */
    PURCHASED {
        /**
         * Return the text of purchased.
         * @return a <code>String</code> object representing the text for purchased.
         */
        public String getText() {
            return "Purchased";
        }
    },

    /**
     * Vehicle has been delivered.
     */
    DELIVERED {
        /**
         * Return the text for delivered.
         * @return a <code>String</code> object representing the text for delivered.
         */
        public String getText() { return "Delivered"; }
    },

    /**
     * Vehicle has been inspected.
     */
    INSPECTED {
        /**
         * Return the text for inspected.
         * @return a <code>String</code> object representing the text for inspected.
         */
        public String getText() { return "Inspected"; }
    },

    /**
     * Vehicle has been sold.
     */
    SOLD {
        /**
         * Return the text for sold.
         * @return a <code>String</code> object representing the text for sold.
         */
        public String getText() { return "Sold"; }
    };

    /**
     * Abstract method to return the text for a particular reason.
     * @return a <code>String</code> object representing the text for a particular reason.
     */
    public abstract String getText();

}
