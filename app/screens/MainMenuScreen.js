import { Alert, Appearance, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useLayoutEffect } from "react";
import IconButton from "../utilities/IconButton";
import { deleteGame, fetchGames } from "../utilities/sqlite";

function MainMenuScreen({navigation, route}) {

    const colorScheme = Appearance.getColorScheme();

    useLayoutEffect(() => {
        navigation.setOptions({
            headerLeft: ({tintColor}) => (
                <IconButton icon="trash-outline" size={24} color={tintColor} onPress={onDeleteGame}/>
            ),
            headerRight: ({tintColor}) => (
                <>
                <IconButton icon="add" size={24} color={tintColor} onPress={onCreateGame}/>
                <IconButton icon="apps" size={24} color={tintColor} onPress={onLoadGame}/>
                </>
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
        Alert.alert(
            'Delete ' + route.params.company,
            'Are you sure you want to delete this transport company?',
            [
              {text: 'Yes', onPress: async () => {
                await deleteGame(route.params.company);
                if ( fetchGames().length > 0 ) {
                    navigation.navigate("LoadGameScreen");
                } else {
                    navigation.navigate("CreateGameScreen");
                }
              }},
              {text: 'No', onPress: async () => {
                // Do nothing if no is clicked.
              }},
            ],
            {cancelable: true},
          );
       
    }

    function onCreateGame() {
        navigation.navigate("CreateGameScreen");
    }

    function onLoadGame() {
        navigation.navigate("LoadGameScreen");
    }

    return (
        <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <View style={styles.headerContainer}>
                <Text style={[styles.header, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Company: {route.params.company}</Text>
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
    container: {
        flex: 1,
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