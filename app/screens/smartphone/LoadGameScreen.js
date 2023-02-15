import { View, StyleSheet } from "react-native";
import SavedGamesList from "../../components/SavedGamesList";

/**
 * This screen represents the load game screen on a Smartphone.
 * It displays a list of saved games using a separate component which
 * is based on all of the games currently stored in the database.
 */
function LoadGameScreen({navigation}) {

    return <View style={styles.container}>
        <View style={styles.bodyContainer}>
            <SavedGamesList navigation={navigation}/>
        </View>
    </View>
}

export default LoadGameScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f2ffe6',
        alignItems: 'center',
        justifyContent: 'center',
    },
    bodyContainer: {
        flex: 4,
        width: '100%',
        alignItems: 'center',
        marginTop: 30
    }
});