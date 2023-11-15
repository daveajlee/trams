import { Appearance, View, Text, StyleSheet } from "react-native"

function VehicleDetails(props) {

    const colorScheme = Appearance.getColorScheme();

    return <View style={styles.details}>
        <Text style={[styles.heading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{props.fleetNumber} - {props.registrationNumber}</Text>
        <Text style={[styles.detailText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Chassis Type: {props.chassisType}</Text>
        <Text style={[styles.detailText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Body Type: {props.bodyType}</Text>
        <Text style={[styles.detailText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Special Features: {props.specialFeatures}</Text>
        <Text style={[styles.detailText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Livery: {props.livery}</Text>
    </View>
}

export default VehicleDetails;

const styles = StyleSheet.create({
    details: {
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        padding: 8
    },
    heading: {
        fontSize: 20,
        fontWeight: "bold",
        marginBottom: 5
    },
    detailText: {
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