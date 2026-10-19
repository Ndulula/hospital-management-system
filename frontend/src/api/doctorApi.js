import axiosConfig from './axiosConfig';

export const doctorApi = {
    getAll: (params) => axiosConfig.get('/doctors', { params }),
    getById: (id) => axiosConfig.get(`/doctors/${id}`),
    create: (data) => axiosConfig.post('/doctors', data),
    update: (id, data) => axiosConfig.put(`/doctors/${id}`, data),
    delete: (id) => axiosConfig.delete(`/doctors/${id}`),
    getBySpecialization: (specialization) => axiosConfig.get(`/doctors/specialization/${specialization}`),
    getByDepartment: (departmentId) => axiosConfig.get(`/doctors/department/${departmentId}`),
    getAvailable: () => axiosConfig.get('/doctors/available'),
};