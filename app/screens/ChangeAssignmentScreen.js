import { useContext, useEffect } from "react";
import { AssignContext } from "../store/context/assign-context";
import { Alert, StyleSheet, Text, View } from "react-native";
import AssignmentList from "../components/AssignmentList";
import { LANDUFF_ROUTES, MDORF_ROUTES, LONGTS_ROUTES, LANDUFF_VEHICLES, MDORF_VEHICLES, LONGTS_VEHICLES } from "../data/scenario-data";
import { deleteAssignment, insertAdditionalTour } from "../utilities/sqlite";
import AdditionalTour from "../models/additionalTour";

function ChangeAssignmentScreen({route, navigation}) {

    const assignContext = useContext(AssignContext);

    useEffect(() => {
        if ( assignContext.savedAssignments.length > 3 ) {
            performRandomSituation();
        }
    }, []);

    function performRandomSituation() {
        var number = Math.floor(Math.random() * 10);
        if ( number % 2 === 0 ) {
            // Vehicle broke down so delete assignment automatically. 
            var positionInArray = Math.floor(Math.random() * assignContext.savedAssignments.length);
            var assignmentToRemove = assignContext.savedAssignments.at(positionInArray);
            assignContext.removeAssignment(assignmentToRemove.routeNumber, assignmentToRemove.tourNumber);
            deleteAssignment(assignmentToRemove.routeNumber, assignmentToRemove.tourNumber, assignmentToRemove.company).then(
                Alert.alert('Problem', 'Vehicle running ' + assignmentToRemove.routeNumber + '/' + assignmentToRemove.tourNumber + ' broke down and is no longer assigned!')
            );
            
        } else {
            // Choose a random position in the array.
            var positionInArray = Math.floor(Math.random() * assignContext.savedAssignments.length);
            var routeNumber = assignContext.savedAssignments.at(positionInArray).routeNumber;
            var routeDatabase = assignContext.savedAssignments.at(positionInArray).routeDatabase;
            var vehicleDatabase = assignContext.savedAssignments.at(positionInArray).vehicleDatabase;
            var companyName = assignContext.savedAssignments.at(positionInArray).company;
            // For this position, generate an additional tour number for this route.
            var tourNumber = getNumberTours(routeNumber, routeDatabase) + 1;
            assignContext.addAdditionalTour(routeNumber, tourNumber);
            insertAdditionalTour(new AdditionalTour(routeNumber, tourNumber, routeDatabase, vehicleDatabase, companyName)).then(
                Alert.alert('Congestion', 'Route ' + routeNumber + ' is crowded! Adding additional tour ' + tourNumber + ' to increase capacity!')
            );
        }
    }

    function getNumberTours(routeNumber, routeDatabase) {
        var numberTours = 0;
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
            if ( additionalTour.split('/')[0] === routeNumber ) {
                numberTours++;
            }
        })
        return numberTours;
    }

    if ( assignContext.savedAssignments.length === 0 ) {
        return (<View style={styles.bodyContainer}>
            <Text>There are currently no assignments.</Text>
            </View>)
    } else {
        
        return (
            <View style={styles.bodyContainer}>
                <AssignmentList items={assignContext.savedAssignments} companyName={route.params.company} routeDatabase={route.params.routes} vehicleDatabase={route.params.vehicles} />
            </View>
        );
    }
    
}

export default ChangeAssignmentScreen;

const styles = StyleSheet.create({
    bodyContainer: {
        flex: 4,
        width: '100%',
        alignItems: 'center',
        backgroundColor: '#f2ffe6',
    }
});