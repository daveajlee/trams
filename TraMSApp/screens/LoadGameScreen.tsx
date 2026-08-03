import { Appearance, View, StyleSheet } from "react-native";
import SavedGamesList from "../components/SavedGamesList";
import { useNavigation } from '@react-navigation/native';
import { useEffect } from "react";
import IconButton from "../utilities/IconButton";

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

/**
 * This screen represents the load game screen on a Smartphone.
 * It displays a list of saved games using a separate component which
 * is based on all of the games currently stored in the database.
 */
function LoadGameScreen() {

    const navigation = useNavigation<NavigationStackParams>();

    const colorScheme = Appearance.getColorScheme();

    useEffect(() => {
        navigation.setOptions({
            title: 'Saved Games',
            headerRight: () => <View style={{marginLeft: 10, flexDirection: 'row'}}>             
                <IconButton icon="add" size={24} color="black" onPress={onCreateGame}/>
                </View>,
        });
    });

    function onCreateGame() {
        navigation.navigate("CreateGameScreen");
    }

    return <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
        <View style={styles.bodyContainer}>
            <SavedGamesList navigation={navigation} games={null}/>
        </View>
    </View>
}

export default LoadGameScreen;

const styles = StyleSheet.create({
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#f2ffe6',
    },
    container: {
        flex: 1,
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