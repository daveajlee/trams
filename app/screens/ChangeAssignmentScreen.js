import { useEffect, useState } from "react";
import { Alert, StyleSheet, Text, View } from "react-native";
import AssignmentList from "../components/AssignmentList";
import { LANDUFF_ROUTES, LANDUFF_VEHICLES } from "../scenarios/landuff-scenario";
import { LONGTS_ROUTES, LONGTS_VEHICLES } from "../scenarios/longts-scenario";
import { MDORF_ROUTES, MDORF_VEHICLES } from "../scenarios/mdorf-scenario";
import { deleteAssignment, fetchAssignments, insertAdditionalTour, fetchAdditionalTours } from "../utilities/sqlite";
import AdditionalTour from "../models/additionalTour";
import { TouchableOpacity } from "react-native";

function ChangeAssignmentScreen({route, navigation}) {

    const [assignments, setAssignments] = useState([]);

    useEffect(() => {
        async function loadAssignments() {
            const fetchedAssignments = await fetchAssignments(route.params.company);
            setAssignments(fetchedAssignments);
            if ( fetchedAssignments.length > 3 ) {
                performRandomSituation();
            }
        }

        loadAssignments();
    }, []);

    function mainMenuPress() {
        navigation.navigate("MainMenuScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    function performRandomSituation() {
        var number = Math.floor(Math.random() * 10);
        var assignments = fetchAssignments(route.params.company);
        if ( number % 2 === 0 ) {
            // Vehicle broke down so delete assignment automatically. 
            var positionInArray = Math.floor(Math.random() * assignments.length);
            var assignmentToRemove = assignments.at(positionInArray);
            deleteAssignment(assignmentToRemove.routeNumber, assignmentToRemove.tourNumber, assignmentToRemove.company).then(
                Alert.alert('Problem', 'Vehicle running ' + assignmentToRemove.routeNumber + '/' + assignmentToRemove.tourNumber + ' broke down and is no longer assigned!')
            );
        } else {
            // Choose a random position in the array.
            var positionInArray = Math.floor(Math.random() * assignments.length);
            var routeNumber = assignments.at(positionInArray).routeNumber;
            var scenarioName = assignments.at(positionInArray).scenarioName;
            var companyName = assignments.at(positionInArray).company;
            // For this position, generate an additional tour number for this route.
            var tourNumber = getNumberTours(routeNumber, scenarioName) + 1;
            insertAdditionalTour(new AdditionalTour(routeNumber, tourNumber, scenarioName, companyName)).then(
                Alert.alert('Congestion', 'Route ' + routeNumber + ' is crowded! Adding additional tour ' + tourNumber + ' to increase capacity!')
            );
        }
    }

    function getNumberTours(routeNumber, scenarioName) {
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
        fetchAdditionalTours(route.params.company).forEach((additionalTour) => {
            if ( additionalTour.split('/')[0] === routeNumber ) {
                numberTours++;
            }
        })
        return numberTours;
    }

    console.log(assignments.length + ' for company ' + route.params.company);

    if ( assignments.length > 0) {
        return (
            <View style={styles.bodyContainer}>
                <AssignmentList items={assignments} companyName={route.params.company} scenarioName={route.params.scenarioName} />
                <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                    <Text style={styles.buttonText}>Main Menu</Text>
                </TouchableOpacity>
            </View>
        );
    } else {
        return (<View style={styles.bodyContainer}>
            <Text style={styles.noAssignmentText}>There are currently no assignments.</Text>
            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                    <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
            </View>)
    }
    
}

export default ChangeAssignmentScreen;

const styles = StyleSheet.create({
    bodyContainer: {
        flex: 4,
        width: '100%',
        alignItems: 'center',
        backgroundColor: '#f2ffe6',
    },
    noAssignmentText: {
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
});