import { FlatList, View, Text, StyleSheet, TouchableOpacity } from "react-native";
import { useEffect, useState } from "react";
import { fetchGames } from "../utilities/sqlite";

function SavedGamesList({games, navigation}) {

    const [loadedGames, setLoadedGames] = useState([]);

    useEffect(() => {
        async function loadGames() {
            const games = await fetchGames();
            setLoadedGames(games);
        }

        loadGames();
    }, []);

    async function onLoadGame(item) {
        console.log('I loaded game with company name ' + item.companyName);
    }

    if ( !loadedGames || loadedGames.length === 0 ) {
        return <View style={styles.fallbackContainer}>
            <Text style={styles.fallbackTitle}>No games added yet - start adding some!</Text>
        </View>
    }
    return <FlatList style={styles.list} data={loadedGames} keyExtractor={(item) => item.id} renderItem={({item}) => <TouchableOpacity style={styles.button} onPress={onLoadGame.bind(null, item)}>
        <Text style={styles.buttonText}>{item.companyName}</Text>
    </TouchableOpacity>}/>

}

export default SavedGamesList;

const styles = StyleSheet.create({
    list: {
        marginLeft: '10%',
        width: '100%'
    },
    fallbackContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    fallbackText: {
        fontSize: 16
    },
    button: {
        alignItems: 'center',
        backgroundColor: "#5e7947",
        width: '90%',
        padding: 20,
        marginBottom: 20,
    },
    buttonText: {
        color: 'white',
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center'
    }
})