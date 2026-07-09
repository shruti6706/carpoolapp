import api from './api';

export const getMyProfile = async () => {
    const response = await api.get('/api/users/me');
    return response.data;
};

export const updateMyProfile = async (data: {
    name?: string;
    phone?: string;
    role?: string;
}) => {
    const repsonse = await api.put('/api/users/me', data);
    return repsonse.data;
}