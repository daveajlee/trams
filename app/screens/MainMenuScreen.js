import { Alert, StyleSheet, Text, TouchableOpacity, View } from "react-native";

function MainMenuScreen({navigation, route}) {

    function onAssignPress() {
        navigation.navigate("AssignTourScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    function onChangePress() {
        navigation.navigate("ChangeAssignmentScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    function onSearchRoutePress() {
        navigation.navigate("SearchRouteScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    function onSearchFleetPress() {
        navigation.navigate("SearchFleetScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    function onDisplayFleetPress() {
        navigation.navigate("FleetScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    return (
        <View style={styles.container}>
            <View style={styles.headerContainer}>
                <Text style={styles.header}>Company: {route.params.company}</Text>
            </View>
            <View style={styles.bodyContainer}>
                <TouchableOpacity style={styles.button} onPress={onAssignPress}>
                    <Text style={styles.buttonText}>Assign Allocation</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={onChangePress}>
                    <Text style={styles.buttonText}>Change / Remove Allocation</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={onSearchRoutePress}>
                    <Text style={styles.buttonText}>Search by Route</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={onSearchFleetPress}>
                    <Text style={styles.buttonText}>Search by Fleet</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={onDisplayFleetPress}>
                    <Text style={styles.buttonText}>Display Fleet Info</Text>
                </TouchableOpacity>
            </View>
        </View>
    )

}

export default MainMenuScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f2ffe6',
        alignItems: 'center',
        justifyContent: 'center',
    },
    headerContainer: {
        paddingTop: 10
    },
    header: {
        fontSize: 32,
        fontWeight: 'bold',
        textAlign: 'center'
    },
    bodyContainer: {
        flex: 4,
        width: '100%',
        alignItems: 'center',
        marginTop: 30
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
    }
})