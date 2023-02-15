import { StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import { useState } from "react";
import DropDownPicker from "react-native-dropdown-picker";
import DatePicker from "react-native-date-picker";
import { Game } from "../../models/game";
import { insertGame } from "../../utilities/sqlite";

/**
 * This screen represents the create game screen on a Smartphone.
 * It enables the user to enter a company and player name as well
 * as choose their desired skill level and start date.
 * Clicking the create button, creates a game in the database.
 */
function CreateGameScreen() {

    const [companyName, setCompanyName] = useState('');
    const [playerName, setPlayerName] = useState('');
    const [levelValue, setLevelValue] = useState(null);
    const [openLevelDropdown, setOpenLevelDropdown] = useState(false);
    const [levelItems, setLevelItems] = useState([
        {label: 'Easy', value: 'easy'},
        {label: 'Intermediate', value: 'intermediate'},
        {label: 'Hard', value: 'hard'},
      ]);
    const [startDate, setStartDate] = useState(new Date());

    /**
     * Set the company name that the user entered.
     * @param {string} enteredText the text that the user entered in the company name field.
     */
    function companyNameInputHandler(enteredText) {
        setCompanyName(enteredText);
    }

    /**
     * Set the player name that the user entered.
     * @param {string} enteredText the text that the user entered in the player name field.
     */
    function playerNameInputHandler(enteredText) {
        setPlayerName(enteredText);
    }

    /**
     * Create the game based on the information provided by the user.
     */
    function createGameHandler() {
        const game = new Game(companyName, playerName, levelValue, startDate.toLocaleString());
        insertGame(game).then(
            console.log('Game created successfully!')
        )
    }

    /**
     * Display the screen with a title and fields to enter company name,
     * player name, skill level and start date.
     */
    return (
        <View style={styles.container}>
            <View style={styles.headerContainer}>
                <Text style={styles.headerText}>Welcome to TraMS</Text>
            </View>
            <View style={styles.bodyContainer}>
                <View style={styles.companyNameContainer}>
                    <Text style={styles.bodyText}>Company Name:</Text>
                    <TextInput style={styles.textInput} placeholder='Your Company Name' onChangeText={companyNameInputHandler} value={companyName}/>
                </View>
                <View style={styles.playerNameContainer}>
                    <Text style={styles.bodyText}>Player Name:</Text>
                    <TextInput style={styles.textInput} placeholder='Your Name' onChangeText={playerNameInputHandler} value={playerName}/>
                </View>
                <View style={styles.levelContainer}>
                    <Text style={styles.bodyText}>Level:</Text>
                    <DropDownPicker open={openLevelDropdown} value={levelValue} items={levelItems}
                        setOpen={setOpenLevelDropdown} setValue={setLevelValue} setItems={setLevelItems}/>
                </View>
                <View style={styles.dateContainer}>
                    <Text style={styles.bodyText}>Start Date & Time:</Text>
                    <DatePicker date={startDate} onDateChange={setStartDate} />
                </View>
            </View>
            <View style={styles.buttonContainer}>
                <TouchableOpacity style={styles.button} onPress={createGameHandler}>
                    <Text style={styles.buttonText}>Create Game</Text>
                </TouchableOpacity>
            </View>
        </View>
    );

}

export default CreateGameScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f2ffe6',
        height: '100%',
        alignItems: 'center',
        justifyContent: 'center',
    },
    headerContainer: {
        paddingTop: 50
    },
    headerText: {
        fontSize: 32,
        fontWeight: 'bold',
        textAlign: 'center'
    },
    bodyContainer: {
        paddingTop: 20,
        width: '100%',
        alignItems: 'center',
        justifyContent: 'center'
    },
    bodyText: {
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
        paddingBottom: 16
    },
    buttonContainer: {
        marginTop: 20,
        flexDirection: 'row'
    },
    textInput: {
        borderWidth: 1,
        borderColor: '#e4d0ff',
        backgroundColor: 'white',
        color: '#120438',
        borderRadius: 6,
        width: '100%',
        padding: 8
    },
    companyNameContainer: {
        flexDirection: 'column',
        width: '80%',
    },
    playerNameContainer: {
        flexDirection: 'column',
        width: '80%',
        marginTop: 20
    },
    levelContainer: {
        flexDirection: 'column',
        width: '80%',
        marginTop: 20,
        marginBottom: 100
    },
    dateContainer: {
        flexDirection: 'column',
        width: '80%',
        marginTop: 20,
        alignItems: 'center',
        justifyContent: 'center'
    },
    button: {
        alignItems: "center",
        backgroundColor: "#5e7947",
        width: '90%',
        padding: 10,
        marginBottom: 30,
    },
    buttonText: {
        color: 'white',
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
    }
});