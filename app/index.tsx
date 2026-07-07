import {
    View,
    Text,
    TouchableOpacity,
    StyleSheet,
    Image,
} from 'react-native';
import { useRouter } from 'expo-router';

export default function WelcomeScreen() {
    const router = useRouter();

    return (
        <View style={styles.container}>

            {/* Logo / App Name */}
            <View style={styles.logoContainer}>
                <Text style={styles.logo}>🚗</Text>
                <Text style={styles.appName}>CarpoolApp</Text>
                <Text style={styles.tagline}>Safe, Smart & Shared Rides</Text>
            </View>

            {/* Buttons */}
            <View style={styles.buttonContainer}>

                <TouchableOpacity
                    style={styles.primaryButton}
                    onPress={() => router.push('/login' as any)}>
                    <Text style={styles.primaryButtonText}>Continue with Email</Text>
                </TouchableOpacity>

                <View style={styles.divider}>
                    <View style={styles.line} />
                    <Text style={styles.orText}>or</Text>
                    <View style={styles.line} />
                </View>

                <TouchableOpacity
                    style={styles.secondaryButton}
                    onPress={() => router.push('/register' as any)}>
                    <Text style={styles.secondaryButtonText}>Create an Account</Text>
                </TouchableOpacity>

            </View>

        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        justifyContent: 'space-between',
        padding: 24,
        paddingBottom: 48,
    },
    logoContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    logo: {
        fontSize: 80,
        marginBottom: 16,
    },
    appName: {
        fontSize: 36,
        fontWeight: 'bold',
        color: '#1a73e8',
        marginBottom: 8,
    },
    tagline: {
        fontSize: 16,
        color: '#666',
        textAlign: 'center',
    },
    buttonContainer: {
        gap: 12,
    },
    primaryButton: {
        backgroundColor: '#1a73e8',
        padding: 16,
        borderRadius: 8,
        alignItems: 'center',
    },
    primaryButtonText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    },
    divider: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 8,
    },
    line: {
        flex: 1,
        height: 1,
        backgroundColor: '#ddd',
    },
    orText: {
        color: '#999',
        fontSize: 14,
    },
    secondaryButton: {
        borderWidth: 1,
        borderColor: '#1a73e8',
        padding: 16,
        borderRadius: 8,
        alignItems: 'center',
    },
    secondaryButtonText: {
        color: '#1a73e8',
        fontSize: 16,
        fontWeight: 'bold',
    },
});