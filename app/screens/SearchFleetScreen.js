import { Appearance, View, StyleSheet, Text, TextInput, TouchableOpacity } from "react-native";
import { useState } from "react";

function SearchFleetScreen({route, navigation}) {

    const [enteredFleetNumber, setEnteredFleetNumber] = useState('');
    const colorScheme = Appearance.getColorScheme();


    function onChangeFleetNumber(enteredText) {
        setEnteredFleetNumber(enteredText)
    }

    function onSearchFleetPress() {
        navigation.navigate("VehicleScreen", {
            fleetNumber: enteredFleetNumber,
            scenarioName: route.params.scenarioName,
            company: route.params.company
        });
    }

    return (
        <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <View style={styles.headerContainer}>
                <Text style={[styles.header, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Company: {route.params.company}</Text>
            </View>
            <View style={styles.bodyContainer}>
                <TextInput
                    style={[styles.input, colorScheme === 'dark' ? styles.darkText : styles.lightText]}
                    onChangeText={onChangeFleetNumber}
                    value={enteredFleetNumber}
                    placeholder="Fleet Number"
                />
                <TouchableOpacity style={styles.button} onPress={onSearchFleetPress}>
                    <Text style={styles.buttonText}>Search</Text>
                </TouchableOpacity>
                
            </View>
        </View>
    );

}

export default SearchFleetScreen;

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
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
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
    input: {
        height: 40,
        margin: 12,
        borderWidth: 1,
        padding: 10,
    },
})