import { LANDUFF_NAME, LANDUFF_VEHICLES } from "../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_VEHICLES } from "../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_VEHICLES } from "../scenarios/longts-scenario";
import { ScrollView, StyleSheet, Text } from "react-native";
import VehicleDetails from "../components/VehicleDetails";
import { TouchableOpacity } from "react-native";

function VehicleScreen(props) {

    const fleetNumber = parseInt(props.route.params.fleetNumber);

    function mainMenuPress() {
        props.navigation.navigate("MainMenuScreen", {
            company: props.route.params.company,
            scenarioName: props.route.params.scenarioName,
        });
    }

    var selectedVehicle;
    if ( props.route.params.scenarioName === LANDUFF_NAME) {
        selectedVehicle = LANDUFF_VEHICLES.find((vehicle) => vehicle.fleetNumber === fleetNumber );
    }
    else if ( props.route.params.scenarioName === MDORF_NAME) {
        selectedVehicle = MDORF_VEHICLES.find((vehicle) => vehicle.fleetNumber === fleetNumber );
    }
    else if ( props.route.params.scenarioName === LONGTS_NAME) {
        selectedVehicle = LONGTS_VEHICLES.find((vehicle) => vehicle.fleetNumber === fleetNumber );
    }

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
            <Text style={styles.noVehicleText}>Did not find any vehicle for the specified fleet number: {props.route.params.fleetNumber}</Text>
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