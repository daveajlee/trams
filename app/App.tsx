/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import { StatusBar, useColorScheme } from 'react-native';
import { SafeAreaProvider, /*useSafeAreaInsets,*/ } from 'react-native-safe-area-context';
import CreateGameScreen from './screens/CreateGameScreen';
import { useEffect, useState } from 'react';
import { fetchGames, init } from './utilities/sqlite';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import LoadGameScreen from './screens/LoadGameScreen';
import IconButton from './utilities/IconButton';
import ChooseScenarioScreen from './screens/ChooseScenarioScreen';
import MainMenuScreen from './screens/MainMenuScreen';
import SearchRouteScreen from './screens/SearchRouteScreen';
import SearchFleetScreen from './screens/SearchFleetScreen';
import RouteScreen from './screens/RouteScreen';
import VehicleScreen from './screens/VehicleScreen';
import FleetScreen from './screens/FleetScreen';
import AssignTourScreen from './screens/AssignTourScreen';
import ChangeAssignmentScreen from './screens/ChangeAssignmentScreen';
import { Game } from './models/game.ts';

// Define stack navigation
const Stack = createNativeStackNavigator();

function App() {
  const isDarkMode = useColorScheme() === 'dark';

  return (
    <SafeAreaProvider>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <AppContent />
    </SafeAreaProvider>
  );
}

function AppContent() {
  //const safeAreaInsets = useSafeAreaInsets();

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
        (games: Game[]) => {
          setFirstScreen(!games || games.length === 0 ? 'CreateGameScreen' : 'LoadGameScreen');
          setLoading(false);
        }
      ).catch((error) => {
        setLoading(false);
        console.error('Setting default screen because of error ', error);
        setFirstScreen('CreateGameScreen');})
  }, []);

  if (!dbInitialized || loading) {
    return null;
  }

  return (
    <>
    <NavigationContainer>
      <Stack.Navigator initialRouteName={firstScreen}>
        <Stack.Screen name="CreateGameScreen" component={CreateGameScreen} options={() => ({
          headerShown: false
        })}/>
        <Stack.Screen name="LoadGameScreen" component={LoadGameScreen} options={() => ({
          title: 'Saved Games'
          })}/>
        <Stack.Screen name="ChooseScenarioScreen" component={ChooseScenarioScreen} options={() => ({
          title: 'Choose Scenario'
        })}/>
        <Stack.Screen name="MainMenuScreen" component={MainMenuScreen} options={{
          title: 'Game Menu',
          headerBackVisible: false,
          }}/>
        <Stack.Screen name="SearchRouteScreen" component={SearchRouteScreen} options={{
          title: 'Search by Route Number'
        }}/>
        <Stack.Screen name="SearchFleetScreen" component={SearchFleetScreen} options={{
          title: 'Search by Fleet Number'
        }}/>
        <Stack.Screen name="RouteScreen" component={RouteScreen} options={{
          title: 'Route Details',
          headerBackVisible: false,
        }}/>
        <Stack.Screen name="VehicleScreen" component={VehicleScreen} options={{
          title: 'Vehicle Details',
          headerBackVisible: false
        }}/>
        <Stack.Screen name="FleetScreen" component={FleetScreen} options={{
          title: 'Fleet Overview'
        }}/>
        <Stack.Screen name="AssignTourScreen" component={AssignTourScreen} options={{
          title: 'Assign Routes and Vehicles'
        }}/>
        <Stack.Screen name="ChangeAssignmentScreen" component={ChangeAssignmentScreen} options={{
          title: 'Assignments',
          headerBackVisible: false
        }}/>
      </Stack.Navigator>
    </NavigationContainer>
    </>
    );
}

export default App;
