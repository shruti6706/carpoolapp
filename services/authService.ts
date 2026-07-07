import axios from 'axios';
import API_BASE_URL from '../constants/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    },
});

export const login = async (email: string, password: string) => {
    const response = await api.post('/api/auth/login', {
        email, password
    });
    return response.data;
};

export const register = async (
    name: string,
    email: string,
    password: string,
    phone: string,
    role: string) => {
    const resonse = await api.post('api/auth/register', {
        name, email, password, phone, role
    });
    return resonse.data;
}