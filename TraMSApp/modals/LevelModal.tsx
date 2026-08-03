import { Alert, Modal, Pressable, StyleSheet, Text, View } from "react-native";
import IconButton from "../utilities/IconButton";
import Level from "../models/level";
import { useEffect, useState } from "react";

type LevelModalProperties = {
  modalVisible: boolean;
  setModalVisible: Function;
  setOriginSelectedItem: Function;
}

function LevelModal({modalVisible, setModalVisible, setOriginSelectedItem}: LevelModalProperties) {

    const [levels, setLevels] = useState<Level[]>([]);
    const [selectedLevel, setSelectedLevel] = useState<Level>();

    useEffect(() => {
        async function loadLevels() {
            let myLevels: Level[] = [];
            myLevels.push(
                new Level('Easy', 'Less delays and less challenges to solve on a regular basis.'),
                new Level('Medium', 'Regular delays and new challenges to solve on a regular basis.'),
                new Level('Difficult', 'Heavy delays and lots of challenges to solve on a regular basis.')
            );
            setLevels(myLevels);
            setSelectedLevel(myLevels[0]);
        }
    
        loadLevels();
    }, []);

    function selectValue(level: Level) {
        setSelectedLevel(level);
        setOriginSelectedItem(level.name);
    }

    return (
        <Modal 
            animationType="slide"
            transparent={true}
            visible={modalVisible}
            onRequestClose={() => {
                Alert.alert('Modal has been closed.');
                setModalVisible(!modalVisible);
            }}>
            <View style={styles.centeredView}>
                <View style={styles.modalView}>
                    <View style={styles.headerView}>
                        <IconButton icon="chevron-back-outline" size={48} color="black" onPress={() => setModalVisible(!modalVisible)}/>
                        <Text style={styles.modalHeadline}>Level</Text>
                    </View>
                    { levels.map((level, _) => (
                        <View key={level.name} style={styles.optionBoxContainer}>
                            <Pressable onPress={selectValue.bind(null, level)}>
                                <View style={styles.optionContainer}>
                                    <View style={styles.nameOptionContainer}>
                                        {level.name && <Text style={styles.nameOption}>{level.name}</Text> }
                                    </View>
                                    <View style={styles.valueOptionContainer}>
                                        {selectedLevel.name === level.name && <IconButton icon="checkmark" size={48} color="black" onPress={selectValue.bind(null, level)}/>}
                                    </View>
                                </View>
                                <View style={styles.infoOptionContainer}>
                                    <IconButton icon="information-circle" size={32} color="black" onPress={selectValue}/>
                                    {level.description && <Text style={styles.nameFooterOption}>{level.description}</Text>}
                                </View>
                            </Pressable>
                        </View>
                    ))};
                </View>
            </View>
        </Modal>
    )

}

export default LevelModal;

const styles = StyleSheet.create({

    centeredView: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    headerView: {
        flexDirection: 'row',
        marginTop: 30
    },
    modalView: {
        margin: 20,
        backgroundColor: '#b5de90',
        borderRadius: 20,
        padding: 35,
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 5,
        width: '100%',
        height: '100%'
    },
    optionContainer: {
        flexDirection: 'row',
    },
    optionBoxContainer: {
        marginBottom: 30
    },
    nameOptionContainer: {
        flexDirection: 'column',
        width: '90%'
    },
    nameOption: {
        fontSize: 36,
        fontWeight: 'bold',
        textAlign: 'left'
    },
    nameFooterOption: {
        fontSize: 14,
        fontStyle: 'italic',
        textAlign: 'left',
        width: '70%',
        marginLeft: 10
    },
    valueOptionContainer: {
        textAlign: 'right',
        justifyContent: 'flex-end',
        alignItems: 'flex-end'
    },
    infoOptionContainer: {
        flexDirection: 'row'
    },
    modalButton: {
        borderRadius: 20,
        padding: 10,
        elevation: 2,
    },
    textStyle: {
        color: 'white',
        fontWeight: 'bold',
        textAlign: 'center',
    },
    modalHeadline: {
        marginTop: 10,
        marginLeft: 30,
        marginBottom: 15,
        textAlign: 'center',
        fontSize: 48,
        fontWeight: 'bold'
    },
    modalText: {
        marginTop: 30,
        marginBottom: 15,
        textAlign: 'center',
    },

});