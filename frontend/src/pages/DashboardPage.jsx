import React from 'react';
import { useNavigate } from 'react-router-dom';
import { CalendarPlus, FilePlus2, Stethoscope, UserPlus } from 'lucide-react';
import { DashboardStats } from '../components/dashboard/DashboardStats';
import { AppointmentChart } from '../components/dashboard/AppointmentChart';
import { RevenueChart } from '../components/dashboard/RevenueChart';

export const DashboardPage = () => {
    const navigate = useNavigate();
    const actions = [
        { label: 'Add Patient', description: 'Register a new patient', icon: UserPlus, path: '/patients/new' },
        { label: 'Book Appointment', description: 'Schedule a consultation', icon: CalendarPlus, path: '/appointments/new' },
        { label: 'Add Doctor', description: 'Register a clinician', icon: Stethoscope, path: '/doctors/new' },
        { label: 'Generate Bill', description: 'Create a patient invoice', icon: FilePlus2, path: '/bills/new' },
    ];

    return (
        <div>
            <DashboardStats />

            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mt-6">
                <AppointmentChart />
                <RevenueChart />
            </div>

            <div className="mt-6">
                <h2 className="text-xl font-semibold text-gray-900 mb-4">Quick Actions</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-4">
                    {actions.map(({ label, description, icon: Icon, path }) => (
                        <button
                            key={path}
                            type="button"
                            onClick={() => navigate(path)}
                            className="bg-white p-4 rounded-lg shadow hover:shadow-md transition-shadow text-left"
                        >
                            <Icon className="h-6 w-6 text-blue-600 mb-3" aria-hidden="true" />
                            <div className="font-medium">{label}</div>
                            <div className="text-sm text-gray-500">{description}</div>
                        </button>
                    ))}
                </div>
            </div>
        </div>
    );
};
