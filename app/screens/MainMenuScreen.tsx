import { Alert, Appearance, StyleSheet, View } from "react-native";
import { useEffect } from "react";
import IconButton from "../utilities/IconButton";
import { deleteGame, fetchGames } from "../utilities/sqlite";
import { useNavigation } from '@react-navigation/native';
import IconTextButton from "../components/IconTextButton";

type MainMenuScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function MainMenuScreen({route}: MainMenuScreenProps) {

    const colorScheme = Appearance.getColorScheme();
    const navigation = useNavigation<NavigationStackParams>();

    useEffect(() => {
        navigation.setOptions({
            title: route.params.company,
            headerRight: () => <View style={{marginLeft: 10, flexDirection: 'row'}}>             
                <IconButton icon="add" size={24} color="black" onPress={onCreateGame}/>
                <IconButton icon="apps" size={24} color="black" onPress={onLoadGame}/>
                <IconButton icon="trash-outline" size={24} color="black" onPress={onDeleteGame}/>
                </View>,
        });

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
                if ( (await fetchGames()).length > 0 ) {
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

      }, [navigation, route.params.company]); // pass method directly here

    function onAssignPress() {
        navigation.navigate("AllocateScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    function onRoutePress() {
        navigation.navigate("RouteScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    function onFleetPress() {
        navigation.navigate("FleetScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    return (
        <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <View style={styles.bodyContainer}>
                <View style={styles.row}>
                    <IconTextButton icon="code" text="Allocate" onPress={onAssignPress}/>
                    <IconTextButton icon="subway-sharp" text="Fleet" onPress={onFleetPress}/>
                    <IconTextButton icon="swap-horizontal" text="Routes" onPress={onRoutePress}/>
                </View>
                {/*<TouchableOpacity style={styles.button} onPress={onAssignPress}>
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
                </TouchableOpacity>*/}
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
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        width: '100%',
    }
})