import { FlatList, View, Text, StyleSheet, TouchableOpacity } from "react-native";

function SavedGamesList({games, navigation}) {

    async function onLoadGame(item) {
        navigation.navigate("MainMenuScreen", {
            company: item.companyName,
            scenarioName: item.scenarioName,
          });
    }

    if ( !games || games.length === 0 ) {
        return <View style={styles.fallbackContainer}>
            <Text style={styles.fallbackTitle}>No games added yet - start adding some!</Text>
        </View>
    }
    return <FlatList style={styles.list} data={games} keyExtractor={(item) => item.id} renderItem={({item}) => <TouchableOpacity style={styles.button} onPress={onLoadGame.bind(null,item)}>
        <Text style={styles.buttonText}>{item.companyName}</Text>
    </TouchableOpacity>} />
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
        alignItems: "center",
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