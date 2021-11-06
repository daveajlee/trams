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
         */
        @Override
        public BigDecimal getPurchasePrice() {
            return BigDecimal.valueOf(200000);
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
         */
        @Override
        public BigDecimal getPurchasePrice() {
            return BigDecimal.valueOf(1000000);
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
         * The current purchase price of a train.
         * @return a <code>BigDecimal</code>
         */
        @Override
        public BigDecimal getPurchasePrice() {
            return BigDecimal.valueOf(700000);
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
     * The current purchase price of a train.
     * @return a <code>BigDecimal</code>
     */
    public abstract BigDecimal getPurchasePrice();

}
