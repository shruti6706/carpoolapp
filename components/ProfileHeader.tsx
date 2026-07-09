import { View, Text, StyleSheet } from 'react-native';
import { Colors } from '../constants/colors';

type Props = {
    name: string;
    email: string;
    role: string;
    verified: boolean;
};

export default function ProfileHeader({ name, email, role, verified }: Props) {
    return (
        <View style={styles.container}>
            <View style={styles.avatar}>
                <Text style={styles.avatarText}>
                    {name?.charAt(0).toUpperCase()}
                </Text>
            </View>
            <Text style={styles.name}>{name}</Text>
            <Text style={styles.email}>{email}</Text>
            <View style={styles.badgeRow}>
                <View style={styles.badge}>
                    <Text style={styles.badgeText}>{role}</Text>
                </View>
                {verified && (
                    <View style={[styles.badge, styles.verifiedBadge]}>
                        <Text style={styles.badgeText}>✓ Verified</Text>
                    </View>
                )}
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: Colors.primary,
        padding: 32,
        alignItems: 'center',
        paddingTop: 60,
        paddingBottom: 32,
    },
    avatar: {
        width: 84,
        height: 84,
        borderRadius: 42,
        backgroundColor: Colors.white,
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: 12,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.15,
        shadowRadius: 4,
        elevation: 4,
    },
    avatarText: {
        fontSize: 36,
        fontWeight: 'bold',
        color: Colors.primary,
    },
    name: {
        fontSize: 22,
        fontWeight: 'bold',
        color: Colors.white,
        marginBottom: 4,
    },
    email: {
        fontSize: 14,
        color: Colors.white,
        opacity: 0.85,
        marginBottom: 12,
    },
    badgeRow: {
        flexDirection: 'row',
        gap: 8,
    },
    badge: {
        backgroundColor: 'rgba(255,255,255,0.2)',
        paddingHorizontal: 14,
        paddingVertical: 4,
        borderRadius: 20,
    },
    verifiedBadge: {
        backgroundColor: 'rgba(76,175,80,0.3)',
    },
    badgeText: {
        color: Colors.white,
        fontSize: 13,
        fontWeight: '600',
    },
});