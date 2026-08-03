import { Appearance, StyleSheet, Text, View } from "react-native";

type VehicleDetailEntryProps = {
    label: string;
    value: string;
    showAsList?: boolean;
}

function VehicleDetailEntry({label, value, showAsList}: VehicleDetailEntryProps) {

    const colorScheme = Appearance.getColorScheme();

    function processValueAsList(value: string) {
        let output = "";
        let splitValue = value.split(",");
        splitValue.forEach((item) => {
            output += " " + item.trim() + "\n";
        });
        return output.substring(0, output.length - 1); // Remove the last newline character
    }   

    return (
        <View style={styles.container}>
            <Text style={[styles.label, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{label}:</Text>
            <Text style={[showAsList ? styles.valueList : styles.value, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{showAsList ? processValueAsList(value): value}</Text>
        </View>
    );

}

export default VehicleDetailEntry;

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        marginTop: 10,
    },
    label: {
        fontWeight: 'bold',
        fontSize: 18,
        width: '50%',
        textAlign: 'center'
    },
    value: {
        fontSize: 18,
        width: '50%',
        textAlign: 'center'
    },
    valueList: {
        fontSize: 18,
        width: '50%',
        textAlign: 'center'
    },
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
    }
});