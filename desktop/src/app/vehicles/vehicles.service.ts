import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Vehicle} from './vehicle.model';
import {VehiclesResponse} from './vehicles-response.model';
import {AdditionalTypeInformation} from "./additionalTypeInfo.model";

@Injectable()
/**
 * This class provides access to the list of vehicles from the server so that all vehicles or a single vehicle from the list can be returned
 * to the Frontend component.
 */
export class VehiclesService {

    private vehiclesChanged = new Subject<Vehicle[]>();

    private vehicles: Vehicle[];

    /**
     * Set the list of vehicles to new vehicles supplied from the server.
     * @param vehicles an array of vehicles sent from the server
     */
    setVehicles(vehicles: VehiclesResponse): void {
        this.vehicles = [];
        for ( let vehicle of vehicles.vehicleResponses) {
            this.vehicles.push(new Vehicle(vehicle.fleetNumber, vehicle.vehicleType, vehicle.livery, vehicle.allocatedTour,
                vehicle.inspectionStatus, vehicle.nextInspectionDueInDays, new AdditionalTypeInformation()))
        }
        this.vehiclesChanged.next(this.vehicles.slice());
    }

    /**
     * Return the current list of vehicles which the server has provided.
     */
    getVehicles(): Vehicle[] {
        return this.vehicles.slice();
    }

    /**
     * Return a single vehicle based on the supplied position in the array.
     * @param index a number containing the position in the array to return.
     */
    getVehicle(index: number): Vehicle {
        return this.vehicles.slice()[index];
    }

    /**
     * Retrieve the vehicles that changed.
     * @return an array of vehicles that changed as Subject objects.
     */
    getVehiclesChanged(): Subject<Vehicle[]> {
        return this.vehiclesChanged;
    }

}
