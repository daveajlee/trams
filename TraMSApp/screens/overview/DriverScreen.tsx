import { LANDUFF_NAME, LANDUFF_DRIVERS } from "../../scenarios/landuff-scenario";
import { MDORF_NAME, MDORF_DRIVERS } from "../../scenarios/mdorf-scenario";
import { LONGTS_NAME, LONGTS_DRIVERS } from "../../scenarios/longts-scenario";
import { useEffect, useState } from "react";
import { Alert, Appearance, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import Driver from "../../models/driver.ts";
import { useNavigation } from "@react-navigation/native";
import IconTextButton from "../../components/IconTextButton";
import IconButton from "../../utilities/IconButton";

type DriverScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function DriverScreen({route}: DriverScreenProps) {

    const colorScheme = Appearance.getColorScheme();
    const navigation = useNavigation<NavigationStackParams>();

    const [drivers, setDrivers] = useState<Driver[]>([]);

    useEffect(() => {
        navigation.setOptions({
            title: route.params.company + ' - Drivers',
            headerRight: () => <View style={{marginLeft: 10, flexDirection: 'row'}}>             
                <IconButton icon="add" size={24} color="black" onPress={onCreateDriver}/>
                </View>,
        });

        async function onCreateDriver() {
            Alert.alert("Coming Soon!", "Not yet available!");
        }
    
        async function loadDrivers() {
            switch (route.params.scenarioName) {
                case LANDUFF_NAME:
                    setDrivers(convertToDriverArray(LANDUFF_DRIVERS));
                    break;
                case MDORF_NAME:
                    setDrivers(convertToDriverArray(MDORF_DRIVERS));
                    break;
                case LONGTS_NAME:
                    setDrivers(convertToDriverArray(LONGTS_DRIVERS));
                    break;
                default:
                    Alert.alert('Error', 'Unknown scenario: ' + route.params.scenarioName);
            }
        }
    
        loadDrivers();
    }, [route.params.scenarioName, navigation, route.params.company]);

    function convertToDriverArray(names: string[]): Driver[] {
        let drivers: Driver[] = [];
        names.forEach((name) => {
            drivers.push(new Driver(name));
        })
        return drivers;
    }

    function mainMenuPress() {
        navigation.navigate("MainMenuScreen", {
            scenarioName: route.params.scenarioName,
            company: route.params.company
        });
    }

    async function onPressDriver(name: string) {
        Alert.alert("Coming Soon!", "Not yet available!");
    }

    return <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
        <View style={styles.bodyContainer}>
            <View style={styles.row}>
                {drivers.map((driver) => (
                    <IconTextButton key={driver.name} icon="person" text={"" + driver.name} onPress={onPressDriver.bind(null, driver.name)}/>
                ))}
            </View>

            <TouchableOpacity style={styles.button} onPress={mainMenuPress}>
                <Text style={styles.buttonText}>Main Menu</Text>
            </TouchableOpacity>
        </View>
    </View>
}

export default DriverScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#f2ffe6',
    },
    bodyContainer: {
        flex: 4,
        width: '100%',
        alignItems: 'center',
        marginTop: 30
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        width: '100%',
        flexWrap: 'wrap',
        alignItems: 'flex-start'
    },
    button: {
        alignItems: "center",
        backgroundColor: "#5e7947",
        width: '90%',
        padding: 20,
        marginTop: 30,
        marginBottom: 20,
    },
    buttonText: {
        color: 'white',
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center'
    }
})