import React, { createContext, useContext, useEffect, useState } from 'react';
import { appointmentApi } from '../api/appointmentApi';
import { billApi } from '../api/billApi';

const NotificationContext = createContext();

export const useNotifications = () => useContext(NotificationContext);

export const NotificationProvider = ({ children }) => {
    const [notifications, setNotifications] = useState([]);
    const [unreadCount, setUnreadCount] = useState(0);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchNotifications();
        const interval = setInterval(fetchNotifications, 120000);
        return () => clearInterval(interval);
    }, []);

    const fetchNotifications = async () => {
        try {
            setLoading(true);
            const [appointmentsResponse, billsResponse] = await Promise.all([
                appointmentApi.getAll({ page: 0, size: 100 }),
                billApi.getAll({ page: 0, size: 100 }),
            ]);

            const appointments = appointmentsResponse.data.data?.content || [];
            const bills = billsResponse.data.data?.content || [];
            const now = new Date();
            const nextWeek = new Date(now);
            nextWeek.setDate(nextWeek.getDate() + 7);

            const upcomingAppointments = appointments
                .filter((appointment) => {
                    const date = new Date(appointment.appointmentDate);
                    return date >= now && date <= nextWeek && appointment.status !== 'CANCELLED';
                })
                .map((appointment) => {
                    const daysUntil = Math.ceil((new Date(appointment.appointmentDate) - now) / 86400000);
                    const patientName = appointment.patient
                        ? `${appointment.patient.firstName} ${appointment.patient.lastName}`
                        : 'a patient';
                    const timing = daysUntil === 0 ? 'today' : daysUntil === 1 ? 'tomorrow' : `in ${daysUntil} days`;
                    return {
                        id: `appointment-${appointment.id}`,
                        type: 'appointment',
                        message: `Appointment with ${patientName} is ${timing}.`,
                        date: appointment.appointmentDate,
                        read: false,
                        link: '/appointments',
                    };
                });

            const pendingBills = bills
                .filter((bill) => bill.status === 'PENDING')
                .map((bill) => ({
                    id: `bill-${bill.id}`,
                    type: 'bill',
                    message: `Bill #${bill.billNumber} is pending payment: $${(bill.finalAmount || 0).toFixed(2)}.`,
                    date: bill.createdAt,
                    read: false,
                    link: '/bills',
                }));

            const nextNotifications = [...upcomingAppointments, ...pendingBills]
                .sort((left, right) => new Date(right.date) - new Date(left.date))
                .slice(0, 20);

            setNotifications(nextNotifications);
            setUnreadCount(nextNotifications.length);
        } catch (error) {
            console.error('Error fetching notifications:', error);
        } finally {
            setLoading(false);
        }
    };

    const markAsRead = (id) => {
        setNotifications((current) => current.map((notification) =>
            notification.id === id ? { ...notification, read: true } : notification
        ));
        setUnreadCount((current) => Math.max(0, current - 1));
    };

    const markAllAsRead = () => {
        setNotifications((current) => current.map((notification) => ({ ...notification, read: true })));
        setUnreadCount(0);
    };

    const clearNotifications = () => {
        setNotifications([]);
        setUnreadCount(0);
    };

    return (
        <NotificationContext.Provider value={{
            notifications,
            unreadCount,
            loading,
            markAsRead,
            markAllAsRead,
            clearNotifications,
            fetchNotifications,
        }}>
            {children}
        </NotificationContext.Provider>
    );
};
