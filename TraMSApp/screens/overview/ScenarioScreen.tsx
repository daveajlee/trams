import { useEffect, useState } from "react";
import { Alert, Appearance, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/native";
import IconTextButton from "../../components/IconTextButton";

type ScenarioScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function ScenarioScreen({route}: ScenarioScreenProps) {

    const colorScheme = Appearance.getColorScheme();
    const navigation = useNavigation<NavigationStackParams>();

    useEffect(() => {
        navigation.setOptions({
            title: route.params.company + ' - ' + route.params.scenarioName
        });
    
    }, [route.params.scenarioName, navigation, route.params.company]);

    function onInfoPress() {
        Alert.alert("Coming Soon!", "Not yet available!");
    }

    function onLocationMapPress() {
        Alert.alert("Coming Soon!", "Not yet available!");
    }

    function onStopsPress() {
        Alert.alert("Coming Soon!", "Not yet available!");
    }

    function mainMenuPress() {
        navigation.navigate("MainMenuScreen", {
            scenarioName: route.params.scenarioName,
            company: route.params.company
        });
    }

    return <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
        <View style={styles.bodyContainer}>
            <View style={styles.row}>
                <IconTextButton key={"infoscreen"} icon="information-circle" text={"Information"} onPress={onInfoPress}/>
                <IconTextButton key={"locationmap"} icon="map-outline" text={"Location Map"} onPress={onLocationMapPress}/>
                <IconTextButton key={"stops"} icon="flag-outline" text={"Stops"} onPress={onStopsPress}/>
            </View>

            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
        </View>
    </View>
}

export default ScenarioScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#f2ffe6',
    },
    bodyContainer: {
        flex: 4,
        width: '100%',
        alignItems: 'center',
        marginTop: 30
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        width: '100%',
        flexWrap: 'wrap',
        alignItems: 'flex-start'
    },
    button: {
        alignItems: "center",
        backgroundColor: "#5e7947",
        width: '90%',
        padding: 20,
        marginTop: 30,
        marginBottom: 20,
    },
    buttonText: {
        color: 'white',
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center'
    }
})