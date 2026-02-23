import { Appearance, StyleSheet, View } from "react-native";
import IconTextButton from "../../components/IconTextButton";
import { useNavigation } from "@react-navigation/native";

type AllocateScreenProps = {
  route: any;
}

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

function AllocateScreen({route}: AllocateScreenProps) {

    const colorScheme = Appearance.getColorScheme();
    const navigation = useNavigation<NavigationStackParams>();

    function onAssignPress() {
        navigation.navigate("AssignTourScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    function onChangePress() {
        navigation.navigate("ChangeAssignmentScreen", {
            company: route.params.company,
            scenarioName: route.params.scenarioName,
        });
    }

    return (
        <View style={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <View style={styles.bodyContainer}>
                <View style={styles.row}>
                    <IconTextButton icon="add" text="Assign" onPress={onAssignPress}/>
                    <IconTextButton icon="repeat" text="Change" onPress={onChangePress}/>
                </View>
            </View>
        </View>
    )

}

export default AllocateScreen;

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
    }
});