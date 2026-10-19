import axiosConfig from './axiosConfig';

export const billApi = {
    getAll: (params) => axiosConfig.get('/bills', { params }),
    getById: (id) => axiosConfig.get(`/bills/${id}`),
    getByPatient: (patientId) => axiosConfig.get(`/bills/patient/${patientId}`),
    getByStatus: (status) => axiosConfig.get(`/bills/status/${status}`),
    getPending: () => axiosConfig.get('/bills/pending'),
    getByNumber: (billNumber) => axiosConfig.get(`/bills/number/${billNumber}`),
    create: (data) => axiosConfig.post('/bills', data),
    pay: (id, paymentMethod) => axiosConfig.patch(`/bills/${id}/pay`, null, { 
        params: { paymentMethod } 
    }),
    getTotalRevenue: () => axiosConfig.get('/bills/revenue/total'),
    getRevenueBetween: (start, end) => axiosConfig.get('/bills/revenue/range', { 
        params: { start, end } 
    }),
};
