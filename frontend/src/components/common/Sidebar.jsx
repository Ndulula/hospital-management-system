import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export const Sidebar = () => {
    const location = useLocation();
    const { hasRole } = useAuth();

    const isActive = (path) => location.pathname.startsWith(path);

    const menuItems = [
        { path: '/dashboard', label: 'Dashboard', marker: 'DB', roles: ['ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST'] },
        { path: '/patients', label: 'Patients', marker: 'PT', roles: ['ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST'] },
        { path: '/doctors', label: 'Doctors', marker: 'DR', roles: ['ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST'] },
        { path: '/appointments', label: 'Appointments', marker: 'AP', roles: ['ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST'] },
        { path: '/bills', label: 'Bills', marker: 'BL', roles: ['ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST'] },
        { path: '/profile', label: 'Profile', marker: 'PR', roles: ['ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST'] }
    ];

    const filteredMenu = menuItems.filter((item) =>
        item.roles.some((role) => hasRole(role))
    );

    return (
        <div className="w-64 bg-gray-800 min-h-screen p-4">
            <div className="text-white text-xl font-bold mb-8">
                HMS
            </div>
            <nav className="space-y-2">
                {filteredMenu.map((item) => (
                    <Link
                        key={item.path}
                        to={item.path}
                        className={`flex items-center space-x-3 px-4 py-2 rounded-lg transition-colors ${
                            isActive(item.path)
                                ? 'bg-blue-600 text-white'
                                : 'text-gray-300 hover:bg-gray-700 hover:text-white'
                        }`}
                    >
                        <span className="w-8 text-xs font-semibold">{item.marker}</span>
                        <span>{item.label}</span>
                    </Link>
                ))}
            </nav>
        </div>
    );
};
