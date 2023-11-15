import { Appearance, View, Text, StyleSheet } from "react-native"

function RouteDetails(props) {

    const colorScheme = Appearance.getColorScheme();

    return <View style={styles.details}>
        <Text style={[styles.heading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{props.number} - {props.outwardTerminus} &lt;&gt; {props.returnTerminus}</Text>
        <Text style={[styles.tours, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Number of tours/vehicles required: {props.numberTours}</Text>
    </View>
}

export default RouteDetails;

const styles = StyleSheet.create({
    details: {
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        padding: 8,
    },
    heading: {
        fontSize: 20,
        fontWeight: "bold",
    },
    tours: {
        fontSize: 14,
        fontStyle: "italic",
    },
    detailItem: {
        marginHorizontal: 4,
        fontSize: 12
    },
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
    }
})