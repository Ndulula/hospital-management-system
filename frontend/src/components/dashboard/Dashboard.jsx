import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { patientApi } from '../../api/patientApi';
import { appointmentApi } from '../../api/appointmentApi';
import { doctorApi } from '../../api/doctorApi';
import { billApi } from '../../api/billApi';

export const Dashboard = () => {
    const { user } = useAuth();
    const navigate = useNavigate();
    const [stats, setStats] = useState({
        totalPatients: 0,
        totalDoctors: 0,
        todayAppointments: 0,
        totalRevenue: 0,
    });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchStats();
    }, []);

    const fetchStats = async () => {
        try {
            setLoading(true);

            let totalPatients = 0;
            let totalDoctors = 0;
            let todayAppointments = 0;
            let totalRevenue = 0;

            try {
                const patientsRes = await patientApi.getAll({ page: 0, size: 1 });
                totalPatients = patientsRes.data.data?.totalElements || 0;
            } catch (error) {
                console.log('No patients data yet');
            }

            try {
                const doctorsRes = await doctorApi.getAll({ page: 0, size: 1 });
                totalDoctors = doctorsRes.data.data?.totalElements || 0;
            } catch (error) {
                console.log('No doctors data yet');
            }

            try {
                const appointmentsRes = await appointmentApi.getToday();
                todayAppointments = appointmentsRes.data.data?.length || 0;
            } catch (error) {
                console.log('No appointments data yet');
            }

            try {
                const revenueRes = await billApi.getTotalRevenue();
                totalRevenue = revenueRes.data.data || 0;
            } catch (error) {
                console.log('No revenue data yet');
            }

            setStats({
                totalPatients,
                totalDoctors,
                todayAppointments,
                totalRevenue,
            });
        } catch (error) {
            console.error('Error fetching stats:', error);
        } finally {
            setLoading(false);
        }
    };

    const cards = [
        {
            title: 'Total Patients',
            value: stats.totalPatients,
            label: 'Patients',
            color: 'bg-blue-500',
        },
        {
            title: 'Total Doctors',
            value: stats.totalDoctors,
            label: 'Doctors',
            color: 'bg-green-500',
        },
        {
            title: "Today's Appointments",
            value: stats.todayAppointments,
            label: 'Appts',
            color: 'bg-purple-500',
        },
        {
            title: 'Total Revenue',
            value: `$${stats.totalRevenue.toFixed(2)}`,
            label: 'Revenue',
            color: 'bg-yellow-500',
        },
    ];

    if (loading) {
        return (
            <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
            </div>
        );
    }

    return (
        <div>
            <div className="mb-8">
                <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
                <p className="text-gray-600">Welcome back, {user?.firstName}!</p>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                {cards.map((card) => (
                    <div key={card.title} className="bg-white rounded-lg shadow p-6">
                        <div className="flex items-center">
                            <div className={`w-16 h-12 flex items-center justify-center rounded-lg ${card.color} text-white text-xs font-semibold`}>
                                {card.label}
                            </div>
                            <div className="ml-4">
                                <p className="text-sm font-medium text-gray-600">{card.title}</p>
                                <p className="text-2xl font-semibold text-gray-900">{card.value}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            <div className="mt-8">
                <h2 className="text-xl font-semibold text-gray-900 mb-4">Quick Actions</h2>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                    <button
                        onClick={() => navigate('/patients/new')}
                        className="bg-white p-4 rounded-lg shadow hover:shadow-md transition-shadow text-left"
                    >
                        <div className="font-medium">Add Patient</div>
                        <div className="text-sm text-gray-500">Register a new patient</div>
                    </button>
                </div>
            </div>
        </div>
    );
};
