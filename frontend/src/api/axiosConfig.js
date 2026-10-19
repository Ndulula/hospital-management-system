import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const axiosConfig = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add token to every request
axiosConfig.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Handle token expiration
axiosConfig.interceptors.response.use(
    (response) => response,
    (error) => {
        const isAuthRequest = error.config?.url?.startsWith('/auth/');
        const isPublicAuthPage = ['/login', '/register'].includes(window.location.pathname);

        if (error.response?.status === 401 && !isAuthRequest && !isPublicAuthPage) {
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.replace('/login');
        }
        return Promise.reject(error);
    }
);

export default axiosConfig;
