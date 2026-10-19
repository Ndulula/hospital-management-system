import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { appointmentApi } from '../../api/appointmentApi';
import { patientApi } from '../../api/patientApi';
import { doctorApi } from '../../api/doctorApi';
import toast from 'react-hot-toast';

export const AppointmentForm = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [patients, setPatients] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [formData, setFormData] = useState({
        patientId: '',
        doctorId: '',
        appointmentDate: '',
        notes: ''
    });

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const [patientsRes, doctorsRes] = await Promise.all([
                patientApi.getAll({ page: 0, size: 100 }),
                doctorApi.getAll({ page: 0, size: 100 })
            ]);
            setPatients(patientsRes.data.data?.content || []);
            setDoctors(doctorsRes.data.data?.content || []);
        } catch (error) {
            console.error('Error fetching data:', error);
            toast.error('Failed to load patients and doctors');
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            await appointmentApi.create(formData);
            toast.success('Appointment booked successfully');
            navigate('/appointments');
        } catch (error) {
            toast.error(error.response?.data?.message || 'Failed to book appointment');
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    return (
        <div>
            <h1 className="text-2xl font-bold mb-6">Book Appointment</h1>
            <div className="bg-white rounded-lg shadow p-6">
                <form onSubmit={handleSubmit} className="space-y-6">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label className="block text-sm font-medium">Patient *</label>
                            <select
                                name="patientId"
                                required
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.patientId}
                                onChange={handleChange}
                            >
                                <option value="">Select Patient</option>
                                {patients.map((patient) => (
                                    <option key={patient.id} value={patient.id}>
                                        {patient.firstName} {patient.lastName}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Doctor *</label>
                            <select
                                name="doctorId"
                                required
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.doctorId}
                                onChange={handleChange}
                            >
                                <option value="">Select Doctor</option>
                                {doctors.map((doctor) => (
                                    <option key={doctor.id} value={doctor.id}>
                                        Dr. {doctor.firstName} {doctor.lastName} ({doctor.specialization})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Date & Time *</label>
                            <input
                                type="datetime-local"
                                name="appointmentDate"
                                required
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.appointmentDate}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="col-span-2">
                            <label className="block text-sm font-medium">Notes</label>
                            <textarea
                                name="notes"
                                rows="3"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.notes}
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="flex justify-end space-x-3">
                        <button
                            type="button"
                            onClick={() => navigate('/appointments')}
                            className="px-4 py-2 border rounded-lg hover:bg-gray-50"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            disabled={loading}
                            className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50"
                        >
                            {loading ? 'Booking...' : 'Book Appointment'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};