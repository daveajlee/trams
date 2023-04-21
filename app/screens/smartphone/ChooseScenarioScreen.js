import { ScrollView, StyleSheet, Text, View } from "react-native";
import ScenarioCard from "../../components/ScenarioCard";

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

    return (
        <ScrollView style={styles.container} contentContainerStyle={{alignItems: 'center',
        justifyContent: 'center'}}>
            <View style={styles.headerContainer}>
                <Text style={styles.headerText}>Congratulations - {playerName} has been appointed Managing Director of {companyName}!</Text>
                <Text style={styles.headerText2}>Choose your scenario and prove that your new company can make public transport run on time!</Text>
            </View>
            <ScenarioCard image={landuffImage} title="Landuff" description="Landuff Town is a small town with a very friendly town council. They want to work with you in providing an efficient and effective transport service for Landuff Town."
                targets="Serve all bus stops in Landuff. // Ensure a frequent service on all routes. // Ensure that passenger satisfaction remains above 70% at all times." navigation={navigation} companyName={companyName}/>
            <ScenarioCard image={longtsImage} title="Longts City" description="Longts City is a very large city. The city council are suspicious of your new company and you will need to impress them very quickly in order to establish a good working relationship."
                targets="Serve all bus stops in Longts. // Ensure a very frequent service on all routes. // Ensure that passenger satisfaction remains above 50% at all times." navigation={navigation} companyName={companyName}/>
            <ScenarioCard image={mdorfImage} title="MDorf" description="Millenium Dorf City is a small city. The city council are prepared to work with you providing that you can meet their targets within their timescales."
                targets="Serve all bus stops in MDorf. // Ensure a frequent service on all routes. // Ensure that passenger satisfaction remains above 35% at all times." navigation={navigation} companyName={companyName}/>
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
    container: {
        flex: 1,
        backgroundColor: '#f2ffe6',
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