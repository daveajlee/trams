import { StyleSheet, View, FlatList, Text } from "react-native";

function FleetList({items}) {
    function renderFleetItem(itemData) {
        return (
            <View style={styles.details}>
                <Text style={styles.heading}>{itemData.item.fleetNumber} - {itemData.item.registrationNumber}</Text>
                <Text style={styles.detailText}>Chassis Type: {itemData.item.chassisType}</Text>
                <Text style={styles.detailText}>Body Type: {itemData.item.bodyType}</Text>
                <Text style={styles.detailText}>Special Features: {itemData.item.specialFeatures}</Text>
                <Text style={styles.detailText}>Livery: {itemData.item.livery}</Text>
                <View style={styles.lineStyle}/>
            </View>
        )
    }

    return (
        <View style={styles.container}>
            <FlatList data={items} keyExtractor={(item) => item.fleetNumber} renderItem={renderFleetItem}/>
        </View>
    );
}

export default FleetList;

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
    heading: {
        fontSize: 20,
        fontWeight: "bold",
        marginBottom: 5
    },
    detailText: {
        alignItems: 'center',
        justifyContent: 'center',
        fontSize: 14,
        fontStyle: "italic",
    },
    lineStyle:{
        borderWidth: 0.5,
        borderColor:'black',
        width: '100%',
        marginTop: 10,
        marginBottom: 10
   }
})