import axiosConfig from './axiosConfig';

export const authApi = {
    register: (userData) => axiosConfig.post('/auth/register', userData),
    login: (credentials) => axiosConfig.post('/auth/login', credentials),
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },
};