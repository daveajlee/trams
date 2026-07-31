import { Appearance, View, Text, StyleSheet } from "react-native";
import { Ionicons } from '@react-native-vector-icons/ionicons';
import VehicleDetailEntry from "./VehicleDetailEntry";

type VehicleDetailsProps = {
  fleetNumber: number;
  registrationNumber: string;
  chassisType: string;
  bodyType: string;
  specialFeatures: string;
  livery: string;
}

function VehicleDetails({fleetNumber, registrationNumber, chassisType, bodyType, specialFeatures, livery}: VehicleDetailsProps) {

    const colorScheme = Appearance.getColorScheme();

    return <View style={styles.details}>
        <Ionicons name="bus" size={48} color={colorScheme === 'dark' ? 'white' : 'black'} />
        <Text style={[styles.heading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{fleetNumber}</Text>
        <VehicleDetailEntry label="Registration Number" value={registrationNumber}/>
        <VehicleDetailEntry label="Chassis Type" value={chassisType}/>
        <VehicleDetailEntry label="Body Type" value={bodyType}/>
        <VehicleDetailEntry label="Special Features" value={specialFeatures} showAsList={true}/>
        <VehicleDetailEntry label="Livery" value={livery}/>
    </View>
}

export default VehicleDetails;

const styles = StyleSheet.create({
    details: {
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        padding: 8
    },
    heading: {
        fontSize: 20,
        fontWeight: "bold",
        marginBottom: 20
    },
    detailText: {
        fontSize: 14,
        fontStyle: "italic",
    },
    detailItem: {
        marginHorizontal: 4,
        fontSize: 12
    },
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
    }
})