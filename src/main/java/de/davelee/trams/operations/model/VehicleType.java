package de.davelee.trams.operations.model;

import java.math.BigDecimal;

/**
 * This class represents the type of a vehicle as a simple enum which can be returned as part of the vehicle response.
 * @author Dave Lee
 */
public enum VehicleType {

    BUS {
        /**
         * Return the name of the type as a string e.g. Bus
         * @return a <code>String</code> containing the name of the type.
         */
        @Override
        public String getTypeName() {
            return "Bus";
        }

        /**
         * Return the number of years between inspections.
         * @return a <code>int</code> containing the number of years between inspections.
         */
        @Override
        public int getInspectionPeriod() {
            return 3;
        }

        /**
         * Return the maximum number of hours that a vehicle should be in service before taking a break.
         * @return a <code>int</code> containing the maximum number of hours that a vehicle may be in service per day.
         *
         */
        @Override
        public int getMaximumHoursPerDay() {
            return 16;
        }

        /**
         * The current purchase price of a bus.
         * @return a <code>BigDecimal</code> containing the purchase price of the vehicle.
         */
        @Override
        public BigDecimal getPurchasePrice() {
            return BigDecimal.valueOf(200000);
        }

        /**
         * The current inspection price for a bus.
         * @return a <code>BigDecimal</code> containing the inspection price of the vehicle.
         */
        @Override
        public BigDecimal getInspectionPrice() {
            return BigDecimal.valueOf(1000);
        }

    },

    TRAIN {
        /**
         * Return the name of the type as a string e.g. Train
         * @return a <code>String</code> containing the name of the type.
         */
        @Override
        public String getTypeName() {
            return "Train";
        }

        /**
         * Return the number of years between inspections.
         * @return a <code>int</code> containing the number of years between inspections.
         */
        @Override
        public int getInspectionPeriod() {
            return 8;
        }

        /**
         * Return the maximum number of hours that a vehicle should be in service before taking a break.
         * @return a <code>int</code> containing the maximum number of hours that a vehicle may be in service per day.
         */
        @Override
        public int getMaximumHoursPerDay() {
            return 21;
        }

        /**
         * The current purchase price of a train.
         * @return a <code>BigDecimal</code> containing the purchase price of the vehicle.
         */
        @Override
        public BigDecimal getPurchasePrice() {
            return BigDecimal.valueOf(1000000);
        }

        /**
         * The current inspection price for a train.
         * @return a <code>BigDecimal</code> containing the inspection price of the vehicle.
         */
        @Override
        public BigDecimal getInspectionPrice() {
            return BigDecimal.valueOf(2000);
        }
    },

    TRAM {
        /**
         * Return the name of the type as a string e.g. Tram
         * @return a <code>String</code> containing the name of the type.
         */
        @Override
        public String getTypeName() {
            return "Tram";
        }

        /**
         * Return the number of years between inspections.
         * @return a <code>int</code> containing the number of years between inspections.
         */
        @Override
        public int getInspectionPeriod() {
            return 9;
        }

        /**
         * Return the maximum number of hours that a vehicle should be in service before taking a break.
         * @return a <code>int</code> containing the maximum number of hours that a vehicle may be in service per day.
         */
        @Override
        public int getMaximumHoursPerDay() {
            return 20;
        }

        /**
         * The current purchase price of a tram.
         * @return a <code>BigDecimal</code> containing the purchase price of the vehicle.
         */
        @Override
        public BigDecimal getPurchasePrice() {
            return BigDecimal.valueOf(700000);
        }

        /**
         * The current inspection price for a tram.
         * @return a <code>BigDecimal</code> containing the inspection price of the vehicle.
         */
        @Override
        public BigDecimal getInspectionPrice() {
            return BigDecimal.valueOf(1500);
        }
    };

    /**
     * Return the name of the type as a string e.g. Bus
     * @return a <code>String</code> containing the name of the type.
     */
    public abstract String getTypeName();

    /**
     * Return the number of years between inspections.
     * @return a <code>int</code> containing the number of years between inspections.
     */
    public abstract int getInspectionPeriod();

    /**
     * Return the maximum number of hours that a vehicle should be in service before taking a break.
     * @return a <code>int</code> containing the maximum number of hours that a vehicle may be in service per day.
     */
    public abstract int getMaximumHoursPerDay();

    /**
     * The current purchase price of a vehicle.
     * @return a <code>BigDecimal</code> containing the purchase price of the vehicle.
     */
    public abstract BigDecimal getPurchasePrice();

    /**
     * The current inspection price of a vehicle.
     * @return a <code>BigDecimal</code> containing the inspection price of the vehicle.
     */
    public abstract BigDecimal getInspectionPrice();

    /**
     * Return the relevant vehicle type based on the name.
     * @param name a <code>String</code> containing the name of the vehicle type.
     * @return a <code>VehicleType</code> object corresponding to the supplied type name.
     */
    public static VehicleType getVehicleTypeFromName ( final String name ) {
        VehicleType[] types = VehicleType.values();
        for ( VehicleType type : types ) {
            if ( type.getTypeName().contentEquals(name)) {
                return type;
            }
        }
        return null;
    }

}
