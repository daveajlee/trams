import { LANDUFF_NAME, LANDUFF_VEHICLES } from "../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_VEHICLES } from "../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_VEHICLES } from "../scenarios/longts-scenario";
import { ScrollView, StyleSheet, Text } from "react-native";
import VehicleDetails from "../components/VehicleDetails";
import { TouchableOpacity } from "react-native";

/**
 * This screen shows the details of the vehicle that the user searched for by fleet number.
 * @param route the company, scenario name and fleet number that the user provided
 * @param navigation the navigation object to allow switching screens.
 * @returns the components to be displayed to the user.
 */
function VehicleScreen({route, navigation}) {

    // Note the fleet number as an integer that the user provided.
    const fleetNumber = parseInt(route.params.fleetNumber);

    /**
     * Clicking on the main menu button moves the user back to the main menu screen.
     */
    function mainMenuPress() {
        navigation.navigate("MainMenuScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    // Search by scenario name and fleet number.
    var selectedVehicle;
    if ( route.params.scenarioName === LANDUFF_NAME) {
        selectedVehicle = LANDUFF_VEHICLES.find((vehicle) => vehicle.fleetNumber === fleetNumber );
    }
    else if ( route.params.scenarioName === MDORF_NAME) {
        selectedVehicle = MDORF_VEHICLES.find((vehicle) => vehicle.fleetNumber === fleetNumber );
    }
    else if ( route.params.scenarioName === LONGTS_NAME) {
        selectedVehicle = LONGTS_VEHICLES.find((vehicle) => vehicle.fleetNumber === fleetNumber );
    }

    // Display the vehicle to the user or a message to the user if it could not be found.
    if ( selectedVehicle ) {
        return <ScrollView contentContainerStyle={styles.rootContainer}>
            <VehicleDetails fleetNumber={selectedVehicle.fleetNumber} registrationNumber={selectedVehicle.registrationNumber} 
            chassisType={selectedVehicle.chassisType} bodyType={selectedVehicle.bodyType} specialFeatures={selectedVehicle.specialFeatures}
            livery={selectedVehicle.livery}/>
            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
        </ScrollView>
    }
    else {
        return <ScrollView contentContainerStyle={styles.rootContainer}>
            <Text style={styles.noVehicleText}>Did not find any vehicle for the specified fleet number: {route.params.fleetNumber}</Text>
            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
        </ScrollView>
    }
}

export default VehicleScreen;

const styles = StyleSheet.create({
    rootContainer: {
        alignItems: "center",
        marginBottom: 32,
        flex: 1,
        backgroundColor: '#f2ffe6',
    },
    noVehicleText: {
        alignItems: "center",
        fontSize: 20,
        fontWeight: "bold",
        marginTop: 10
    },
    button: {
        alignItems: "center",
        backgroundColor: "#5e7947",
        width: '90%',
        padding: 20,
        marginTop: 30,
        marginBottom: 20,
    },
    buttonText: {
        color: 'white',
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center'
    }
})