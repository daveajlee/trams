import { Appearance, View, Text, StyleSheet } from "react-native";
import { Ionicons } from '@react-native-vector-icons/ionicons';
import VehicleDetailEntry from "./VehicleDetailEntry";

type VehicleDetailsProps = {
  fleetNumber: number;
  registrationNumber: string;
  chassisType: string;
  modelName: string;
  seatingCapacity: number;
  standingCapacity: number;
  livery: string;
  value: number;
}

function VehicleDetails({fleetNumber, registrationNumber, chassisType, modelName, seatingCapacity, standingCapacity, livery, value}: VehicleDetailsProps) {

    const colorScheme = Appearance.getColorScheme();

    return <View style={styles.details}>
        <Ionicons name="bus" size={48} color={colorScheme === 'dark' ? 'white' : 'black'} />
        <Text style={[styles.heading, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{fleetNumber}</Text>
        <VehicleDetailEntry label="Registration Number" value={registrationNumber}/>
        <VehicleDetailEntry label="Chassis Type" value={chassisType}/>
        <VehicleDetailEntry label="Model Name" value={modelName}/>
        <VehicleDetailEntry label="Seating (Standing) Capacity" value={seatingCapacity + "(" + standingCapacity + ")"}/>
        <VehicleDetailEntry label="Livery" value={livery}/>
        <VehicleDetailEntry label="Value" value={"" + value}/>
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