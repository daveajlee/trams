import { LANDUFF_ROUTES, MDORF_ROUTES, LONGTS_ROUTES, LANDUFF_VEHICLES, MDORF_VEHICLES, LONGTS_VEHICLES } from "../data/scenario-data";
import { ScrollView, StyleSheet, Text } from "react-native";
import RouteDetails from "../components/RouteDetails";

function RouteScreen(props) {

    const routeNumber = props.route.params.routeNumber;

    var selectedRoute;
    if ( props.route.params.scenarioName === 'Landuff') {
        selectedRoute = LANDUFF_ROUTES.find((route) => route.number === routeNumber)
    }
    else if ( props.route.params.routeDatabase === 'MDorf') {
        selectedRoute = MDORF_ROUTES.find((route) => route.number === routeNumber)
    }
    else if ( props.route.params.routeDatabase === 'Longts') {
        selectedRoute = LONGTS_ROUTES.find((route) => route.number === routeNumber)
    }

    if ( selectedRoute ) {
        return <ScrollView style={styles.rootContainer}>
            <RouteDetails number={selectedRoute.number} outwardTerminus={selectedRoute.outwardTerminus} returnTerminus={selectedRoute.returnTerminus}
            numberTours={selectedRoute.numberTours}/>
        </ScrollView>
    }
    else {
        return <ScrollView style={styles.rootContainer}>
            <Text>Did not find any route for the specified route number: {props.route.params.routeNumber}</Text>
        </ScrollView>
    }
}

export default RouteScreen;

const styles = StyleSheet.create({
    rootContainer: {
        marginBottom: 32,
        flex: 1,
        backgroundColor: '#f2ffe6',
    }
})