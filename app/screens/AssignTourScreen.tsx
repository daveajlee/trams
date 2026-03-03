import { Alert, Appearance, StyleSheet, View, Text } from "react-native";
import { LANDUFF_NAME, LANDUFF_VEHICLES } from "../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_VEHICLES } from "../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_VEHICLES } from "../scenarios/longts-scenario";
import { useEffect, useState } from "react";
import Assignment from "../models/assignment";
import { fetchAssignments, insertAssignment } from "../utilities/sqlite";
import { TouchableOpacity } from "react-native";
import { useNavigation } from '@react-navigation/native';
import Vehicle from '../models/vehicle.ts';
import IconTextButton from "../components/IconTextButton.tsx";

type AssignTourScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function AssignTourScreen({route}: AssignTourScreenProps) {

    const navigation = useNavigation<NavigationStackParams>();
    const [vehicles, setVehicles] = useState<Vehicle[]>([]);
    const [assignments, setAssignments] = useState<Assignment[]>([]);

    const colorScheme = Appearance.getColorScheme();

    useEffect(() => {
        navigation.setOptions({
            title: 'Assign vehicle to ' + route.params.routeTourAssignment,
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

        async function loadAssignments() {
            const fetchedAssignments = await fetchAssignments(route.params.company);
            setAssignments(fetchedAssignments);
        }
            
        loadVehicles();

        loadAssignments();

    }, [navigation, route.params.routeTourAssignment, route.params.scenarioName, route.params.company]);

    const scenarioName = route.params.scenarioName;

    async function assignTourHandler(fleetNumber: number) {
        if ( isVehicleAssigned(fleetNumber) ) {
            Alert.alert("Vehicle " + fleetNumber + " is already assigned to another tour.");
            return;
        }
        var assignment = new Assignment(route.params.routeTourAssignment.split("/")[0], parseInt(route.params.routeTourAssignment.split("/")[1], 10), fleetNumber, scenarioName, route.params.company);
        insertAssignment(assignment).then(routePress);
    }

    function isVehicleAssigned(fleetNumber: number) {
        if ( fleetNumber === 101 ) {
            for ( let i = 0; i < assignments.length; i++) {
                if (assignments[i].fleetNumber === fleetNumber) {
                    return true;
                }
            }
        }
        return assignments.some((assignment) => assignment.fleetNumber === fleetNumber);
    }

    async function routePress() {
        navigation.navigate("RouteDetailScreen", {
            routeNumber: route.params.routeTourAssignment.split("/")[0],
            scenarioName: route.params.scenarioName,
            company: route.params.company
        });
    }

    return (
        <View style={[styles.bodyContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <View style={styles.row}>
                {vehicles.map((vehicle) => (
                    <IconTextButton colour={isVehicleAssigned(vehicle.fleetNumber) ? 'red' : 'green'} key={vehicle.fleetNumber} icon="bus" text={"" + vehicle.fleetNumber} onPress={assignTourHandler.bind(null, vehicle.fleetNumber)}/>
                ))}
            </View>

            <TouchableOpacity style={styles.button} onPress={routePress}>
                <Text style={styles.buttonText}>Route {route.params.routeTourAssignment.split("/")[0]}</Text>
            </TouchableOpacity>
        </View>

    )
}

export default AssignTourScreen;

const styles = StyleSheet.create({
    bodyContainer: {
        flex: 4,
        width: '100%',
        alignItems: 'center',
    },
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#f2ffe6',
    },
    darkBorder: {
        borderColor: 'white',
        color: 'white'
    },
    lightBorder: {
        borderColor: 'black',
        color: 'black'
    },
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
    },
    formLabel: {
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center',
        paddingBottom: 16,
        marginRight: 10
    },
    dropdown: {
        borderWidth: 1,
        padding: 2,
        width: '30%'
    },
    dropdownLight: {
        borderColor: '#e4d0ff',
        backgroundColor: 'white',
        color: 'black',
    },
    dropdownDark: {
        borderColor: '#e4d0ff',
        backgroundColor: 'gray',
        color: 'white',
    },
    vehicleField: {
        flexDirection: 'row',
        marginTop: 120
    },
    row: {
        marginTop: 10,
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
        marginBottom: 20,
    },
    buttonText: {
        color: 'white',
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center'
    },
    textItem: {
        color: 'black',
        fontSize: 18,
        marginLeft: 5
    }
})