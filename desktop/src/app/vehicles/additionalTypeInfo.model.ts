/**
 * This class represents a POJO of an additional type information map as it is implemented in the TraMS Server.
 */
export class AdditionalTypeInformation {

    private model: string;
    private age: string;
    private standingCapacity: string;
    private seatingCapacity: string;
    private value: string;
    private registrationNumber: string;
    private powerMode: string;

    /**
     * Set the model of this vehicle.
     * @param model the name of the model as a string.
     */
    setModel(model: string) {
        this.model = model;
    }

    /**
     * Set the age of this vehicle.
     * @param age the age of the vehicle with the unit of measurement as a string.
     */
    setAge(age: string) {
        this.age = age;
    }

    /**
     * Set the standing capacity of this vehicle.
     * @param capacity the capacity of the vehicle as a string.
     */
    setStandingCapacity(capacity: string) {
        this.standingCapacity = capacity;
    }

    /**
     * Set the seating capacity of this vehicle.
     * @param capacity the capacity of this vehicle as a string.
     */
    setSeatingCapacity(capacity: string) {
        this.seatingCapacity = capacity;
    }

    /**
     * Set the value of this vehicle.
     * @param value the value of this vehicle as a string.
     */
    setValue(value: string) {
        this.value = value;
    }

    /**
     * Set the registration number of this vehicle.
     * @param registrationNumber the registration number of this vehicle as a string.
     */
    setRegistrationNumber(registrationNumber: string) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Get the value of this vehicle.
     * @return the value of this vehicle as a string.
     */
    getValue(): string {
        return this.value;
    }

    /**
     * Get the power mode of this vehicle.
     * @return the power mode of this vehicle as a string.
     */
    getPowerMode(): string {
        return this.powerMode;
    }

}