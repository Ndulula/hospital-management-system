import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { doctorApi } from '../../api/doctorApi';
import { departmentApi } from '../../api/departmentApi';
import toast from 'react-hot-toast';

export const DoctorForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [departments, setDepartments] = useState([]);
    const [formData, setFormData] = useState({
        doctorId: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        specialization: '',
        departmentId: '',
        availability: '',
        consultationFee: '',
        qualifications: ''
    });

    useEffect(() => {
        const loadForm = async () => {
            try {
                const departmentResponse = await departmentApi.getAll();
                setDepartments(departmentResponse.data.data || []);

                if (!id) {
                    return;
                }

                setLoading(true);
                const doctorResponse = await doctorApi.getById(id);
                const data = doctorResponse.data.data;
                setFormData({
                    doctorId: data.doctorId || '',
                    firstName: data.firstName || '',
                    lastName: data.lastName || '',
                    email: data.email || '',
                    phoneNumber: data.phoneNumber || '',
                    specialization: data.specialization || '',
                    departmentId: data.department?.id || '',
                    availability: data.availability || '',
                    consultationFee: data.consultationFee || '',
                    qualifications: data.qualifications || ''
                });
            } catch (error) {
                toast.error(id ? 'Failed to load doctor details' : 'Failed to load departments');
            } finally {
                if (id) {
                    setLoading(false);
                }
            }
        };

        loadForm();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            if (id) {
                await doctorApi.update(id, formData);
                toast.success('Doctor updated successfully');
            } else {
                await doctorApi.create(formData);
                toast.success('Doctor created successfully');
            }
            navigate('/doctors');
        } catch (error) {
            toast.error(error.response?.data?.message || 'Failed to save doctor');
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    if (loading && id) {
        return (
            <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
            </div>
        );
    }

    return (
        <div>
            <h1 className="text-2xl font-bold mb-6">{id ? 'Edit Doctor' : 'Add New Doctor'}</h1>
            <div className="bg-white rounded-lg shadow p-6">
                <form onSubmit={handleSubmit} className="space-y-6">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label className="block text-sm font-medium">Doctor ID *</label>
                            <input
                                type="text"
                                name="doctorId"
                                required
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.doctorId}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">First Name *</label>
                            <input
                                type="text"
                                name="firstName"
                                required
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.firstName}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Last Name *</label>
                            <input
                                type="text"
                                name="lastName"
                                required
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.lastName}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Email</label>
                            <input
                                type="email"
                                name="email"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.email}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Phone Number</label>
                            <input
                                type="text"
                                name="phoneNumber"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.phoneNumber}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Specialization *</label>
                            <input
                                type="text"
                                name="specialization"
                                required
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.specialization}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Department *</label>
                            <select
                                name="departmentId"
                                required
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.departmentId}
                                onChange={handleChange}
                            >
                                <option value="">Select Department</option>
                                {departments.map((dept) => (
                                    <option key={dept.id} value={dept.id}>
                                        {dept.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Consultation Fee</label>
                            <input
                                type="number"
                                name="consultationFee"
                                step="0.01"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.consultationFee}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="col-span-2">
                            <label className="block text-sm font-medium">Availability</label>
                            <input
                                type="text"
                                name="availability"
                                placeholder="e.g., Mon-Fri 9am-5pm"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.availability}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="col-span-2">
                            <label className="block text-sm font-medium">Qualifications</label>
                            <textarea
                                name="qualifications"
                                rows="3"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.qualifications}
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="flex justify-end space-x-3">
                        <button
                            type="button"
                            onClick={() => navigate('/doctors')}
                            className="px-4 py-2 border rounded-lg hover:bg-gray-50"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            disabled={loading}
                            className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50"
                        >
                            {loading ? 'Saving...' : id ? 'Update' : 'Create'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};
