import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { patientApi } from '../../api/patientApi';
import { doctorApi } from '../../api/doctorApi';
import { appointmentApi } from '../../api/appointmentApi';
import { billApi } from '../../api/billApi';
import { Users, User, Calendar, DollarSign, Activity, FileText } from 'lucide-react';

export const DashboardStats = () => {
    const { user } = useAuth();
    const [stats, setStats] = useState({
        totalPatients: 0,
        totalDoctors: 0,
        totalNurses: 0,
        totalAppointments: 0,
        todayAppointments: 0,
        pendingAppointments: 0,
        totalRevenue: 0,
        pendingBills: 0
    });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchStats();
    }, []);

    const fetchStats = async () => {
        try {
            setLoading(true);
            
            const [patientsRes, doctorsRes, apptsRes, billsRes] = await Promise.all([
                patientApi.getAll({ page: 0, size: 1 }),
                doctorApi.getAll({ page: 0, size: 1 }),
                appointmentApi.getAll({ page: 0, size: 100 }),
                billApi.getAll({ page: 0, size: 100 })
            ]);

            const appointments = apptsRes.data.data?.content || [];
            const bills = billsRes.data.data?.content || [];
            const today = new Date().toDateString();

            setStats({
                totalPatients: patientsRes.data.data?.totalElements || 0,
                totalDoctors: doctorsRes.data.data?.totalElements || 0,
                totalNurses: 0,
                totalAppointments: appointments.length,
                todayAppointments: appointments.filter(a => 
                    new Date(a.appointmentDate).toDateString() === today
                ).length,
                pendingAppointments: appointments.filter(a => a.status === 'PENDING').length,
                totalRevenue: bills
                    .filter(b => b.status === 'PAID')
                    .reduce((sum, b) => sum + (b.finalAmount || 0), 0),
                pendingBills: bills.filter(b => b.status === 'PENDING').length
            });
        } catch (error) {
            console.error('Error fetching stats:', error);
        } finally {
            setLoading(false);
        }
    };

    const statCards = [
        {
            title: 'Total Patients',
            value: stats.totalPatients,
            icon: Users,
            color: 'bg-blue-500',
            change: '+12%',
            changeColor: 'text-green-500'
        },
        {
            title: 'Total Doctors',
            value: stats.totalDoctors,
            icon: User,
            color: 'bg-green-500',
            change: '+5%',
            changeColor: 'text-green-500'
        },
        {
            title: "Today's Appointments",
            value: stats.todayAppointments,
            icon: Calendar,
            color: 'bg-purple-500',
            change: `${stats.pendingAppointments} pending`,
            changeColor: 'text-yellow-500'
        },
        {
            title: 'Total Revenue',
            value: `$${stats.totalRevenue.toFixed(2)}`,
            icon: DollarSign,
            color: 'bg-yellow-500',
            change: `${stats.pendingBills} bills pending`,
            changeColor: 'text-orange-500'
        },
        {
            title: 'Total Appointments',
            value: stats.totalAppointments,
            icon: Activity,
            color: 'bg-indigo-500',
            change: `${stats.pendingAppointments} pending`,
            changeColor: 'text-yellow-500'
        },
        {
            title: 'Pending Bills',
            value: stats.pendingBills,
            icon: FileText,
            color: 'bg-red-500',
            change: 'Need attention',
            changeColor: 'text-red-500'
        }
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

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                {statCards.map((card, index) => (
                    <div key={index} className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition-shadow">
                        <div className="flex items-center justify-between">
                            <div className={`p-3 rounded-lg ${card.color}`}>
                                <card.icon className="h-6 w-6 text-white" />
                            </div>
                            <span className={`text-sm font-medium ${card.changeColor}`}>
                                {card.change}
                            </span>
                        </div>
                        <div className="mt-4">
                            <p className="text-sm font-medium text-gray-600">{card.title}</p>
                            <p className="text-2xl font-semibold text-gray-900 mt-1">{card.value}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};
