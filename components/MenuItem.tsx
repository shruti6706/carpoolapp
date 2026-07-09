import { TouchableOpacity, Text, StyleSheet } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { Colors } from '../constants/colors';

type Props = {
    icon: React.ComponentProps<typeof Ionicons>['name'];
    label: string;
    onPress?: () => void;
    danger?: boolean;
};

export default function MenuItem({ icon, label, onPress, danger }: Props) {
    return (
        <TouchableOpacity style={styles.container} onPress={onPress}>
            <Ionicons
                name={icon}
                size={20}
                color={danger ? Colors.error : Colors.primary}
                style={styles.icon}
            />
            <Text style={[styles.label, danger && styles.danger]}>{label}</Text>
            <Ionicons name="chevron-forward" size={16} color={Colors.textLight} />
        </TouchableOpacity>
    );
}

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        alignItems: 'center',
        paddingVertical: 14,
        borderBottomWidth: 1,
        borderBottomColor: Colors.border,
    },
    icon: {
        marginRight: 12,
    },
    label: {
        flex: 1,
        fontSize: 15,
        color: Colors.textDark,
    },
    danger: {
        color: Colors.error,
    },
});