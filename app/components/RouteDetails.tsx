import { Appearance, ScrollView, View, Text, StyleSheet } from "react-native"
import { useEffect, useState } from "react";
import { Ionicons } from '@react-native-vector-icons/ionicons';
import Route from "../models/route";

type RouteDetailsProps = {
  route: Route;
}

function RouteDetails({route}: RouteDetailsProps) {

    const [assignments, setAssignments] = useState<string[]>([]);

    useEffect(() => {

        async function loadAssignments() {
            let myAssignments: string[] = [];
            for ( let i = 0; i < route.numberTours; i++) { 
                myAssignments.push(route.number + "/" + (i+1));
            }
            setAssignments(myAssignments);
        }
    
            loadAssignments();
    }, [route.number, route.numberTours]);

    const colorScheme = Appearance.getColorScheme();

    return <ScrollView contentContainerStyle={styles.details}>
        <Text style={[styles.routeNumber, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{route.number}</Text>
        <Text style={[styles.heading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{route.outwardTerminus} &lt;&gt; {route.returnTerminus}</Text>

        <View style={styles.stopHeadingView}>
            <Ionicons name="stop-circle" size={48} color={colorScheme === 'dark' ? 'white' : 'black'} />
            <Text style={[styles.stopHeading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Stops</Text>
        </View>
        
        {route.stopList.map((stop)=> (
            <Text key={stop} style={[styles.stopText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{stop}</Text>
        ))}

        <View style={styles.stopHeadingView}>
            <Ionicons name="bus" size={48} color={colorScheme === 'dark' ? 'white' : 'black'} />
            <Text style={[styles.stopHeading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Tours</Text>
        </View> 
        {assignments.map((assignment) => (
            <View style={styles.container}>
                <Text key={assignment}style={[styles.label, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{assignment}:</Text>
                <Text style={[styles.value, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Unassigned</Text>
            </View>
        ))}
    </ScrollView>
}

export default RouteDetails;

const styles = StyleSheet.create({
    details: {
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        padding: 8,
    },
    stopHeadingView: {
        flexDirection: 'row',
        marginTop: 10
    },
    stopHeading: {
        fontSize: 32,
        fontWeight: "bold",
        marginLeft: 10
    },
    stopText: {
        fontSize: 24,
        fontWeight: "bold",
        marginBottom: 10
    },
    routeNumber: {
        fontSize: 36,
        fontWeight: "bold",
    },
    heading: {
        fontSize: 28,
        fontWeight: "bold",
        marginBottom: 10,
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
    },
    container: {
        flexDirection: 'row',
        marginTop: 10,
    },
    label: {
        fontWeight: 'bold',
        fontSize: 18,
        width: '50%',
        textAlign: 'center'
    },
    value: {
        fontSize: 18,
        width: '50%',
        textAlign: 'center'
    },
})