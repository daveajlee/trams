import {
  StyleSheet,
  useColorScheme,
} from 'react-native';
import CreateGameScreen from './screens/smartphone/CreateGameScreen';
import { useCallback, useEffect, useState } from 'react';
import { fetchGames, init } from './utilities/sqlite';
import SplashScreen from 'expo-splash-screen';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import LoadGameScreen from './screens/smartphone/LoadGameScreen';
import IconButton from './utilities/IconButton';
import ChooseScenarioScreen from './screens/smartphone/ChooseScenarioScreen';
import MainMenuScreen from './screens/MainMenuScreen';
import SearchRouteScreen from './screens/SearchRouteScreen';
import SearchFleetScreen from './screens/SearchFleetScreen';
import RouteScreen from './screens/RouteScreen';
import VehicleScreen from './screens/VehicleScreen';
import FleetScreen from './screens/FleetScreen';
import AssignTourScreen from './screens/AssignTourScreen';
import ChangeAssignmentScreen from './screens/ChangeAssignmentScreen';

// Define stack navigation
const Stack = createNativeStackNavigator();

/**
 * This is the first screen in the App which appears as 
 * soon as React Native starts. It detects the device
 * that the user uses and then moves the user to the
 * appropriate screen.
 * @returns the content to be displayed to the user
 */
export default function App() {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? 'black' : 'white',
  };

  const [firstScreen, setFirstScreen] = useState('');

  const [loading, setLoading] = useState(true);

  const [dbInitialized, setDbInitialized] = useState(false);

  useEffect(() => {
    async function prepare() {
      try {
        init().then(() => {
          setDbInitialized(true);
        })
      } catch (err) {
        console.log(err);
      }
    }

    prepare();

    fetchGames().then(
      (games) => {
        setFirstScreen(!games || games.length === 0 ? 'CreateGameScreen' : 'LoadGameScreen');
        setLoading(false);
      }
    ).catch((err) => {
      setLoading(false);
      console.log('Setting default screen because of error');
      setFirstScreen('CreateGameScreen');
    })
  }, []);

  const onLayoutRootView = useCallback(async () => {
    if (dbInitialized) {
      await SplashScreen.hideAsync();
    }
  }, [dbInitialized]);

  if (!dbInitialized || loading) {
    return null;
  }

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName={firstScreen}>
        <Stack.Screen name="CreateGameScreen" component={CreateGameScreen} options={({navigation}) => ({
          headerShown: false
        })}/>
        <Stack.Screen name="LoadGameScreen" component={LoadGameScreen} options={({navigation}) => ({
          title: 'Saved Games',
          headerRight: ({tintColor}) => (
            <IconButton icon="add" size={24} color={tintColor} onPress={() => navigation.navigate('CreateGameScreen')}/>
          ),})}/>
        <Stack.Screen name="ChooseScenarioScreen" component={ChooseScenarioScreen} options={({navigation}) => ({
          title: 'Choose Scenario'
        })}/>
        <Stack.Screen name="MainMenu" component={MainMenuScreen} options={{
          title: 'Main Menu'
        }}/>
        <Stack.Screen name="SearchRouteScreen" component={SearchRouteScreen} options={{
          title: 'Search by Route Number'
        }}/>
        <Stack.Screen name="SearchFleetScreen" component={SearchFleetScreen} options={{
          title: 'Search by Fleet Number'
        }}/>
        <Stack.Screen name="RouteScreen" component={RouteScreen} options={{
          title: 'Route Details'
        }}/>
        <Stack.Screen name="VehicleScreen" component={VehicleScreen} options={{
          title: 'Vehicle Details'
        }}/>
        <Stack.Screen name="FleetScreen" component={FleetScreen} options={{
          title: 'Fleet Overview'
        }}/>
        <Stack.Screen name="AssignTourScreen" component={AssignTourScreen} options={{
          title: 'Assign Routes and Vehicles'
        }}/>
        <Stack.Screen name="ChangeAssignmentScreen" component={ChangeAssignmentScreen} options={{
          title: 'Assignments'
        }}/>
      </Stack.Navigator>
    </NavigationContainer>
    );
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});