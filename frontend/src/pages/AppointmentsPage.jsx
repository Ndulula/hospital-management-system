import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { AppointmentList } from '../components/appointments/AppointmentList';
import { AppointmentForm } from '../components/appointments/AppointmentForm';

export const AppointmentsPage = () => {
    return (
        <Routes>
            <Route index element={<AppointmentList />} />
            <Route path="/new" element={<AppointmentForm />} />
            <Route path="/edit/:id" element={<AppointmentForm />} />
        </Routes>
    );
};
