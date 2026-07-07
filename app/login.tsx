import { useState } from 'react';
import {
    View,
    Text,
    TextInput,
    TouchableOpacity,
    StyleSheet,
    Alert,
    ActivityIndicator,
    KeyboardAvoidingView,
    Platform,
    ScrollView,
} from 'react-native';
import { useRouter } from 'expo-router';
import { login } from '../services/authService';
import { saveToken } from '../store/authStore';

export default function LoginScreen() {
    const router = useRouter();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);

    const handleLogin = async () => {
        if (!email || !password) {
            Alert.alert('Error', 'Please fill in all fields');
            return;
        }

        setLoading(true);
        try {
            const data = await login(email, password);
            await saveToken(data.token);
            router.replace('/(tabs)');
        } catch (error: any) {
            Alert.alert(
                'Login Failed',
                error.response?.data?.message || 'Invalid credentials'
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <KeyboardAvoidingView
            style={styles.container}
            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}>
            <ScrollView contentContainerStyle={styles.inner}>

                {/* Header */}
                <View style={styles.header}>
                    <TouchableOpacity onPress={() => router.back()}>
                        <Text style={styles.backButton}>← Back</Text>
                    </TouchableOpacity>
                </View>

                {/* Title */}
                <View style={styles.titleContainer}>
                    <Text style={styles.title}>Welcome back</Text>
                    <Text style={styles.subtitle}>Sign in to your account</Text>
                </View>

                {/* Form */}
                <View style={styles.form}>
                    <Text style={styles.label}>Email</Text>
                    <TextInput
                        style={styles.input}
                        placeholder="Enter your email"
                        placeholderTextColor="#999"
                        value={email}
                        onChangeText={setEmail}
                        keyboardType="email-address"
                        autoCapitalize="none"
                    />

                    <Text style={styles.label}>Password</Text>
                    <TextInput
                        style={styles.input}
                        placeholder="Enter your password"
                        placeholderTextColor="#999"
                        value={password}
                        onChangeText={setPassword}
                        secureTextEntry
                    />

                    <TouchableOpacity
                        style={styles.button}
                        onPress={handleLogin}
                        disabled={loading}>
                        {loading
                            ? <ActivityIndicator color="#fff" />
                            : <Text style={styles.buttonText}>Login</Text>}
                    </TouchableOpacity>

                    <TouchableOpacity onPress={() => router.push('/register' as any)}>
                        <Text style={styles.link}>
                            Don't have an account?{' '}
                            <Text style={styles.linkBold}>Register</Text>
                        </Text>
                    </TouchableOpacity>
                </View>

            </ScrollView>
        </KeyboardAvoidingView>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
    },
    inner: {
        flexGrow: 1,
        padding: 24,
    },
    header: {
        marginTop: 16,
        marginBottom: 32,
    },
    backButton: {
        fontSize: 16,
        color: '#1a73e8',
    },
    titleContainer: {
        marginBottom: 32,
    },
    title: {
        fontSize: 28,
        fontWeight: 'bold',
        color: '#333',
        marginBottom: 8,
    },
    subtitle: {
        fontSize: 16,
        color: '#666',
    },
    form: {
        gap: 8,
    },
    label: {
        fontSize: 14,
        fontWeight: '600',
        color: '#333',
        marginBottom: 4,
    },
    input: {
        borderWidth: 1,
        borderColor: '#ddd',
        borderRadius: 8,
        padding: 14,
        fontSize: 16,
        color: '#333',
        marginBottom: 16,
    },
    button: {
        backgroundColor: '#1a73e8',
        padding: 16,
        borderRadius: 8,
        alignItems: 'center',
        marginTop: 8,
        marginBottom: 16,
    },
    buttonText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    },
    link: {
        textAlign: 'center',
        color: '#666',
        fontSize: 14,
    },
    linkBold: {
        color: '#1a73e8',
        fontWeight: 'bold',
    },
});