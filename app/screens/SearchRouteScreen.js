import { Appearance, View, StyleSheet, Text, TextInput, TouchableOpacity, Alert } from "react-native";
import { useState } from "react";

function SearchRouteScreen({route, navigation}) {

    const [enteredRouteNumber, setEnteredRouteNumber] = useState('');
    const colorScheme = Appearance.getColorScheme();


    function onChangeRouteNumber(enteredText) {
        setEnteredRouteNumber(enteredText)
    }

    function onSearchRoutePress() {
        navigation.navigate("RouteScreen", {
            routeNumber: enteredRouteNumber,
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
                    onChangeText={onChangeRouteNumber}
                    value={enteredRouteNumber}
                    placeholder="Route Number"
                />
                <TouchableOpacity style={styles.button} onPress={onSearchRoutePress}>
                    <Text style={styles.buttonText}>Search</Text>
                </TouchableOpacity>
            </View>
        </View>
    );

}

export default SearchRouteScreen;

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