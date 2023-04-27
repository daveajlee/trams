import { View, Text, FlatList, StyleSheet } from "react-native";
import { TouchableOpacity } from "react-native";
import { useNavigation } from "@react-navigation/native";
import { deleteAssignment } from "../utilities/sqlite";

function AssignmentList({items, companyName, scenarioName}) {

    const navigation = useNavigation();

    async function deleteAssignmentFromDB(routeNumber, tourNumber, companyName) {
        deleteAssignment(routeNumber, tourNumber, companyName).then(
            navigation.navigate("MainMenuScreen", {
                company: companyName,
                scenarioName: scenarioName
            }));
        
    }

    function renderAssignmentItem(itemData) {
        return (
            <View style={styles.details}>
                <Text style={styles.heading}>{itemData.item.routeNumber}/{itemData.item.tourNumber} assigned to vehicle {itemData.item.fleetNumber}</Text>
                <TouchableOpacity style={styles.button} onPress={deleteAssignmentFromDB.bind(null, itemData.item.routeNumber, itemData.item.tourNumber, companyName)}>
                    <Text style={styles.buttonText}>Delete</Text>
                </TouchableOpacity>
                <View style={styles.lineStyle}/>
            </View>
        )
    }

    return (
        <View style={styles.container}>
            <FlatList data={items} keyExtractor={(item) => item.routeNumber + item.tourNumber} renderItem={renderAssignmentItem}/>
        </View>
    );
}

export default AssignmentList;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 16
    },
    details: {
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        padding: 8
    },
    lineStyle:{
        borderWidth: 0.5,
        borderColor:'black',
        width: '100%',
    },
    heading: {
        fontSize: 20,
        fontWeight: "bold",
        marginBottom: 15
    },
    button: {
        alignItems: "center",
        backgroundColor: "#5e7947",
        width: '90%',
        padding: 10,
        marginBottom: 15,
    },
    buttonText: {
        color: 'white',
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
    }
})