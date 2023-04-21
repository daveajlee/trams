import { StyleSheet, View, FlatList, Text } from "react-native";

function FleetList({items}) {
    function renderFleetItem(itemData) {
        return (
            <View style={styles.details}>
                <Text style={styles.details}>{itemData.item.fleetNumber} - {itemData.item.registrationNumber}</Text>
                <Text style={styles.details}>Chassis Type: {itemData.item.chassisType}</Text>
                <Text style={styles.details}>Body Type: {itemData.item.bodyType}</Text>
                <Text style={styles.details}>Special Features: {itemData.item.specialFeatures}</Text>
                <Text style={styles.details}>Livery: {itemData.item.livery}</Text>
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
    lineStyle:{
        borderWidth: 0.5,
        borderColor:'black',
        width: '100%',
   }
})