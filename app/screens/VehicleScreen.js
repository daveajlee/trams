import { LANDUFF_VEHICLES, MDORF_VEHICLES, LONGTS_VEHICLES } from "../data/scenario-data";
import { ScrollView, StyleSheet, Text } from "react-native";
import VehicleDetails from "../components/VehicleDetails";

function VehicleScreen(props) {

    const fleetNumber = parseInt(props.route.params.fleetNumber);

    var selectedVehicle = LANDUFF_VEHICLES.find((vehicle) => vehicle.fleetNumber === fleetNumber );

    if ( selectedVehicle ) {
        return <ScrollView style={styles.rootContainer}>
            <VehicleDetails fleetNumber={selectedVehicle.fleetNumber} registrationNumber={selectedVehicle.registrationNumber} 
            chassisType={selectedVehicle.chassisType} bodyType={selectedVehicle.bodyType} specialFeatures={selectedVehicle.specialFeatures}
            livery={selectedVehicle.livery}/>
        </ScrollView>
    }
    else {
        return <ScrollView style={styles.rootContainer}>
            <Text>Did not find any vehicle for the specified fleet number: {props.route.params.fleetNumber}</Text>
        </ScrollView>
    }
}

export default VehicleScreen;

const styles = StyleSheet.create({
    rootContainer: {
        marginBottom: 32,
        flex: 1,
        backgroundColor: '#f2ffe6',
    }
})