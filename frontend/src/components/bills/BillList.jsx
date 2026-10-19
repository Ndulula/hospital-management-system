import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { billApi } from '../../api/billApi';
import toast from 'react-hot-toast';

export const BillList = () => {
    const [bills, setBills] = useState([]);
    const [loading, setLoading] = useState(true);
    const [filterStatus, setFilterStatus] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            setLoading(true);
            const billsRes = await billApi.getAll({ page: 0, size: 100 });
            setBills(billsRes.data.data?.content || []);
        } catch (error) {
            console.error('Error fetching data:', error);
            toast.error('Failed to load bills');
        } finally {
            setLoading(false);
        }
    };

    const handlePay = async (id) => {
        const method = prompt('Enter payment method (Cash, Credit Card, Insurance):');
        if (!method) return;
        try {
            await billApi.pay(id, method);
            toast.success('Bill marked as paid');
            fetchData();
        } catch (error) {
            toast.error('Failed to mark bill as paid');
        }
    };

    const getPatientName = (bill) => {
        const patient = bill.patient;
        return patient ? `${patient.firstName} ${patient.lastName}` : 'N/A';
    };

    const getStatusColor = (status) => {
        switch(status) {
            case 'PAID': return 'bg-green-100 text-green-800';
            case 'PENDING': return 'bg-yellow-100 text-yellow-800';
            case 'CANCELLED': return 'bg-red-100 text-red-800';
            default: return 'bg-gray-100 text-gray-800';
        }
    };

    const filteredBills = filterStatus 
        ? bills.filter(b => b.status === filterStatus)
        : bills;

    const totalRevenue = bills
        .filter(b => b.status === 'PAID')
        .reduce((sum, b) => sum + (b.finalAmount || 0), 0);

    if (loading) {
        return (
            <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
            </div>
        );
    }

    return (
        <div>
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold">Bills</h1>
                <button
                    onClick={() => navigate('/bills/new')}
                    className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                    + Generate Bill
                </button>
            </div>

            {/* Summary Cards */}
            <div className="grid grid-cols-3 gap-4 mb-6">
                <div className="bg-white rounded-lg shadow p-4">
                    <p className="text-sm text-gray-500">Total Bills</p>
                    <p className="text-2xl font-bold">{bills.length}</p>
                </div>
                <div className="bg-white rounded-lg shadow p-4">
                    <p className="text-sm text-gray-500">Pending</p>
                    <p className="text-2xl font-bold text-yellow-600">
                        {bills.filter(b => b.status === 'PENDING').length}
                    </p>
                </div>
                <div className="bg-white rounded-lg shadow p-4">
                    <p className="text-sm text-gray-500">Total Revenue</p>
                    <p className="text-2xl font-bold text-green-600">
                        ${totalRevenue.toFixed(2)}
                    </p>
                </div>
            </div>

            {/* Filter */}
            <div className="mb-6">
                <select
                    className="px-4 py-2 border rounded-lg"
                    value={filterStatus}
                    onChange={(e) => setFilterStatus(e.target.value)}
                >
                    <option value="">All Status</option>
                    <option value="PENDING">Pending</option>
                    <option value="PAID">Paid</option>
                    <option value="CANCELLED">Cancelled</option>
                </select>
            </div>

            <div className="bg-white rounded-lg shadow overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Bill #</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Patient</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Total</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Date</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Actions</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {filteredBills.length === 0 ? (
                            <tr>
                                <td colSpan="6" className="px-6 py-4 text-center text-gray-500">
                                    No bills found
                                </td>
                            </tr>
                        ) : (
                            filteredBills.map((bill) => (
                                <tr key={bill.id} className="hover:bg-gray-50">
                                    <td className="px-6 py-4 text-sm font-medium">{bill.billNumber}</td>
                                    <td className="px-6 py-4 text-sm">{getPatientName(bill)}</td>
                                    <td className="px-6 py-4 text-sm font-semibold">${bill.finalAmount?.toFixed(2) || 0}</td>
                                    <td className="px-6 py-4 text-sm">
                                        <span className={`px-2 py-1 rounded-full text-xs ${getStatusColor(bill.status)}`}>
                                            {bill.status}
                                        </span>
                                    </td>
                                    <td className="px-6 py-4 text-sm">
                                        {new Date(bill.createdAt).toLocaleDateString()}
                                    </td>
                                    <td className="px-6 py-4 text-sm space-x-2">
                                        {bill.status === 'PENDING' && (
                                            <button
                                                onClick={() => handlePay(bill.id)}
                                                className="text-green-600 hover:text-green-900"
                                            >
                                                Mark Paid
                                            </button>
                                        )}
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};
