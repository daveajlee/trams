import { Appearance, StyleSheet, View, FlatList, Text } from "react-native";

function FleetList({items}) {

    const colorScheme = Appearance.getColorScheme();

    function renderFleetItem(itemData) {
        return (
            <View style={styles.details}>
                <Text style={[styles.heading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{itemData.item.fleetNumber} - {itemData.item.registrationNumber}</Text>
                <Text style={[styles.detailText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Chassis Type: {itemData.item.chassisType}</Text>
                <Text style={[styles.detailText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Body Type: {itemData.item.bodyType}</Text>
                <Text style={[styles.detailText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Special Features: {itemData.item.specialFeatures}</Text>
                <Text style={[styles.detailText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Livery: {itemData.item.livery}</Text>
                <View style={[styles.lineStyle, colorScheme === 'dark' ? styles.darkLine : styles.lightLine]}/>
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
        width: '100%',
        marginTop: 10,
        marginBottom: 10
    },
    darkLine: {
        borderColor: 'white'
    },
    lightLine: {
        borderColor: 'black'
    },
})