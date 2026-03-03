import { LANDUFF_NAME, LANDUFF_VEHICLES } from "../../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_VEHICLES } from "../../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_VEHICLES } from "../../scenarios/longts-scenario";
import { useEffect, useState } from "react";
import { Alert, Appearance, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import Vehicle from "../../models/vehicle";
import { useNavigation } from "@react-navigation/native";
import IconTextButton from "../../components/IconTextButton";

type FleetScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function FleetScreen({route}: FleetScreenProps) {

    const colorScheme = Appearance.getColorScheme();
    const navigation = useNavigation<NavigationStackParams>();

    const [vehicles, setVehicles] = useState<Vehicle[]>([]);

    useEffect(() => {
        navigation.setOptions({
            title: route.params.company + ' - Fleet',
        });
    
        async function loadVehicles() {
            switch (route.params.scenarioName) {
                case LANDUFF_NAME:
                    setVehicles(LANDUFF_VEHICLES);
                    break;
                case MDORF_NAME:
                    setVehicles(MDORF_VEHICLES);
                    break;
                case LONGTS_NAME:
                    setVehicles(LONGTS_VEHICLES);
                    break;
                default:
                    Alert.alert('Error', 'Unknown scenario: ' + route.params.scenarioName);
            }
        }
    
        loadVehicles();
    }, [route.params.scenarioName, navigation, route.params.company]);

    function onSearchFleetPress(fleetNumber: number) {
        navigation.navigate("VehicleScreen", {
            fleetNumber: fleetNumber,
            scenarioName: route.params.scenarioName,
            company: route.params.company
        });
    }

    function mainMenuPress() {
        navigation.navigate("MainMenuScreen", {
            scenarioName: route.params.scenarioName,
            company: route.params.company
        });
    }

    return <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
        <View style={styles.bodyContainer}>
            <View style={styles.row}>
                {vehicles.map((vehicle) => (
                    <IconTextButton key={vehicle.fleetNumber} icon="bus" text={"" + vehicle.fleetNumber} onPress={onSearchFleetPress.bind(null, vehicle.fleetNumber)}/>
                ))}
            </View>

            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
        </View>
    </View>
}

export default FleetScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#f2ffe6',
    },
    bodyContainer: {
        flex: 4,
        width: '100%',
        alignItems: 'center',
        marginTop: 30
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        width: '100%',
        flexWrap: 'wrap',
        alignItems: 'flex-start'
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