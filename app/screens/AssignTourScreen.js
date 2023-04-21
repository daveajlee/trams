import { StyleSheet, View, Text, TextInput, Button, Alert } from "react-native";
import { Dropdown } from "react-native-element-dropdown";
import { LANDUFF_ROUTES, MDORF_ROUTES, LONGTS_ROUTES, LANDUFF_VEHICLES, MDORF_VEHICLES, LONGTS_VEHICLES } from "../data/scenario-data";
import { useContext, useState } from "react";
import { AssignContext } from "../store/context/assign-context";
import Assignment from "../models/assignment";
import { insertAssignment } from "../utilities/sqlite";

function AssignTourScreen({route, navigation}) {
    const assignContext = useContext(AssignContext);

    var routeData = [];
    var vehicleData = [];
    if ( route.params.scenarioName === 'Landuff') {
        LANDUFF_ROUTES.forEach(addToRouteData);
        LANDUFF_VEHICLES.forEach(addToVehicleData);
    }
    else if ( route.params.routes === 'MDorf') {
        MDORF_ROUTES.forEach(addToRouteData);
        MDORF_VEHICLES.forEach(addToVehicleData);
    }
    else if ( route.params.routes=== 'Longts') {
        LONGTS_ROUTES.forEach(addToRouteData);
        LONGTS_VEHICLES.forEach(addToVehicleData);
    }

    const [routeDropdown, setRouteDropdown] = useState(null);
    const [tourNumber, setTourNumber] = useState(1);
    const [vehicleDropdown, setVehicleDropdown] = useState(null);
    const [disableAssignButton, setDisableAssignButton] = useState(true);
    const routeDatabase = route.params.routes;
    const vehicleDatabase = route.params.vehicles;

    const _renderItem = item => {
        return (
            <View style={styles.item}>
                <Text style={styles.textItem}>{item.label}</Text>
            </View>
        );
    };

    function addToRouteData(item) {
        routeData.push({label: item.number, value: item.number});
    }

    function addToVehicleData(item) {
        vehicleData.push({label: item.fleetNumber, value: item.fleetNumber});
    }

    function onChangeTourNumber(tourNumber) {
        setTourNumber(tourNumber);
        // Calculate the number of tours for the selected route number based on route database.
        var numberTours = 0;
        if ( route.params.scenarioName === 'Landuff') {
            LANDUFF_ROUTES.forEach(addToRouteData);
            LANDUFF_VEHICLES.forEach(addToVehicleData);
        }
        else if ( route.params.routes === 'MDorf') {
            MDORF_ROUTES.forEach(addToRouteData);
            MDORF_VEHICLES.forEach(addToVehicleData);
        }
        else if ( route.params.routes=== 'Longts') {
            LONGTS_ROUTES.forEach(addToRouteData);
            LONGTS_VEHICLES.forEach(addToVehicleData);
        }
        if ( route.params.scenarioName === 'Landuff') {
            numberTours = LANDUFF_ROUTES.find((route) => route.number === routeDropdown).numberTours
        }
        else if ( route.params.routes === 'MDorf') {
            numberTours = MDORF_ROUTES.find((route) => route.number === routeDropdown).numberTours
        }
        else if ( route.params.routes=== 'Longts') {
            numberTours = LONGTS_ROUTES.find((route) => route.number === routeDropdown).numberTours
        }
        // Don't forget to add any tours which were already automatically generated.
        assignContext.savedAdditionalTours.forEach((additionalTour) => {
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
        var assignment = new Assignment(routeDropdown, tourNumber, vehicleDropdown, routeDatabase, vehicleDatabase, route.params.company);
        assignContext.addAssignment(assignment)
        insertAssignment(assignment).then(
            navigation.navigate("ChangeAssignmentScreen", {
                company: route.params.company,
                routes: route.params.routes,
                vehicles: route.params.vehicles
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
                    value={tourNumber}
                    placeholder="1"
                    keyboardType="numeric"
                />
            </View>
            
            <View style={styles.buttonContainer}>
                <Button title="Assign Tour" onPress={assignTourHandler} disabled={disableAssignButton}/>
            </View>
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
        marginTop: 80
    },
    formLabel: {
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center',
        paddingBottom: 16
    },
    dropdown: {
        backgroundColor: '#e4d0ff',
        borderBottomColor: 'gray',
        borderBottomWidth: 0.5,
        width: '50%',
        marginLeft: 10
    },
    vehicleField: {
        flexDirection: 'row',
        marginTop: 120
    },
    tourField: {
        flexDirection: 'row',
        marginTop: 120,
    },
    tourInput: {
        height: 40,
        borderWidth: 1,
        padding: 10,
        marginLeft: 10
    }
})