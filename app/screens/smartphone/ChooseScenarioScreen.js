import { Appearance, ScrollView, StyleSheet, Text, View } from "react-native";
import ScenarioCard from "../../components/ScenarioCard";
import { LANDUFF_DESCRIPTION, LANDUFF_NAME, LANDUFF_TARGETS } from "../../scenarios/landuff-scenario";
import { LONGTS_DESCRIPTION, LONGTS_NAME, LONGTS_TARGETS } from "../../scenarios/longts-scenario";
import { MDORF_DESCRIPTION, MDORF_NAME, MDORF_TARGETS } from "../../scenarios/mdorf-scenario";

const landuffImage = require("./../../assets/landuff-map-picture.jpg");
const longtsImage = require("./../../assets/longts-map-picture.jpg");
const mdorfImage = require("./../../assets/mdorf-map-picture.jpg")

/**
 * This screen represents the choose scenario screen on a Smartphone.
 * It enables the user to choose the scenario they wish to play
 * from the currently available scenarios.
 */
function ChooseScenarioScreen({route, navigation}) {

    var playerName = route.params.playerName;
    var companyName = route.params.companyName;
    const colorScheme = Appearance.getColorScheme();

    /**
     * Return the components to be displayed to the user.
     */
    return (
        <ScrollView style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]} contentContainerStyle={{alignItems: 'center',
        justifyContent: 'center'}}>
            <View style={styles.headerContainer}>
                <Text style={[styles.headerText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Congratulations - {playerName} has been appointed Managing Director of {companyName}!</Text>
                <Text style={[styles.headerText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Choose your scenario and prove that your new company can make public transport run on time!</Text>
            </View>
            <ScenarioCard image={landuffImage} title={LANDUFF_NAME} description={LANDUFF_DESCRIPTION} targets={LANDUFF_TARGETS} navigation={navigation} companyName={companyName}/>
            <ScenarioCard image={longtsImage} title={LONGTS_NAME} description={LONGTS_DESCRIPTION} targets={LONGTS_TARGETS} navigation={navigation} companyName={companyName}/>
            <ScenarioCard image={mdorfImage} title={MDORF_NAME} description={MDORF_DESCRIPTION} targets={MDORF_TARGETS} navigation={navigation} companyName={companyName}/>
            <View style={{
                borderColor:'black',
                borderWidth: StyleSheet.hairlineWidth,
                alignSelf:'stretch',
                width: "100%"
            }}/>
        </ScrollView>
    );
}

export default ChooseScenarioScreen;

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
        height: '100%',
    },
    headerContainer: {
        paddingTop: 20,
        paddingBottom: 20
    },
    headerText: {
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center',
        marginLeft: 5,
        marginRight: 5
    },
    headerText2: {
        marginTop: 5,
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center',
        marginLeft: 5,
        marginRight: 5
    },
    image: {
        marginTop: 10
    },
});