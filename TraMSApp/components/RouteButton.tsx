import { StyleSheet, Text, TouchableOpacity } from "react-native";

type RouteButtonProps = {
    routeNumber: string;
    from: string;
    to: string;
    onPress: Event;
}

function RouteButton({routeNumber, from, to, onPress}: RouteButtonProps) {
    
    return ( 
        <TouchableOpacity style={styles.button} onPress={onPress}>
            <Text style={styles.routeNumberText}>{routeNumber}</Text>
            <Text style={styles.fromToText}>{from} &lt;&gt; {to}</Text>
        </TouchableOpacity>
    );
}

export default RouteButton;;

const styles = StyleSheet.create({
    button: {
        alignItems: "center",
        backgroundColor: "#5e7947",
        width: '30%',
        padding: 20,
        marginBottom: 20,
        height: '40%'
    },
    routeNumberText: {
        color: 'white',
        fontSize: 20,
        fontWeight: 'bold',
        marginTop: 8
    },
    fromToText: {
        color: 'white',
        fontSize: 14,
        marginTop: 4
    }
})