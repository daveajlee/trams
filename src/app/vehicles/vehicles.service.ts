import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Vehicle} from './vehicle.model';

@Injectable()
/**
 * This class provides access to the list of vehicles from the server so that all vehicles or a single vehicle from the list can be returned
 * to the Frontend component.
 */
export class VehiclesService {

    vehiclesChanged = new Subject<Vehicle[]>();

    private vehicles: Vehicle[];

    /**
     * Set the list of vehicles to new vehicles supplied from the server.
     * @param vehicles an array of vehicles sent from the server
     */
    setVehicles(vehicles: Vehicle[]): void {
        this.vehicles = vehicles;
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

}
