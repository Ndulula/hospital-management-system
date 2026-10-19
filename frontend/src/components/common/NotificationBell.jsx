import React, { useEffect, useRef, useState } from 'react';
import { Bell, CalendarClock, CircleAlert, ReceiptText } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { useNotifications } from '../../context/NotificationContext';

export const NotificationBell = () => {
    const [isOpen, setIsOpen] = useState(false);
    const dropdownRef = useRef(null);
    const { notifications, unreadCount, markAsRead, markAllAsRead, clearNotifications } = useNotifications();
    const navigate = useNavigate();

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setIsOpen(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    const openNotification = (notification) => {
        markAsRead(notification.id);
        setIsOpen(false);
        navigate(notification.link);
    };

    const getIcon = (type) => {
        if (type === 'appointment') return CalendarClock;
        if (type === 'bill') return ReceiptText;
        return CircleAlert;
    };

    return (
        <div className="relative" ref={dropdownRef}>
            <button
                type="button"
                aria-label="Notifications"
                onClick={() => setIsOpen((open) => !open)}
                className="relative p-2 text-gray-600 hover:text-gray-800 focus:outline-none"
            >
                <Bell className="h-6 w-6" aria-hidden="true" />
                {unreadCount > 0 && (
                    <span className="absolute top-0 right-0 transform translate-x-1 -translate-y-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">
                        {unreadCount > 9 ? '9+' : unreadCount}
                    </span>
                )}
            </button>

            {isOpen && (
                <div className="absolute right-0 mt-2 w-80 bg-white rounded-lg shadow-lg border z-50 max-h-96 flex flex-col">
                    <div className="p-3 border-b flex justify-between items-center">
                        <h3 className="font-semibold text-gray-900">Notifications</h3>
                        <div className="flex space-x-2">
                            {unreadCount > 0 && <button type="button" onClick={markAllAsRead} className="text-xs text-blue-600 hover:text-blue-800">Mark all read</button>}
                            <button type="button" onClick={clearNotifications} className="text-xs text-gray-500 hover:text-gray-700">Clear all</button>
                        </div>
                    </div>

                    <div className="flex-1 overflow-y-auto">
                        {notifications.length === 0 ? (
                            <div className="p-4 text-center text-gray-500 text-sm">No notifications</div>
                        ) : notifications.map((notification) => {
                            const Icon = getIcon(notification.type);
                            return (
                                <button
                                    key={notification.id}
                                    type="button"
                                    onClick={() => openNotification(notification)}
                                    className={`w-full p-3 border-b hover:bg-gray-50 text-left ${!notification.read ? 'bg-blue-50' : ''}`}
                                >
                                    <div className="flex items-start space-x-2">
                                        <Icon className="h-5 w-5 text-gray-500 flex-shrink-0" aria-hidden="true" />
                                        <div className="flex-1 min-w-0">
                                            <p className="text-sm text-gray-800">{notification.message}</p>
                                            <p className="text-xs text-gray-400 mt-1">{new Date(notification.date).toLocaleDateString()}</p>
                                        </div>
                                        {!notification.read && <span className="w-2 h-2 bg-blue-500 rounded-full flex-shrink-0 mt-1" />}
                                    </div>
                                </button>
                            );
                        })}
                    </div>
                </div>
            )}
        </div>
    );
};
