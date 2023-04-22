import { View, StyleSheet, Text, TextInput, TouchableOpacity, Alert } from "react-native";
import { useState } from "react";

function SearchFleetScreen({route, navigation}) {

    const [enteredFleetNumber, setEnteredFleetNumber] = useState('')


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
        <View style={styles.container}>
            <View style={styles.headerContainer}>
                <Text style={styles.header}>Company: {route.params.company}</Text>
            </View>
            <View style={styles.bodyContainer}>
                <TextInput
                    style={styles.input}
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
        backgroundColor: '#f2ffe6',
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
    input: {
        height: 40,
        margin: 12,
        borderWidth: 1,
        padding: 10,
    },
})