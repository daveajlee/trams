import { LANDUFF_NAME, LANDUFF_ROUTES } from "../scenarios/landuff-scenario";
import { LONGTS_NAME, LONGTS_ROUTES } from "../scenarios/longts-scenario";
import { MDORF_ROUTES, MDORF_NAME } from "../scenarios/mdorf-scenario";
import { ScrollView, StyleSheet, Text } from "react-native";
import RouteDetails from "../components/RouteDetails";
import { TouchableOpacity } from "react-native";

function RouteScreen(props) {

    const routeNumber = props.route.params.routeNumber;

    var selectedRoute;
    if ( props.route.params.scenarioName === LANDUFF_NAME) {
        selectedRoute = LANDUFF_ROUTES.find((route) => route.number === routeNumber)
    }
    else if ( props.route.params.scenarioName === MDORF_NAME) {
        selectedRoute = MDORF_ROUTES.find((route) => route.number === routeNumber)
    }
    else if ( props.route.params.scenarioName === LONGTS_NAME) {
        selectedRoute = LONGTS_ROUTES.find((route) => route.number === routeNumber)
    }

    function mainMenuPress() {
        props.navigation.navigate("MainMenuScreen", {
            company: props.route.params.company,
            scenarioName: props.route.params.scenarioName,
        });
    }

    if ( selectedRoute ) {
        return <ScrollView contentContainerStyle={styles.rootContainer}>
            <RouteDetails number={selectedRoute.number} outwardTerminus={selectedRoute.outwardTerminus} returnTerminus={selectedRoute.returnTerminus}
            numberTours={selectedRoute.numberTours}/>
            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
        </ScrollView>
    }
    else {
        return <ScrollView contentContainerStyle={styles.rootContainer}>
            <Text style={styles.noRouteText}>Did not find any route for the specified route number: {props.route.params.routeNumber}</Text>
            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
        </ScrollView>
    }
}

export default RouteScreen;

const styles = StyleSheet.create({
    rootContainer: {
        marginBottom: 32,
        flex: 1,
        backgroundColor: '#f2ffe6',
        alignItems: "center",
    },
    noRouteText: {
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