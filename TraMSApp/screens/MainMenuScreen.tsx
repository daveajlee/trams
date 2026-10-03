import { Alert, Appearance, Pressable, ScrollView, StyleSheet, Text, View } from "react-native";
import { useEffect } from "react";
import IconButton from "../utilities/IconButton";
import { deleteGame, fetchGames } from "../utilities/sqlite";
import { useNavigation } from '@react-navigation/native';
import {SafeAreaView} from "react-native-safe-area-context";
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

    async function onDriverPress() {
        navigation.navigate("DriverScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    async function onMessagePress() {
        navigation.navigate("MessagesScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    async function onScenarioPress() {
        navigation.navigate("ScenarioScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    async function onSimulationPress() {
        navigation.navigate("LiveSituationScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    return (
        <SafeAreaView style={styles.centeredView}>
        <ScrollView contentContainerStyle={styles.container}>
          {/*<View style={styles.infoContainer}>
            <View style={styles.titleContainer}>
              <IconButton icon="information-circle" size={36} color="black"/>
              <Text style={styles.balanceText}>{t('balance')}:</Text>
              <Text style={styles.balanceText}>{balance}€</Text>
            </View>
            <View style={styles.notesContainer}>
              <View style={styles.noteContainer}>
                <Text style={[styles.noteText, styles.fiveColour]}>5</Text>
                <Text style={styles.amount}>{fiveAmount}</Text>
              </View>
              <View style={styles.noteContainer}>
                <Text style={[styles.noteText, styles.tenColour]}>10</Text>
                <Text style={styles.amount}>{tenAmount}</Text>
              </View>
            </View>
            <View style={styles.notesContainer}>
              <View style={styles.noteContainer}>
                <Text style={[styles.noteText, styles.twentyColour]}>20</Text>
                <Text style={styles.amount}>{twentyAmount}</Text>
              </View>
              <View style={styles.noteContainer}>
                <Text style={[styles.noteText, styles.fiftyColour]}>50</Text>
                <Text style={styles.amount}>{fiftyAmount}</Text>
              </View>
            </View>
            <View style={styles.notesContainer}>
              <View style={styles.noteContainer}>
                <Text style={[styles.noteText, styles.hundredColour]}>100</Text>
                <Text style={styles.amount}>{hundredAmount}</Text>
              </View>
            </View>
          </View> */}
          
          
          <View style={styles.menuContainer}>
                <View style={styles.menuButtonLeft}>
                    <Pressable onPress={onDriverPress}>
                        <IconButton icon="person-outline" size={48} color="black" onPress={onDriverPress}/>
                        <Text style={styles.textStyle}>{"Drivers"}</Text>
                    </Pressable>
                </View>
                <View style={styles.menuButtonRight}>
                    <Pressable onPress={onFleetPress}>
                        <IconButton icon="subway-sharp" size={48} color="black" onPress={onFleetPress}/>
                        <Text style={styles.textStyle}>{"Fleet"}</Text>
                    </Pressable>     
                </View>
          </View>
          <View style={styles.menuContainer}>
                <View style={styles.menuButtonLeft}>
                    <Pressable onPress={onSimulationPress}>
                        <IconButton icon="film-outline" size={48} color="black" onPress={onSimulationPress}/>
                        <Text style={styles.textStyle}>{"Live Situation"}</Text>
                    </Pressable>
                </View>
                <View style={styles.menuButtonRight}>
                    <Pressable onPress={onMessagePress}>
                        <IconButton icon="mail-outline" size={48} color="black" onPress={onMessagePress}/>
                        <Text style={styles.textStyle}>{"Messages"}</Text>
                    </Pressable>
                </View>
          </View>
          <View style={styles.menuContainer}>
                <View style={styles.menuButtonLeft}>
                    <Pressable onPress={onRoutePress}>
                        <IconButton icon="swap-horizontal" size={48} color="black" onPress={onRoutePress}/>
                        <Text style={styles.textStyle}>{"Routes"}</Text>
                    </Pressable>
                </View>
                <View style={styles.menuButtonRight}>
                    <Pressable onPress={onScenarioPress}>
                        <IconButton icon="location-outline" size={48} color="black" onPress={onScenarioPress}/>
                        <Text style={styles.textStyle}>{"Scenario"}</Text>
                    </Pressable>
                </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    )

}

export default MainMenuScreen;

const styles = StyleSheet.create({
    centeredView: {
        flex: 1,
        width: '100%',
        alignItems: 'center',
        justifyContent: 'center'
  },
  container: {
    color: 'black',
    alignItems: 'center',
  },
  infoContainer: {
    borderRadius: 25,
    backgroundColor: '#f2e9e9',
    width: '90%',
    marginBottom: '10%',
    borderStyle: 'solid',
    borderWidth: 3,
    borderColor: 'black'
  },
  balanceText: {
    color: 'black',
    marginLeft: 10,
    fontSize: 24,
    fontWeight: "bold"
  },
  titleContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    justifyContent: 'center',
    marginTop: 5,
  },
  menuContainer: {
    flexDirection: 'row',
    marginTop: 10,
  },
  menuButtonLeft: {
    flexDirection: 'column',
    width: '45%',
    borderRadius: 25,
    backgroundColor: '#c2f3ce',
    padding: 10,
    marginBottom: 10,
    marginRight: 10,
  },
  menuButtonRight: {
    flexDirection: 'column',
    width: '45%',
    borderRadius: 25,
    backgroundColor: '#c2f3ce',
    padding: 10,
    marginLeft: 10,
    marginBottom: 10
  },
  notesContainer: {
    flexDirection: 'row',
    marginLeft: 20,
    marginBottom: 10,
  },
  noteContainer: {
    flexDirection: 'row'
  },
  noteText: {
    alignItems: 'center',
    width: '30%',
    padding: 0,
    marginTop: 10,
    height: 35,
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 24,
    color: 'white'
  },
  darkModeText: {
    color: 'black',
  },
  lightModeText: {
    color: 'black',
  },
  stepContainer: {
    gap: 24,
    marginBottom: 24,
    flexDirection: 'row',
  },
  logo: {
    height: '100%',
    width: '100%',
    bottom: 0,
    left: 0,
    position: 'absolute',
  },
  fiveColour: {
    backgroundColor: 'gray',
  },
  tenColour: {
    backgroundColor: 'red',
  },
  twentyColour: {
    backgroundColor: 'blue',
  },
  fiftyColour: {
    backgroundColor: 'orange',
  },
  hundredColour: {
    backgroundColor: 'green',
  },
  amount: {
    textAlign: 'center',
    fontWeight: 'bold',
    color: 'black',
    fontSize: 24,
    marginTop: 10,
    width: 75,
  },
  buttonText: {
    color: 'white',
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
  },
  buttonContainer: {
    flexDirection: 'row',
    marginTop: 10,
    marginBottom: 20,
  },
  button: {
        borderRadius: 20,
        padding: 10,
        elevation: 2,
        marginRight: 10,
        marginLeft: 10,
        height: 50,
        width: '40%',  
        backgroundColor: '#f2d6d3ff'
  },
  textStyle: {
        color: 'black',
        fontWeight: 'bold',
        textAlign: 'center',
        fontSize: 20,
        marginTop: 10
  },
})