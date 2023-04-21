import { FlatList, View, Text, StyleSheet, TouchableOpacity } from "react-native";
import { fetchAdditionalTours, fetchAssignments } from "../utilities/sqlite";
import { useContext } from "react";
import { AssignContext } from "../store/context/assign-context";

function SavedGamesList({games, navigation}) {

    const assignContext = useContext(AssignContext);

    async function onLoadGame(item) {
        const assignments = await fetchAssignments(item.companyName);
        for (const assignment of assignments) {
            assignContext.addAssignment(assignment);
        }
        const additionalTours = await fetchAdditionalTours(item.companyName);
        for (const additionalTour of additionalTours) {
            assignContext.addAdditionalTour(additionalTour.routeNumber, additionalTour.tourNumber);
        }
        navigation.navigate("MainMenu", {
            company: item.companyName,
            routes: item.routeDatabase,
            vehicles: item.vehicleDatabase
          });
    }

    if ( !games || games.length === 0 ) {
        return <View style={styles.fallbackContainer}>
            <Text style={styles.fallbackTitle}>No games added yet - start adding some!</Text>
        </View>
    }
    return <FlatList style={styles.list} data={games} keyExtractor={(item) => item.id} renderItem={({item}) => <TouchableOpacity style={styles.button} onPress={onLoadGame.bind(null,item)}>
        <Text style={styles.buttonText}>{item.companyName}</Text>
    </TouchableOpacity>} />
}

export default SavedGamesList;

const styles = StyleSheet.create({
    list: {
        marginLeft: '10%',
        width: '100%'
    },
    fallbackContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    fallbackText: {
        fontSize: 16
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
    }
})