import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useLayoutEffect } from "react";
import IconButton from "../utilities/IconButton";
import { deleteGame, fetchGames } from "../utilities/sqlite";

function MainMenuScreen({navigation, route}) {

    useLayoutEffect(() => {
        navigation.setOptions({
          headerRight: ({tintColor}) => (
            <IconButton icon="trash-outline" size={24} color={tintColor} onPress={onDeleteGame}/>
          ),
        });
      }, [navigation]); // pass method directly here

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

    /**
     * If the current game is the only game remaining then back to create game otherwise load game menu.
     */
    async function onDeleteGame() {
        await deleteGame(route.params.company);
        if ( fetchGames().length > 0 ) {
            navigation.navigate("LoadGameScreen");
        } else {
            navigation.navigate("CreateGameScreen");
        }
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