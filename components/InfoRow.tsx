import { View, Text, StyleSheet } from 'react-native';
import { Colors } from '../constants/colors';

type Props = {
    label: string;
    value: string;
};

export default function InfoRow({ label, value }: Props) {
    return (
        <View style={styles.container}>
            <Text style={styles.label}>{label}</Text>
            <Text style={styles.value}>{value}</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingVertical: 12,
        borderBottomWidth: 1,
        borderBottomColor: Colors.border,
    },
    label: {
        fontSize: 14,
        color: Colors.textMedium,
    },
    value: {
        fontSize: 14,
        color: Colors.textDark,
        fontWeight: '500',
    },
});