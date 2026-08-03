import { LANDUFF_NAME, LANDUFF_VEHICLES } from "../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_VEHICLES } from "../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_VEHICLES } from "../scenarios/longts-scenario";
import { Alert, Appearance, ScrollView, StyleSheet, Text, View } from "react-native";
import VehicleDetails from "../components/VehicleDetails";
import { TouchableOpacity } from "react-native";
import { useNavigation } from '@react-navigation/native';
import { useEffect, useState } from "react";
import Vehicle from "../models/vehicle";
import IconButton from "../utilities/IconButton";

type VehicleScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

/**
 * This screen shows the details of the vehicle that the user searched for by fleet number.
 * @param route the company, scenario name and fleet number that the user provided
 * @param navigation the navigation object to allow switching screens.
 * @returns the components to be displayed to the user.
 */
function VehicleScreen({route}: VehicleScreenProps) {

    const colorScheme = Appearance.getColorScheme();
    const navigation = useNavigation<NavigationStackParams>();
    const [selectedVehicle, setSelectedVehicle] = useState<Vehicle>();


    useEffect(() => {
        navigation.setOptions({
            headerRight: () => <View style={{marginLeft: 10, flexDirection: 'row'}}>             
                <IconButton icon="trash" size={24} color="black" onPress={onDeleteVehicle}/>
                </View>,
        });

        async function onDeleteVehicle() {
            Alert.alert("Coming Soon!", "Not yet available!");
        }

        async function loadSelectedVehicle() {
            switch (route.params.scenarioName) {
                case LANDUFF_NAME:
                    setSelectedVehicle(LANDUFF_VEHICLES.find((vehicle) => vehicle.fleetNumber === parseInt(route.params.fleetNumber)));
                    break;
                case MDORF_NAME:
                    setSelectedVehicle(MDORF_VEHICLES.find((vehicle) => vehicle.fleetNumber === parseInt(route.params.fleetNumber)));
                    break;
                case LONGTS_NAME:
                    setSelectedVehicle(LONGTS_VEHICLES.find((vehicle) => vehicle.fleetNumber === parseInt(route.params.fleetNumber)));
                    break;
                default:
                    Alert.alert('Error', 'Unknown scenario: ' + route.params.scenarioName);
            }
        }

        loadSelectedVehicle();

    });

    /**
     * Clicking on the fleet button moves the user back to the fleet screen.
     */
    function fleetScreenPress() {
        navigation.navigate("FleetScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    // Display the vehicle to the user or a message to the user if it could not be found.
    if ( selectedVehicle ) {
        return <ScrollView contentContainerStyle={[styles.rootContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <VehicleDetails fleetNumber={selectedVehicle.fleetNumber} registrationNumber={selectedVehicle.registrationNumber} 
            chassisType={selectedVehicle.chassisType} bodyType={selectedVehicle.bodyType} specialFeatures={selectedVehicle.specialFeatures}
            livery={selectedVehicle.livery}/>
            <TouchableOpacity style={styles.button} onPress={fleetScreenPress}>
                <Text style={styles.buttonText}>Fleet</Text>
            </TouchableOpacity>
        </ScrollView>
    }
    else {
        return <ScrollView contentContainerStyle={[styles.rootContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <Text style={[styles.noVehicleText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Did not find any vehicle for the specified fleet number: {route.params.fleetNumber}</Text>
            <TouchableOpacity style={styles.button} onPress={fleetScreenPress}>
                <Text style={styles.buttonText}>Fleet</Text>
            </TouchableOpacity>
        </ScrollView>
    }
}

export default VehicleScreen;

const styles = StyleSheet.create({
    rootContainer: {
        alignItems: "center",
        flex: 1,
        backgroundColor: '#f2ffe6',
    },
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#f2ffe6',
    },
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
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