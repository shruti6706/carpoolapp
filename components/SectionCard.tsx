import { View, Text, StyleSheet } from 'react-native';
import { Colors } from '../constants/colors';

type Props = {
    title: string;
    children: React.ReactNode;
};

export default function SectionCard({ title, children }: Props) {
    return (
        <View style={styles.container}>
            <Text style={styles.title}>{title}</Text>
            {children}
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: Colors.white,
        margin: 16,
        marginBottom: 0,
        borderRadius: 12,
        padding: 16,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.05,
        shadowRadius: 4,
        elevation: 2,
    },
    title: {
        fontSize: 16,
        fontWeight: 'bold',
        color: Colors.textDark,
        marginBottom: 16,
    },
});