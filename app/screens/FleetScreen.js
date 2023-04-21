import { LANDUFF_VEHICLES, MDORF_VEHICLES, LONGTS_VEHICLES } from "../data/scenario-data";
import FleetList from "../components/FleetList";
import { StyleSheet, View } from "react-native";

function FleetScreen(props) {
    return <View style={styles.rootContainer}>
        <FleetList items={LANDUFF_VEHICLES} />
    </View>
}

export default FleetScreen;

const styles = StyleSheet.create({
    rootContainer: {
        marginBottom: 32,
        flex: 1,
        backgroundColor: '#f2ffe6',
        alignItems: 'center',
        justifyContent: 'center',
    }
})