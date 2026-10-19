import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { PatientList } from '../components/patients/PatientList';
import { PatientForm } from '../components/patients/PatientForm';

export const PatientsPage = () => {
    return (
        <Routes>
            <Route index element={<PatientList />} />
            <Route path="/new" element={<PatientForm />} />
            <Route path="/edit/:id" element={<PatientForm />} />
        </Routes>
    );
};
