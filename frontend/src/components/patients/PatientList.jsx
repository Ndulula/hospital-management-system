import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { patientApi } from '../../api/patientApi';
import toast from 'react-hot-toast';

export const PatientList = () => {
    const [patients, setPatients] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        fetchPatients();
    }, []);

    const fetchPatients = async () => {
        try {
            setLoading(true);
            const response = await patientApi.getAll({ page: 0, size: 100 });
            setPatients(response.data.data?.content || []);
        } catch (error) {
            const message = error.response?.data?.message || 'Failed to load patients';
            toast.error(message);
        } finally {
            setLoading(false);
        }
    };

    const deletePatient = async (patient) => {
        const patientName = `${patient.firstName} ${patient.lastName}`;
        if (!window.confirm(`Delete ${patientName}? This cannot be undone.`)) {
            return;
        }

        try {
            await patientApi.delete(patient.id);
            setPatients((currentPatients) =>
                currentPatients.filter((item) => item.id !== patient.id)
            );
            toast.success('Patient deleted successfully');
        } catch (error) {
            const message = error.response?.data?.message || 'Failed to delete patient';
            toast.error(message);
        }
    };

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
                <h1 className="text-2xl font-bold text-gray-900">Patients</h1>
                <button
                    onClick={() => navigate('/patients/new')}
                    className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                    + Add Patient
                </button>
            </div>

            <div className="bg-white rounded-lg shadow overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Patient ID</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Name</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Gender</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Phone</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Actions</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {patients.length === 0 ? (
                            <tr>
                                <td colSpan="5" className="px-6 py-4 text-center text-gray-500">
                                    No patients found. Click "Add Patient" to create one.
                                </td>
                            </tr>
                        ) : (
                            patients.map((patient) => (
                                <tr key={patient.id} className="hover:bg-gray-50">
                                    <td className="px-6 py-4 text-sm font-medium text-gray-900">
                                        {patient.patientId}
                                    </td>
                                    <td className="px-6 py-4 text-sm text-gray-900">
                                        {patient.firstName} {patient.lastName}
                                    </td>
                                    <td className="px-6 py-4 text-sm text-gray-500">
                                        {patient.gender}
                                    </td>
                                    <td className="px-6 py-4 text-sm text-gray-500">
                                        {patient.phoneNumber}
                                    </td>
                                    <td className="px-6 py-4 text-sm space-x-2">
                                        <button
                                            onClick={() => navigate(`/patients/edit/${patient.id}`)}
                                            className="text-blue-600 hover:text-blue-900"
                                        >
                                            Edit
                                        </button>
                                        <button
                                            onClick={() => deletePatient(patient)}
                                            className="text-red-600 hover:text-red-900"
                                        >
                                            Delete
                                        </button>
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
