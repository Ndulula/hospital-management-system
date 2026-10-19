import axiosConfig from './axiosConfig';

export const departmentApi = {
    getAll: () => axiosConfig.get('/departments'),
    getById: (id) => axiosConfig.get(`/departments/${id}`),
    create: (data) => axiosConfig.post('/departments', data),
    update: (id, data) => axiosConfig.put(`/departments/${id}`, data),
    delete: (id) => axiosConfig.delete(`/departments/${id}`),
};