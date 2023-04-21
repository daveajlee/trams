import { useIsFocused } from "@react-navigation/native";
import { useEffect, useState } from "react";
import SavedGamesList from "../components/SavedGamesList";
import { fetchGames } from "../utilities/sqlite";
import { View, StyleSheet } from "react-native";

function SavedGamesScreen({route, navigation}) {
    const [loadedGames, setLoadedGames] = useState([]);

    const isFocused = useIsFocused;
    useEffect(() => {
        async function loadGames() {
            const games = await fetchGames();
            setLoadedGames(games);
        }

        if ( isFocused) {
            loadGames();
        }
    }, [isFocused]);

    return <View style={styles.container}>
        <View style={styles.bodyContainer}>
            <SavedGamesList games={loadedGames} navigation={navigation}/>
        </View>
    </View>
}

export default SavedGamesScreen;

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
    },
});