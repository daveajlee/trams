import { LANDUFF_NAME, LANDUFF_ROUTES } from "../scenarios/landuff-scenario";
import { LONGTS_NAME, LONGTS_ROUTES } from "../scenarios/longts-scenario";
import { MDORF_ROUTES, MDORF_NAME } from "../scenarios/mdorf-scenario";
import { Appearance, ScrollView, StyleSheet, Text } from "react-native";
import RouteDetails from "../components/RouteDetails";
import { TouchableOpacity } from "react-native";
import { useNavigation } from '@react-navigation/native';

type RouteDetailScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
}

function RouteDetailScreen({route}: RouteDetailScreenProps) {

    const colorScheme = Appearance.getColorScheme();

    const routeNumber = route.params.routeNumber;

    const navigation = useNavigation<NavigationStackParams>();

    

    var selectedRoute;
    if ( route.params.scenarioName === LANDUFF_NAME) {
        selectedRoute = LANDUFF_ROUTES.find((route) => route.number === routeNumber)
        console.log(selectedRoute);
    }
    else if ( route.params.scenarioName === MDORF_NAME) {
        selectedRoute = MDORF_ROUTES.find((route) => route.number === routeNumber)
    }
    else if ( route.params.scenarioName === LONGTS_NAME) {
        selectedRoute = LONGTS_ROUTES.find((route) => route.number === routeNumber)
    }

    function routeOverviewPress() {
        navigation.navigate("RouteScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    if ( selectedRoute ) {
        return <ScrollView contentContainerStyle={[styles.rootContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <RouteDetails route={selectedRoute}/>
            <TouchableOpacity style={styles.button} onPress={routeOverviewPress}>
                <Text style={styles.buttonText}>Routes</Text>
            </TouchableOpacity>
        </ScrollView>
    }
    else {
        return <ScrollView contentContainerStyle={[styles.rootContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <Text style={[styles.noRouteText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Did not find any route for the specified route number: {route.params.routeNumber}</Text>
            <TouchableOpacity style={styles.button} onPress={routeOverviewPress}>
                <Text style={styles.buttonText}>Routes</Text>
            </TouchableOpacity>
        </ScrollView>
    }
}

export default RouteDetailScreen;

const styles = StyleSheet.create({
    rootContainer: {
        marginBottom: 32,
        flex: 1,
        alignItems: "center",
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