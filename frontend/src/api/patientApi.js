import axiosConfig from './axiosConfig';

export const patientApi = {
    getAll: (params) => axiosConfig.get('/patients', { params }),
    getById: (id) => axiosConfig.get(`/patients/${id}`),
    getByPatientId: (patientId) => axiosConfig.get(`/patients/patient-id/${patientId}`),
    create: (data) => axiosConfig.post('/patients', data),
    update: (id, data) => axiosConfig.put(`/patients/${id}`, data),
    delete: (id) => axiosConfig.delete(`/patients/${id}`),
    search: (searchTerm) => axiosConfig.get('/patients/search', { params: { searchTerm } }),
    getByBloodGroup: (bloodGroup) => axiosConfig.get(`/patients/blood-group/${bloodGroup}`),
};