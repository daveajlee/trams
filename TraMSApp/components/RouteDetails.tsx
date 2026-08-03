import { Appearance, ScrollView, View, Text, StyleSheet, Pressable } from "react-native"
import { useEffect, useState } from "react";
import { Ionicons } from '@react-native-vector-icons/ionicons';
import Route from "../models/route";
import { useNavigation } from "@react-navigation/native";
import Assignment from "../models/assignment";
import { fetchAssignments, deleteAssignment } from "../utilities/sqlite";

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
    const [tours, setTours] = useState<string[]>([]);
    const [assignments, setAssignments] = useState<Assignment[]>([]);

    useEffect(() => {

        async function loadTours() {
            let myTours: string[] = [];
            for ( let i = 0; i < route.numberTours; i++) { 
                myTours.push(route.number + "/" + (i+1));
            }
            setTours(myTours);
        }
    
        loadTours();
        
        loadAssignments();

        async function loadAssignments() {
            const fetchedAssignments = await fetchAssignments(companyName);
            setAssignments(fetchedAssignments.filter((assignment) => assignment.routeNumber === route.number));
        }

    }, [route.number, route.numberTours, companyName]);

    function displayAssignmentScreen(routeTourAssigment: string) {
        navigation.navigate("AssignTourScreen", {
                company: companyName,
                scenarioName: scenarioName,
                routeTourAssignment: routeTourAssigment
            });
    }

    async function deleteAssignmentFromDB(routeTourAssigment: string) {
        deleteAssignment(routeTourAssigment.split("/")[0], parseInt(routeTourAssigment.split("/")[1], 10), companyName);
        const fetchedAssignments = await fetchAssignments(companyName);
        setAssignments(fetchedAssignments.filter((assignment) => assignment.routeNumber === route.number));
    }

    function getAssignedVehicle(routeTourAssignment: string) {
        for ( let i = 0; i < assignments.length; i++) {
            if ((assignments[i].routeNumber + "/" + assignments[i].tourNumber) === routeTourAssignment) {
                return assignments[i].fleetNumber;
            }
        }
        return "Unassigned";
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
        {tours.map((tour) => (
            <View key={tour} style={styles.container}>
                <Text style={[styles.label, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{tour}:</Text>
                <Text style={[styles.value, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{getAssignedVehicle(tour)}</Text>
                <Pressable onPress={getAssignedVehicle(tour) === 'Unassigned' ? displayAssignmentScreen.bind(null,tour): deleteAssignmentFromDB.bind(null,tour)}>
                    {getAssignedVehicle(tour) === 'Unassigned' ? 
                        <Ionicons name="create-outline" size={24} color={colorScheme === 'dark' ? 'white' : 'black'}/> : 
                        <Ionicons name="trash-bin-outline" size={24} color={colorScheme === 'dark' ? 'white' : 'black'}/> 
                    }
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