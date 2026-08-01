import { Appearance, Image, ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import { useState } from "react";
import { Game } from "../models/game";
//import { fetchGame, insertGame } from "../utilities/sqlite";
import { useNavigation } from '@react-navigation/native';
import IconButton from "../utilities/IconButton";
import LevelModal from "../modals/LevelModal";

type NavigationStackParams = {
  navigate: Function;
}

/**
 * This screen represents the create game screen on a Smartphone.
 * It enables the user to enter a company and player name as well
 * as choose their desired skill level and start date.
 * Clicking the create button, creates a game in the database.
 */
function CreateGameScreen() {

    const navigation = useNavigation<NavigationStackParams>();

    const [companyName, setCompanyName] = useState('');
    const [playerName, setPlayerName] = useState('');
    const [levelValue, setLevelValue] = useState('');
    //const [openLevelDropdown, setOpenLevelDropdown] = useState(false);
    /*const [levelItems, setLevelItems] = useState([
        {label: 'Easy', value: 'easy'},
        {label: 'Intermediate', value: 'intermediate'},
        {label: 'Hard', value: 'hard'},
      ]);*/
    const [startDate, setStartDate] = useState(new Date());
    const colorScheme = Appearance.getColorScheme();

    const [modalVisible, setModalVisible] = useState(false);

    /**
     * Set the company name that the user entered.
     * @param {string} enteredText the text that the user entered in the company name field.
     */
    function companyNameInputHandler(enteredText: string) {
        setCompanyName(enteredText);
    }

    /**
     * Set the player name that the user entered.
     * @param {string} enteredText the text that the user entered in the player name field.
     */
    function playerNameInputHandler(enteredText: string) {
        setPlayerName(enteredText);
    }

    /**
     * Create the game based on the information provided by the user
     * and move to the next screen which allows the user to choose the scenario.
     */
    async function createGameHandler() {
        // Create game - if level is not set then use default easy level.
        var game;
        if ( !levelValue ) {
            game = new Game(companyName, playerName, '', 'easy', startDate);
        } else {
            game = new Game(companyName, playerName, '', levelValue, startDate);
        }
        // WHen creating a game, then check if the company already exists then show an alert and do not add.
        //const fetchedGame: Game[] = await fetchGame(companyName);
        /*if ( fetchedGame.length > 0 ) {
            Alert.alert('Duplicate Company', 'Please choose another company name');
        }
        else{
            insertGame(game).then(
                navigation.navigate("ChooseScenarioScreen", {
                    companyName: companyName,
                    playerName: playerName,
                    })
            );            
        }*/ 
    }

    function chooseLevel() {
        setModalVisible(true);
    }

    /**
     * Display the screen with a title and fields to enter company name,
     * player name, skill level and start date.
     */
    return (
        <ScrollView contentContainerStyle={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <View style={styles.headerContainer}>
                <Image style={styles.welcomeLogo}
                    source={require('../assets/images/trams-welcome-logo.png')}
                />
            </View>
            <View style={styles.bodyContainer}>
                <View style={styles.introContainer}>
                    <Text style={[styles.introText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Welcome! You are about to manage your own transport company!
                        What is your name and what is the name of your company?
                        Are you playing for fun or would you like a real challenge?</Text>
                </View>
                <View style={styles.inputContainer}>
                    <View style={styles.textFieldContainer}>
                        <Text style={[styles.fieldText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Company Name:</Text>
                        <TextInput style={colorScheme === 'dark' ? styles.textInputDark : styles.textInputLight} placeholder='Your Company Name' onChangeText={companyNameInputHandler} value={companyName}/>
                    </View>
                    <View style={styles.textFieldContainer}>
                        <Text style={[styles.fieldText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Player Name:</Text>
                        <TextInput style={colorScheme === 'dark' ? styles.textInputDark : styles.textInputLight} placeholder='Your Name' onChangeText={playerNameInputHandler} value={playerName}/>
                    </View>
                    <View style={styles.textFieldContainer}>
                        <Text style={[styles.fieldText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Level:</Text>
                        <Text style={[styles.entryText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{levelValue ? levelValue : 'Easy'}</Text>
                        <IconButton icon="chevron-forward" size={24} color="black" onPress={chooseLevel}/>
                    </View>
                </View>
            </View>
            <View style={styles.buttonContainer}>
                <TouchableOpacity style={styles.button} onPress={createGameHandler}>
                    <Text style={styles.buttonText}>Create Game</Text>
                </TouchableOpacity>
            </View>
            <LevelModal modalVisible={modalVisible} setModalVisible={setModalVisible} />
        </ScrollView>
    );

}

export default CreateGameScreen;

const styles = StyleSheet.create({
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#ecf0e8',
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
    welcomeLogo: {
        width: 512,
        height: 161
    },
    bodyContainer: {
        paddingTop: 20,
        width: '100%',
        alignItems: 'center',
        justifyContent: 'center'
    },
    introContainer: {
        justifyContent: 'space-evenly',
    },
    introText: {
        fontSize: 20,
        fontWeight: 'normal',
        textAlign: 'justify',
        paddingLeft: 15,
        paddingRight: 15,
        paddingBottom: 30
    },
    bodyText: {
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
        paddingBottom: 16
    },
    fieldText: {
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'left',
        width: '45%'
    },
    buttonContainer: {
        marginTop: 40,
        flexDirection: 'row',
    },
    textInputLight: {
        paddingLeft: 10,
        fontSize: 18,
        width: '50%'
    },
    entryText: {
        paddingLeft: 10,
        fontSize: 18,
        width: '40%'
    },
    textInputDark: {
        borderWidth: 1,
        borderColor: 'white',
        backgroundColor: 'black',
        color: 'white',
        borderRadius: 6,
        width: '100%',
        padding: 8
    },
    inputContainer: {
        flexDirection: 'column',
        width: '90%',
        borderRadius: 25,
        backgroundColor: '#b5de90',
        paddingBottom: 10
    },
    textFieldContainer: {
        flexDirection: 'row',
        paddingTop: 20,
        paddingLeft: 10,
        paddingRight: 10
    },
    levelContainer: {
        flexDirection: 'column',
        width: '80%',
        marginTop: 10,
        marginBottom: 10

    },
    button: {
        alignItems: "center",
        backgroundColor: "#5e7947",
        width: '90%',
        padding: 10,
        marginBottom: 30,
        borderRadius: 25
    },
    buttonText: {
        color: 'white',
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
    },
});