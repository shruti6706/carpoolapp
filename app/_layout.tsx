import { useEffect, useState } from 'react';
import { Stack } from 'expo-router';
import { getToken } from '../store/authStore';

export default function RootLayout() {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean | null>(null);

  useEffect(() => {
    const checkToken = async () => {
      const token = await getToken();
      setIsLoggedIn(!!token);
    };
    checkToken();
  }, []);

  if (isLoggedIn === null) return null;

  return (
    <Stack>
      <Stack.Screen name="index" options={{ headerShown: false }} />
      <Stack.Screen name="login" options={{ headerShown: false }} />
      <Stack.Screen name="register" options={{ headerShown: false }} />
      <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
    </Stack>
  );
}