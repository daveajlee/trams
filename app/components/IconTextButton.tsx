import { StyleSheet, Text, TouchableOpacity } from "react-native";
import { Ionicons } from '@react-native-vector-icons/ionicons';

type IconTextButtonProps = {
    icon: any;
    text: string;
    onPress: Event;
}

function IconTextButton({icon, text, onPress}: IconTextButtonProps) {
    return ( 
        <TouchableOpacity style={styles.button} onPress={onPress}>
            <Ionicons name={icon} size={24} color="white" />
            <Text style={styles.buttonText}>{text}</Text>
        </TouchableOpacity>
    );
}

export default IconTextButton;;

const styles = StyleSheet.create({
    button: {
        alignItems: "center",
        backgroundColor: "#5e7947",
        width: '30%',
        padding: 20,
        marginBottom: 20,
    },
    buttonText: {
        color: 'white',
        fontSize: 16,
        fontWeight: 'bold',
        marginTop: 8
    }
})