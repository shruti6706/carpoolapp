import { useState, useEffect } from 'react';
import { Alert } from 'react-native';
import { getMyProfile } from '../services/userService';

export function useProfile() {
    const [profile, setProfile] = useState<any>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const fetchProfile = async () => {
        try {
            setLoading(true);
            const data = await getMyProfile();
            setProfile(data);
        } catch {
            setError('Failed to load profile');
            Alert.alert('Error', 'Failed to load profile');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProfile();
    }, []);

    return { profile, loading, error, refetch: fetchProfile };
}