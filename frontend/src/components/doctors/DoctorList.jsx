import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { doctorApi } from '../../api/doctorApi';
import { departmentApi } from '../../api/departmentApi';
import toast from 'react-hot-toast';

export const DoctorList = () => {
    const [doctors, setDoctors] = useState([]);
    const [departments, setDepartments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedDepartment, setSelectedDepartment] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            setLoading(true);
            const [doctorsRes, deptRes] = await Promise.all([
                doctorApi.getAll({ page: 0, size: 100 }),
                departmentApi.getAll()
            ]);
            setDoctors(doctorsRes.data.data?.content || []);
            setDepartments(deptRes.data.data || []);
        } catch (error) {
            console.error('Error fetching data:', error);
            toast.error('Failed to load doctors');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Are you sure you want to delete this doctor?')) return;
        try {
            await doctorApi.delete(id);
            toast.success('Doctor deleted successfully');
            fetchData();
        } catch (error) {
            toast.error('Failed to delete doctor');
        }
    };

    const getDepartmentName = (doctor) => {
        return doctor.department?.name || 'N/A';
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
                <h1 className="text-2xl font-bold">Doctors</h1>
                <button
                    onClick={() => navigate('/doctors/new')}
                    className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                    + Add Doctor
                </button>
            </div>

            {/* Filter by Department */}
            <div className="mb-6">
                <select
                    className="px-4 py-2 border rounded-lg"
                    value={selectedDepartment}
                    onChange={(e) => setSelectedDepartment(e.target.value)}
                >
                    <option value="">All Departments</option>
                    {departments.map((dept) => (
                        <option key={dept.id} value={dept.id}>
                            {dept.name}
                        </option>
                    ))}
                </select>
            </div>

            <div className="bg-white rounded-lg shadow overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Doctor ID</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Name</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Specialization</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Department</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Consultation Fee</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Actions</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {doctors.length === 0 ? (
                            <tr>
                                <td colSpan="6" className="px-6 py-4 text-center text-gray-500">
                                    No doctors found
                                </td>
                            </tr>
                        ) : (
                            doctors
                                .filter(doc => !selectedDepartment || doc.department?.id === Number(selectedDepartment))
                                .map((doctor) => (
                                    <tr key={doctor.id} className="hover:bg-gray-50">
                                        <td className="px-6 py-4 text-sm">{doctor.doctorId}</td>
                                        <td className="px-6 py-4 text-sm">
                                            Dr. {doctor.firstName} {doctor.lastName}
                                        </td>
                                        <td className="px-6 py-4 text-sm">{doctor.specialization}</td>
                                        <td className="px-6 py-4 text-sm">{getDepartmentName(doctor)}</td>
                                        <td className="px-6 py-4 text-sm">${doctor.consultationFee || 0}</td>
                                        <td className="px-6 py-4 text-sm space-x-2">
                                            <button
                                                onClick={() => navigate(`/doctors/edit/${doctor.id}`)}
                                                className="text-blue-600 hover:text-blue-900"
                                            >
                                                Edit
                                            </button>
                                            <button
                                                onClick={() => handleDelete(doctor.id)}
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
