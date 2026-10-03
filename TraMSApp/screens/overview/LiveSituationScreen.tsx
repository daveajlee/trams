import { useEffect, useState } from "react";
import { Alert, Appearance, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import Driver from "../../models/driver.ts";
import { useNavigation } from "@react-navigation/native";
import IconTextButton from "../../components/IconTextButton";
import IconButton from "../../utilities/IconButton";

type LiveSituationScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function LiveSituationScreen({route}: LiveSituationScreenProps) {

    const colorScheme = Appearance.getColorScheme();
    const navigation = useNavigation<NavigationStackParams>();

    useEffect(() => {
        navigation.setOptions({
            title: route.params.company + ' - Live Situation',
        });

    }, [route.params.scenarioName, navigation, route.params.company]);

    function mainMenuPress() {
        navigation.navigate("MainMenuScreen", {
            scenarioName: route.params.scenarioName,
            company: route.params.company
        });
    }

    return <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
        <View style={styles.bodyContainer}>
            <View style={styles.row}>
                <Text style={styles.infoText}>Coming Soon...</Text>
            </View>

            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
        </View>
    </View>
}

export default LiveSituationScreen;

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
    },
    infoText: {
        color: 'black',
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center'
    }
})