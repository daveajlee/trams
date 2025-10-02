class Vehicle {

  fleetNumber: number;
  registrationNumber: string;
  chassisType: string;
  bodyType: string;
  specialFeatures: string;
  livery: string;

    constructor(fleetNumber: number, registrationNumber: string, chassisType: string, bodyType: string, specialFeatures: string, livery: string) {
        this.fleetNumber = fleetNumber;
        this.registrationNumber = registrationNumber;
        this.chassisType = chassisType;
        this.bodyType = bodyType;
        this.specialFeatures = specialFeatures;
        this.livery = livery;
    }
}

export default Vehicle;