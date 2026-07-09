import axios from 'axios';
import API_BASE_URL from '../constants/api';


//No token for login and register, so we create a separate instance of axios without the interceptor
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
    gender: string,
    role: string) => {
    const resonse = await api.post('/api/auth/register', {
        name, email, password, gender, role
    });
    return resonse.data;
}