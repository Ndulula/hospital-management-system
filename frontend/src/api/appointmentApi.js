import axiosConfig from './axiosConfig';

export const appointmentApi = {
    getAll: (params) => axiosConfig.get('/appointments', { params }),
    getById: (id) => axiosConfig.get(`/appointments/${id}`),
    create: (data) => axiosConfig.post('/appointments', data),
    cancel: (id) => axiosConfig.patch(`/appointments/${id}/cancel`),
    complete: (id) => axiosConfig.patch(`/appointments/${id}/complete`),
    getByPatient: (patientId) => axiosConfig.get(`/appointments/patient/${patientId}`),
    getByDoctor: (doctorId) => axiosConfig.get(`/appointments/doctor/${doctorId}`),
    getToday: () => axiosConfig.get('/appointments/today'),
    getByStatus: (status) => axiosConfig.get(`/appointments/status/${status}`),
    checkAvailability: (doctorId, dateTime) => 
        axiosConfig.get('/appointments/check-availability', { params: { doctorId, dateTime } }),
};