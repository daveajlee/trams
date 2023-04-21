import { View, Text, FlatList, StyleSheet } from "react-native";
import { AssignContext } from "../store/context/assign-context";
import { useContext } from "react";
import { Button } from "react-native";
import { useNavigation } from "@react-navigation/native";
import { deleteAssignment } from "../utilities/sqlite";

function AssignmentList({items, companyName, routeDatabase, vehicleDatabase}) {

    const assignContext = useContext(AssignContext);
    const navigation = useNavigation();

    async function deleteAssignmentFromDB(routeNumber, tourNumber, companyName) {
        assignContext.removeAssignment(routeNumber, tourNumber); 
        deleteAssignment(routeNumber, tourNumber, companyName).then(
            navigation.navigate("MainMenu", {
                company: companyName,
                routes: routeDatabase,
                vehicles: vehicleDatabase
            }));
        
    }

    function renderAssignmentItem(itemData) {
        return (
            <View style={styles.details}>
                <Text style={styles.details}>Route Number: {itemData.item.routeNumber}</Text>
                <Text style={styles.details}>Tour Number: {itemData.item.tourNumber}</Text>
                <Text style={styles.details}>Fleet Number: {itemData.item.fleetNumber}</Text>
                <Button title="Delete" onPress={deleteAssignmentFromDB.bind(null, itemData.item.routeNumber, itemData.item.tourNumber, companyName)}/>
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
   }
})