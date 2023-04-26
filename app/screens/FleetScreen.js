import { LANDUFF_NAME, LANDUFF_VEHICLES } from "../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_VEHICLES } from "../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_VEHICLES } from "../scenarios/longts-scenario";
import FleetList from "../components/FleetList";
import { StyleSheet, View } from "react-native";

function FleetScreen({route}) {

    var vehicles;
    if ( route.params.scenarioName === LANDUFF_NAME ) {
        vehicles = LANDUFF_VEHICLES;
    }
    else if ( route.params.scenarioName === MDORF_NAME ) {
        vehicles = MDORF_VEHICLES;
    }
    else if ( route.params.scenarioName === LONGTS_NAME ) {
        vehicles = LONGTS_VEHICLES;
    }

    return <View style={styles.rootContainer}>
        <FleetList items={vehicles} />
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