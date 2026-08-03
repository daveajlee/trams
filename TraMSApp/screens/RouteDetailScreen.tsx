import { LANDUFF_NAME, LANDUFF_ROUTES } from "../scenarios/landuff-scenario";
import { LONGTS_NAME, LONGTS_ROUTES } from "../scenarios/longts-scenario";
import { MDORF_ROUTES, MDORF_NAME } from "../scenarios/mdorf-scenario";
import { Appearance, ScrollView, StyleSheet, Text } from "react-native";
import RouteDetails from "../components/RouteDetails";
import { Alert, TouchableOpacity, View } from "react-native";
import { useNavigation } from '@react-navigation/native';
import Route from "../models/route";
import { useEffect, useState } from "react";
import IconButton from "../utilities/IconButton";

type RouteDetailScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function RouteDetailScreen({route}: RouteDetailScreenProps) {

    const colorScheme = Appearance.getColorScheme();
    const navigation = useNavigation<NavigationStackParams>();
    const [selectedRoute, setSelectedRoute] = useState<Route>();

    useEffect(() => {
        navigation.setOptions({
            headerRight: () => <View style={{marginLeft: 10, flexDirection: 'row'}}>             
                <IconButton icon="trash" size={24} color="black" onPress={onDeleteRoute}/>
                </View>,
        });

        async function onDeleteRoute() {
            Alert.alert("Coming Soon!", "Not yet available!");
        }
    
        async function loadSelectedRoute() {
            switch (route.params.scenarioName) {
                case LANDUFF_NAME:
                    setSelectedRoute(LANDUFF_ROUTES.find((lRoute) => lRoute.number === route.params.routeNumber));
                    break;
                case MDORF_NAME:
                    setSelectedRoute(MDORF_ROUTES.find((mRoute) => mRoute.number === route.params.routeNumber));
                    break;
                case LONGTS_NAME:
                    setSelectedRoute(LONGTS_ROUTES.find((lRoute) => lRoute.number === route.params.routeNumber));
                    break;
                default:
                    Alert.alert('Error', 'Unknown scenario: ' + route.params.scenarioName);
            }
        }
    
        loadSelectedRoute();
    }, [route.params.scenarioName, route.params.routeNumber, navigation, route.params.company]);

    function routeOverviewPress() {
        navigation.navigate("RouteScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    if ( selectedRoute ) {
        return <ScrollView contentContainerStyle={[styles.rootContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <RouteDetails route={selectedRoute} companyName={route.params.company} scenarioName={route.params.scenarioName} />
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