import { Appearance, ScrollView, View, Text, StyleSheet, Pressable } from "react-native"
import { useEffect, useState } from "react";
import { Ionicons } from '@react-native-vector-icons/ionicons';
import Route from "../models/route";
import { useNavigation } from "@react-navigation/native";

type RouteDetailsProps = {
  route: Route;
  companyName: string;
  scenarioName: string;
}

type NavigationStackParams = {
  navigate: Function;
}

function RouteDetails({route, companyName, scenarioName}: RouteDetailsProps) {

    const navigation = useNavigation<NavigationStackParams>();
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

    function displayAssignmentScreen(routeTourAssigment: string) {
        navigation.navigate("AssignTourScreen", {
                company: companyName,
                scenarioName: scenarioName,
                routeTourAssignment: routeTourAssigment
            });
    }

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
            <View key={assignment} style={styles.container}>
                <Text style={[styles.label, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{assignment}:</Text>
                <Text style={[styles.value, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Unassigned</Text>
                <Pressable onPress={displayAssignmentScreen.bind(null,assignment)}>
                    <Ionicons name="create-outline" size={24} color={colorScheme === 'dark' ? 'white' : 'black'}/>
                </Pressable>
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
        width: '40%',
        textAlign: 'center'
    },
    value: {
        fontSize: 18,
        width: '40%',
        textAlign: 'center',
        marginRight: 10
    },
})