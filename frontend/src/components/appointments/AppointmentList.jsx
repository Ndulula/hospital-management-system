import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { appointmentApi } from '../../api/appointmentApi';
import toast from 'react-hot-toast';

export const AppointmentList = () => {
    const [appointments, setAppointments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [filterStatus, setFilterStatus] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            setLoading(true);
            const apptsRes = await appointmentApi.getAll({ page: 0, size: 100 });
            setAppointments(apptsRes.data.data?.content || []);
        } catch (error) {
            console.error('Error fetching data:', error);
            toast.error('Failed to load appointments');
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = async (id) => {
        if (!window.confirm('Cancel this appointment?')) return;
        try {
            await appointmentApi.cancel(id);
            toast.success('Appointment cancelled');
            fetchData();
        } catch (error) {
            toast.error('Failed to cancel appointment');
        }
    };

    const handleComplete = async (id) => {
        try {
            await appointmentApi.complete(id);
            toast.success('Appointment completed');
            fetchData();
        } catch (error) {
            toast.error('Failed to complete appointment');
        }
    };

    const getPatientName = (appointment) => {
        const patient = appointment.patient;
        return patient ? `${patient.firstName} ${patient.lastName}` : 'N/A';
    };

    const getDoctorName = (appointment) => {
        const doctor = appointment.doctor;
        return doctor ? `Dr. ${doctor.firstName} ${doctor.lastName}` : 'N/A';
    };

    const getStatusColor = (status) => {
        switch(status) {
            case 'PENDING': return 'bg-yellow-100 text-yellow-800';
            case 'CONFIRMED': return 'bg-blue-100 text-blue-800';
            case 'COMPLETED': return 'bg-green-100 text-green-800';
            case 'CANCELLED': return 'bg-red-100 text-red-800';
            default: return 'bg-gray-100 text-gray-800';
        }
    };

    const filteredAppointments = filterStatus 
        ? appointments.filter(a => a.status === filterStatus)
        : appointments;

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
                <h1 className="text-2xl font-bold">Appointments</h1>
                <button
                    onClick={() => navigate('/appointments/new')}
                    className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                    + Book Appointment
                </button>
            </div>

            {/* Filter by Status */}
            <div className="mb-6">
                <select
                    className="px-4 py-2 border rounded-lg"
                    value={filterStatus}
                    onChange={(e) => setFilterStatus(e.target.value)}
                >
                    <option value="">All Status</option>
                    <option value="PENDING">Pending</option>
                    <option value="CONFIRMED">Confirmed</option>
                    <option value="COMPLETED">Completed</option>
                    <option value="CANCELLED">Cancelled</option>
                </select>
            </div>

            <div className="bg-white rounded-lg shadow overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Patient</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Doctor</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Date & Time</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Actions</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {filteredAppointments.length === 0 ? (
                            <tr>
                                <td colSpan="5" className="px-6 py-4 text-center text-gray-500">
                                    No appointments found
                                </td>
                            </tr>
                        ) : (
                            filteredAppointments.map((appt) => (
                                <tr key={appt.id} className="hover:bg-gray-50">
                                    <td className="px-6 py-4 text-sm">{getPatientName(appt)}</td>
                                    <td className="px-6 py-4 text-sm">{getDoctorName(appt)}</td>
                                    <td className="px-6 py-4 text-sm">
                                        {new Date(appt.appointmentDate).toLocaleString()}
                                    </td>
                                    <td className="px-6 py-4 text-sm">
                                        <span className={`px-2 py-1 rounded-full text-xs ${getStatusColor(appt.status)}`}>
                                            {appt.status}
                                        </span>
                                    </td>
                                    <td className="px-6 py-4 text-sm space-x-2">
                                        {appt.status !== 'CANCELLED' && appt.status !== 'COMPLETED' && (
                                            <>
                                                <button
                                                    onClick={() => handleComplete(appt.id)}
                                                    className="text-green-600 hover:text-green-900"
                                                >
                                                    Complete
                                                </button>
                                                <button
                                                    onClick={() => handleCancel(appt.id)}
                                                    className="text-red-600 hover:text-red-900"
                                                >
                                                    Cancel
                                                </button>
                                            </>
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
