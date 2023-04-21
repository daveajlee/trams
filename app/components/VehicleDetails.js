import { View, Text, StyleSheet } from "react-native"

function VehicleDetails(props) {
    return <View style={styles.details}>
        <Text style={styles.details}>{props.fleetNumber} - {props.registrationNumber}</Text>
        <Text style={styles.details}>Chassis Type: {props.chassisType}</Text>
        <Text style={styles.details}>Body Type: {props.bodyType}</Text>
        <Text style={styles.details}>Special Features: {props.specialFeatures}</Text>
        <Text style={styles.details}>Livery: {props.livery}</Text>
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
    detailItem: {
        marginHorizontal: 4,
        fontSize: 12
    }
})