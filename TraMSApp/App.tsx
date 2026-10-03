/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import { StatusBar, useColorScheme } from 'react-native';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import CreateGameScreen from './screens/CreateGameScreen';
import { useEffect, useState } from 'react';
import { fetchGames, init } from './utilities/sqlite';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import LoadGameScreen from './screens/LoadGameScreen';
import ChooseScenarioScreen from './screens/ChooseScenarioScreen';
import MainMenuScreen from './screens/MainMenuScreen';
import RouteScreen from './screens/overview/RouteScreen.tsx';
import FleetScreen from './screens/overview/FleetScreen.tsx';
import DriverScreen from './screens/overview/DriverScreen.tsx';
import RouteDetailScreen from './screens/RouteDetailScreen.tsx';
import VehicleScreen from './screens/VehicleScreen';
import AssignTourScreen from './screens/AssignTourScreen';
import ChangeAssignmentScreen from './screens/ChangeAssignmentScreen';
import { Game } from './models/game.ts';
import ScenarioScreen from './screens/overview/ScenarioScreen.tsx';
import LiveSituationScreen from './screens/overview/LiveSituationScreen.tsx';
import MessagesScreen from './screens/overview/MessagesScreen.tsx';

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
          console.log(games);
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
        <Stack.Screen name="RouteScreen" component={RouteScreen} options={{
          title: 'Routes'
        }}/>
        <Stack.Screen name="FleetScreen" component={FleetScreen} options={{
          title: 'Fleet'
        }}/>
        <Stack.Screen name="DriverScreen" component={DriverScreen} options={{
          title: 'Driver'
        }}/>
        <Stack.Screen name="LiveSituationScreen" component={LiveSituationScreen} options={{
          title: 'Live Situation'
        }}/>
        <Stack.Screen name="MessagesScreen" component={MessagesScreen} options={{
          title: 'Messages'
        }}/>
        <Stack.Screen name="ScenarioScreen" component={ScenarioScreen} options={{
          title: 'Scenario'
        }}/>
        <Stack.Screen name="RouteDetailScreen" component={RouteDetailScreen} options={{
          title: 'Route Details',
        }}/>
        <Stack.Screen name="VehicleScreen" component={VehicleScreen} options={{
          title: 'Vehicle Details',
          headerBackVisible: false
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
