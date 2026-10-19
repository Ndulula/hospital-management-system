import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { NotificationBell } from './NotificationBell';

export const Navbar = () => {
    const { user, logout } = useAuth();

    return (
        <nav className="bg-white shadow">
            <div className="px-4 py-3 flex justify-between items-center">
                <div className="flex items-center">
                    <h1 className="text-xl font-semibold text-gray-800">
                        Hospital Management
                    </h1>
                </div>
                <div className="flex items-center space-x-4">
                    <NotificationBell />
                    <span className="text-sm text-gray-600">
                        {user?.firstName} {user?.lastName}
                    </span>
                    <button
                        onClick={logout}
                        className="text-sm text-red-600 hover:text-red-800"
                    >
                        Logout
                    </button>
                </div>
            </div>
        </nav>
    );
};