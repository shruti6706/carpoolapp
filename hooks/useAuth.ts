import { useEffect } from 'react';
import { useRouter } from 'expo-router';
import { getToken } from '../store/authStore';

export function useAuth() {
    const router = useRouter();

    useEffect(() => {
        const checkAuth = async () => {
            const token = await getToken();
            if (!token) {
                router.replace('/' as any);
            }
        };
        checkAuth();
    }, []);
}