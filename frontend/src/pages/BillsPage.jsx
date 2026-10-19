import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { BillList } from '../components/bills/BillList';
import { BillForm } from '../components/bills/BillForm';

export const BillsPage = () => {
    return (
        <Routes>
            <Route index element={<BillList />} />
            <Route path="/new" element={<BillForm />} />
            <Route path="/:id" element={<BillForm />} />
        </Routes>
    );
};
