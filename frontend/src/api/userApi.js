import axiosConfig from './axiosConfig';

export const userApi = {
    getProfile: () => axiosConfig.get('/users/profile'),
    updateProfile: (data) => axiosConfig.put('/users/profile', data),
    changePassword: (data) => axiosConfig.patch('/users/change-password', data),
};