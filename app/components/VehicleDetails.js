import { View, Text, StyleSheet } from "react-native"

function VehicleDetails(props) {
    return <View style={styles.details}>
        <Text style={styles.heading}>{props.fleetNumber} - {props.registrationNumber}</Text>
        <Text style={styles.detailText}>Chassis Type: {props.chassisType}</Text>
        <Text style={styles.detailText}>Body Type: {props.bodyType}</Text>
        <Text style={styles.detailText}>Special Features: {props.specialFeatures}</Text>
        <Text style={styles.detailText}>Livery: {props.livery}</Text>
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
    }
})