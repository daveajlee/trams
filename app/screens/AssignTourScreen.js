import { StyleSheet, View, Text, TextInput, Alert } from "react-native";
import { Dropdown } from "react-native-element-dropdown";
import { LANDUFF_NAME, LANDUFF_ROUTES, LANDUFF_VEHICLES } from "../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_ROUTES, MDORF_VEHICLES } from "../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_ROUTES, LONGTS_VEHICLES } from "../scenarios/longts-scenario";
import { useEffect, useState } from "react";
import Assignment from "../models/assignment";
import { fetchAdditionalTours, fetchAssignments, insertAssignment } from "../utilities/sqlite";
import { TouchableOpacity } from "react-native";


function AssignTourScreen({route, navigation}) {

    const [routeData, setRouteData] = useState([]);
    const [vehicleData, setVehicleData] = useState([]);
    const [routeDropdown, setRouteDropdown] = useState(null);
    const [tourNumber, setTourNumber] = useState(1);
    const [vehicleDropdown, setVehicleDropdown] = useState(null);
    const [disableAssignButton, setDisableAssignButton] = useState(true);
    const [assignments, setAssignments] = useState([]);

    useEffect(() => {
        async function loadVehicleAndRouteData() {
            // Retrieve any assignments that already exist.
            setAssignments(await fetchAssignments(route.params.company));
            // Retrieve routes and vehicles for the specified scenario.
            if ( route.params.scenarioName === LANDUFF_NAME) {
                setRouteData(formatRouteData(LANDUFF_ROUTES));
                setVehicleData(await filterVehicleData(LANDUFF_VEHICLES));
            }
            else if ( route.params.scenarioName === MDORF_NAME) {
                setRouteData(formatRouteData(MDORF_ROUTES));
                setVehicleData(await filterVehicleData(MDORF_VEHICLES));
            }
            else if ( route.params.scenarioName=== LONGTS_NAME) {
                setRouteData(formatRouteData(LONGTS_ROUTES));
                setVehicleData(await filterVehicleData(LONGTS_VEHICLES));
            }
            // Perform random situation if more than 3 assignments.
            if ( assignments.length > 3 ) {
                performRandomSituation();
            }
        }

        loadVehicleAndRouteData();
    }, []);

    const scenarioName = route.params.scenarioName;

    const _renderItem = item => {
        return (
            <View>
                <Text style={styles.textItem}>{item.label}</Text>
            </View>
        );
    };

    async function filterVehicleData(scenarioVehicles ) {
        var vehicleFilteredData = [];
        for ( const vehicle of scenarioVehicles ) {
            var vehicleAssigned = false;
            var assignments = await fetchAssignments(route.params.company);
            for ( const assignment of assignments ) {
                if ( assignment.fleetNumber === vehicle.fleetNumber ) {
                    vehicleAssigned = true;
                }
            }
            if ( !vehicleAssigned ) {
                console.log('Adding vehicle ' + vehicle.fleetNumber);
                vehicleFilteredData.push({label: vehicle.fleetNumber, value: vehicle.fleetNumber});
            }
        }
        return vehicleFilteredData;
    }

    function formatRouteData(scenarioRoutes) {
        var routeData = [];
        for ( const route of scenarioRoutes ) {
            routeData.push({label: route.number, value: route.number});
        }
        return routeData;
    }

    async function onChangeTourNumber(tourNumber) {
        setTourNumber(tourNumber);
        // Calculate the number of tours for the selected route number based on route database.
        var numberTours = 0;
        if ( scenarioName === 'Landuff') {
            numberTours = LANDUFF_ROUTES.find((route) => route.number === routeDropdown).numberTours
        }
        else if ( scenarioName === 'MDorf') {
            numberTours = MDORF_ROUTES.find((route) => route.number === routeDropdown).numberTours
        }
        else if ( scenarioName === 'Longts') {
            numberTours = LONGTS_ROUTES.find((route) => route.number === routeDropdown).numberTours
        }
        // Don't forget to add any tours which were already automatically generated.
        const additionalTours = await fetchAdditionalTours(route.params.company);
        additionalTours.forEach((additionalTour) => {
            if ( additionalTour.split('/')[0] === routeDropdown ) {
                numberTours++;
            }
        })
        // If the tour number was less than or equal to 0 or higher than the number of tours for the selected route number then show an alert and ask for a valid tour number.
        if ( tourNumber <= 0 || tourNumber > numberTours ) {
            Alert.alert('Please enter a valid tour number between 1 and ' +  numberTours);
            setDisableAssignButton(true);
        } else {
            setDisableAssignButton(false);
        }
    }

    async function assignTourHandler() {
        var assignment = new Assignment(routeDropdown, tourNumber, vehicleDropdown, scenarioName, route.params.company);
        insertAssignment(assignment).then(
            navigation.navigate("ChangeAssignmentScreen", {
                company: route.params.company,
                scenarioName: route.params.scenarioName
            }));
        
    }

    return (
        <View style={styles.bodyContainer}>
            <View style={styles.routeField}>
                <Text style={styles.formLabel}>Route Number:</Text>
                <Dropdown
                    style={styles.dropdown}
                    data={routeData}
                    labelField="label"
                    valueField="value"
                    placeholder="Select item"
                    value={routeDropdown}
                    onChange={item => {
                        setRouteDropdown(item.value);
                        console.log('selected', item);                  
                    }}
                    renderItem={item => _renderItem(item)}
                 />
            </View>
            <View style={styles.vehicleField}>
                <Text style={styles.formLabel}>Fleet Number:</Text>
                <Dropdown
                    style={styles.dropdown}
                    data={vehicleData}
                    labelField="label"
                    valueField="value"
                    placeholder="Select item"
                    value={vehicleDropdown}
                    onChange={item => {
                        setVehicleDropdown(item.value);
                        console.log('selected', item);
                    }}
                    renderItem={item => _renderItem(item)}
                />
            </View>
            <View style={styles.tourField}>
                <Text style={styles.formLabel}>Tour Number:</Text>
                <TextInput
                    style={styles.tourInput}
                    onChangeText={onChangeTourNumber}
                    value={"" + tourNumber}
                    keyboardType="numeric"
                />
            </View>
            
            <TouchableOpacity style={styles.button} onPress={assignTourHandler} disabled={disableAssignButton}>
                <Text style={styles.buttonText}>Assign Tour</Text>
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
        backgroundColor: '#f2ffe6',
    },
    routeField: {
        flexDirection: 'row',
        marginTop: 80,
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
        borderColor: '#e4d0ff',
        backgroundColor: 'white',
        color: 'black',
        padding: 2,
        width: '30%'
    },
    vehicleField: {
        flexDirection: 'row',
        marginTop: 120
    },
    tourField: {
        flexDirection: 'row',
        marginTop: 120,
        marginBottom: 30
    },
    tourInput: {
        height: 40,
        borderWidth: 1,
        padding: 10,
        marginLeft: 10
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