import { View, Text, StyleSheet } from "react-native"

function RouteDetails(props) {
    return <View style={styles.details}>
        <Text style={styles.details}>{props.number} - {props.outwardTerminus} &lt;&gt; {props.returnTerminus}</Text>
        <Text style={styles.details}>Number of buses required: {props.numberTours}</Text>
    </View>
}

export default RouteDetails;

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