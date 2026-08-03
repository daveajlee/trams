import { Appearance, View, Text, FlatList, StyleSheet } from "react-native";
import { TouchableOpacity } from "react-native";
import { useNavigation } from '@react-navigation/native';
import { deleteAssignment } from "../utilities/sqlite";

type AssignmentListProps = {
    items: any;
    companyName: string;
    scenarioName: string;
}

type NavigationStackParams = {
  navigate: Function;
}

function AssignmentList({items, companyName, scenarioName}: AssignmentListProps) {

    const navigation = useNavigation<NavigationStackParams>();
    const colorScheme = Appearance.getColorScheme();

    async function deleteAssignmentFromDB(routeNumber: string, tourNumber: string) {
        deleteAssignment(routeNumber, parseInt(tourNumber, 10), companyName).then(
            navigation.navigate("MainMenuScreen", {
                company: companyName,
                scenarioName: scenarioName
            }));
        
    }

    function renderAssignmentItem(itemData: any) {
        return (
            <View style={styles.details}>
                <Text style={[styles.heading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{itemData.item.routeNumber}/{itemData.item.tourNumber} assigned to vehicle {itemData.item.fleetNumber}</Text>
                <TouchableOpacity style={styles.button} onPress={deleteAssignmentFromDB.bind(null, itemData.item.routeNumber, itemData.item.tourNumber)}>
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
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
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