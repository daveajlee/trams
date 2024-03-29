import { Appearance, Dimensions, Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { setScenarioNameForGame } from "../utilities/sqlite";

/**
 * This component displays details of a particular scenario that
 * the user may play.
 */
function ScenarioCard ({image, title, description, targets, navigation, companyName }) {

    const win = Dimensions.get('window');
    const ratio = win.width/800; // 800 is actual width of image. 
    const colorScheme = Appearance.getColorScheme();

    /**
     * Save the scenario that the user wishes to play.
     * @param {string} scenarioName 
     */
    async function selectScenarioHandler(scenarioName) {
        setScenarioNameForGame(companyName, scenarioName.title);
        navigation.navigate("MainMenuScreen", {
            company: companyName,
            scenarioName: scenarioName.title
        });
    }

    return ( 
        <>
        <View style={styles.scenarioContainer}>
            <Image style={{marginTop: 10, width: win.width, height: 398 * ratio}} source={image}/>
            <Text style={[styles.scenarioTitle, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{title}</Text>
            <Text style={[styles.scenarioDesc, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{description}</Text>
            <Text style={[styles.scenarioTargets, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Targets</Text>
            <Text style={[styles.scenarioTarget, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{targets}</Text>
            <View style={styles.buttonContainer}>
                <TouchableOpacity style={styles.button} onPress={selectScenarioHandler.bind(this, {title})}>
                    <Text style={styles.buttonText}>Select</Text>
                </TouchableOpacity>
            </View>
        </View>
        <View style={{
            borderColor:'black',
            borderWidth: StyleSheet.hairlineWidth,
            alignSelf:'stretch',
            width: "100%"
        }}/>
        </> 
    )

}

export default ScenarioCard;

const styles = StyleSheet.create({
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
    },
    scenarioContainer: {
        marginTop: 10,
        alignItems: 'center',
        justifyContent: 'center',
    },
    scenarioTitle: {
        marginTop: 10,
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    scenarioDesc: {
        marginTop: 5,
        marginLeft: 5,
        marginRight: 5,
        fontSize: 18,
        textAlign: 'center',
    },
    scenarioTargets: {
        marginTop: 10,
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    scenarioTarget: {
        marginTop: 5,
        fontSize: 18,
        textAlign: 'center',
    },
    buttonContainer: {
        marginTop: 20,
        flexDirection: 'row'
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