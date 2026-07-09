import {
  ScrollView,
  TouchableOpacity,
  Text,
  StyleSheet,
  View,
  ActivityIndicator,
  Alert,
} from 'react-native';
import { useRouter } from 'expo-router';
import { useProfile } from '../../hooks/useProfile';
import { useAuth } from '../../hooks/useAuth';
import { removeToken } from '../../store/authStore';
import { Colors } from '../../constants/colors';
import ProfileHeader from '../../components/ProfileHeader';
import SectionCard from '../../components/SectionCard';
import InfoRow from '../../components/InfoRow';
import MenuItem from '../../components/MenuItem';

export default function ProfileScreen() {
  const router = useRouter();
  const { profile, loading } = useProfile();
  useAuth();

  const handleLogout = () => {
    Alert.alert('Logout', 'Are you sure you want to logout?', [
      { text: 'Cancel', style: 'cancel' },
      {
        text: 'Logout',
        style: 'destructive',
        onPress: async () => {
          await removeToken();
          router.replace('/' as any);
        },
      },
    ]);
  };

  if (loading) {
    return (
      <View style={styles.centered}>
        <ActivityIndicator size="large" color={Colors.primary} />
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>

      <ProfileHeader
        name={profile?.name ?? ''}
        email={profile?.email ?? ''}
        role={profile?.role ?? ''}
        verified={profile?.verified ?? false}
      />

      <SectionCard title="Personal Info">
        <InfoRow
          label="Phone"
          value={profile?.phone ?? 'Not added'}
        />
        <InfoRow
          label="Gender"
          value={profile?.gender ?? ''}
        />
      </SectionCard>

      <SectionCard title="Account">
        <MenuItem
          icon="create-outline"
          label="Edit Profile"
          onPress={() => router.push('/edit-profile' as any)}
        />
        <MenuItem
          icon="document-outline"
          label="Vehicle Documents"
          onPress={() => {}}
        />
        <MenuItem
          icon="information-circle-outline"
          label="About"
          onPress={() => {}}
        />
      </SectionCard>

      <SectionCard title="Danger Zone">
        <MenuItem
          icon="log-out-outline"
          label="Logout"
          onPress={handleLogout}
          danger
        />
      </SectionCard>

      <View style={styles.bottomSpace} />

    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.surface,
  },
  centered: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: Colors.background,
  },
  bottomSpace: {
    height: 32,
  },
});