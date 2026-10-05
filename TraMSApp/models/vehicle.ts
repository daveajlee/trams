class Vehicle {

  fleetNumber: number;
  registrationNumber: string;
  modelName: string;
  chassisType: string;
  seatingCapacity: number;
  standingCapacity: number;
  value: number;
  livery: string;

    constructor(fleetNumber: number, registrationNumber: string, modelName: string, chassisType: string, seatingCapacity: number, standingCapacity: number, value: number, livery: string) {
        this.fleetNumber = fleetNumber;
        this.registrationNumber = registrationNumber;
        this.modelName = modelName;
        this.chassisType = chassisType;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.value = value;
        this.livery = livery;
    }
}

export default Vehicle;