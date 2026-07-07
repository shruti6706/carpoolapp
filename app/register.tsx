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
import { register } from '../services/authService';
import { saveToken } from '../store/authStore';
import { login } from "../services/authService";

export default function RegisterScreen() {
    const router = useRouter();
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [gender, setGender] = useState('');
    const [role, setRole] = useState('');
    const [loading, setLoading] = useState(false);

    const handleRegister = async () => {
        if (!name || !email || !password || !gender || !role) {
            Alert.alert('Error', 'Please fill in all fields');
            return;
        }

        setLoading(true);
        try {
            const data = await register(name, email, password, gender, role);
            await saveToken(data.token);
            router.replace('/(tabs)');
        } catch (error: any) {
            Alert.alert(
                'Registration Failed',
                error.response?.data?.message || 'Something went wrong'
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
                    <Text style={styles.title}>Create Account</Text>
                    <Text style={styles.subtitle}>Fill in your details to get started</Text>
                </View>

                {/* Form */}
                <View style={styles.form}>

                    <Text style={styles.label}>Full Name</Text>
                    <TextInput
                        style={styles.input}
                        placeholder="Enter your name"
                        placeholderTextColor="#999"
                        value={name}
                        onChangeText={setName}
                    />

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

                    {/* Gender Selector */}
                    <Text style={styles.label}>Gender</Text>
                    <View style={styles.selectorRow}>
                        {['MALE', 'FEMALE', 'OTHER'].map((option) => (
                            <TouchableOpacity
                                key={option}
                                style={[
                                    styles.selectorButton,
                                    gender === option && styles.selectorButtonActive,
                                ]}
                                onPress={() => setGender(option)}>
                                <Text style={[
                                    styles.selectorText,
                                    gender === option && styles.selectorTextActive,
                                ]}>
                                    {option}
                                </Text>
                            </TouchableOpacity>
                        ))}
                    </View>

                    {/* Role Selector */}
                    <Text style={styles.label}>I want to</Text>
                    <View style={styles.selectorRow}>
                        {[
                            { label: 'Offer Rides', value: 'DRIVER' },
                            { label: 'Find Rides', value: 'RIDER' },
                            { label: 'Both', value: 'BOTH' },
                        ].map((option) => (
                            <TouchableOpacity
                                key={option.value}
                                style={[
                                    styles.selectorButton,
                                    role === option.value && styles.selectorButtonActive,
                                ]}
                                onPress={() => setRole(option.value)}>
                                <Text style={[
                                    styles.selectorText,
                                    role === option.value && styles.selectorTextActive,
                                ]}>
                                    {option.label}
                                </Text>
                            </TouchableOpacity>
                        ))}
                    </View>

                    <TouchableOpacity
                        style={styles.button}
                        onPress={handleRegister}
                        disabled={loading}>
                        {loading
                            ? <ActivityIndicator color="#fff" />
                            : <Text style={styles.buttonText}>Create Account</Text>}
                    </TouchableOpacity>

                    <TouchableOpacity onPress={() => router.push('/login' as any)}>
                        <Text style={styles.link}>
                            Already have an account?{' '}
                            <Text style={styles.linkBold}>Login</Text>
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
    selectorRow: {
        flexDirection: 'row',
        gap: 8,
        marginBottom: 16,
    },
    selectorButton: {
        flex: 1,
        borderWidth: 1,
        borderColor: '#ddd',
        borderRadius: 8,
        padding: 12,
        alignItems: 'center',
    },
    selectorButtonActive: {
        backgroundColor: '#1a73e8',
        borderColor: '#1a73e8',
    },
    selectorText: {
        color: '#666',
        fontSize: 13,
        fontWeight: '600',
    },
    selectorTextActive: {
        color: '#fff',
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