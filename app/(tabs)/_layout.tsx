import { Tabs } from 'expo-router';

export default function TabLayout() {
  return (
    <Tabs
      screenOptions={{
        headerShown: false,
        tabBarActiveTintColor: '#1a73e8',
        tabBarInactiveTintColor: '#999',
        tabBarStyle: {
          backgroundColor: '#fff',
          borderTopWidth: 1,
          borderTopColor: '#eee',
          paddingBottom: 8,
          paddingTop: 8,
          height: 60,
        },
      }}>

      <Tabs.Screen
        name="index"
        options={{
          title: 'Search',
          tabBarIcon: () => <></>,
        }}
      />

      <Tabs.Screen
        name="bookings"
        options={{
          title: 'Bookings',
          tabBarIcon: () => <></>,
        }}
      />

      <Tabs.Screen
        name="create-ride"
        options={{
          title: 'Create',
          tabBarIcon: () => <></>,
        }}
      />

      <Tabs.Screen
        name="incoming"
        options={{
          title: 'Requests',
          tabBarIcon: () => <></>,
        }}
      />

      <Tabs.Screen
        name="profile"
        options={{
          title: 'Profile',
          tabBarIcon: () => <></>,
        }}
      />

    </Tabs>
  );
}