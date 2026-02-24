import { Appearance, View, StyleSheet, Text, Alert } from "react-native";
import { useState } from "react";
import { useNavigation } from '@react-navigation/native';
import Route from "../../models/route";
import { useEffect } from "react";
import { LANDUFF_NAME } from "../../scenarios/landuff-scenario";
import { MDORF_NAME } from "../../scenarios/mdorf-scenario";
import { LONGTS_NAME } from "../../scenarios/longts-scenario";
import { LANDUFF_ROUTES } from "../../scenarios/landuff-scenario";
import { MDORF_ROUTES } from "../../scenarios/mdorf-scenario";
import { LONGTS_ROUTES } from "../../scenarios/longts-scenario";
import RouteButton from "../../components/RouteButton";
import { TouchableOpacity } from "react-native";

type RouteScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function RouteScreen({route}: RouteScreenProps) {

    const navigation = useNavigation<NavigationStackParams>();
    const colorScheme = Appearance.getColorScheme();

    const [routes, setRoutes] = useState<Route[]>([]);

    useEffect(() => {
        navigation.setOptions({
            title: route.params.company + ' - Routes',
        });

        async function loadRoutes() {
            switch (route.params.scenarioName) {
                case LANDUFF_NAME:
                    setRoutes(LANDUFF_ROUTES);
                    break;
                case MDORF_NAME:
                    setRoutes(MDORF_ROUTES);
                    break;
                case LONGTS_NAME:
                    setRoutes(LONGTS_ROUTES);
                    break;
                default:
                    Alert.alert('Error', 'Unknown scenario: ' + route.params.scenarioName);
            }
        }

        loadRoutes();
    }, [route.params.scenarioName, navigation, route.params.company]);

    function onSearchRoutePress(routeNumber: string) {
        navigation.navigate("RouteDetailScreen", {
            routeNumber: routeNumber,
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

    return (
        <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <View style={styles.bodyContainer}>
                <View style={styles.row}>
                    {routes.map((route) => (
                        <RouteButton routeNumber={route.number} from={route.outwardTerminus} to={route.returnTerminus} onPress={onSearchRoutePress.bind(null, route.number)}/>
                    ))}
                </View>

                <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                    <Text style={styles.buttonText}>Main Menu</Text>
                </TouchableOpacity>
            </View>
        </View>
    );

}

export default RouteScreen;

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
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
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