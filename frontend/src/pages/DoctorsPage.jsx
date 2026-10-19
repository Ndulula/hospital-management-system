import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { DoctorList } from '../components/doctors/DoctorList';
import { DoctorForm } from '../components/doctors/DoctorForm';

export const DoctorsPage = () => {
    return (
        <Routes>
            <Route index element={<DoctorList />} />
            <Route path="/new" element={<DoctorForm />} />
            <Route path="/edit/:id" element={<DoctorForm />} />
        </Routes>
    );
};
