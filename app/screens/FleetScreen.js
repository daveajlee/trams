import { LANDUFF_NAME, LANDUFF_VEHICLES } from "../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_VEHICLES } from "../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_VEHICLES } from "../scenarios/longts-scenario";
import FleetList from "../components/FleetList";
import { Appearance, StyleSheet, View } from "react-native";

function FleetScreen({route}) {

    const colorScheme = Appearance.getColorScheme();

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

    return <View style={[styles.rootContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
        <FleetList items={vehicles} />
    </View>
}

export default FleetScreen;

const styles = StyleSheet.create({
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#f2ffe6',
    },
    rootContainer: {
        marginBottom: 32,
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    }
})