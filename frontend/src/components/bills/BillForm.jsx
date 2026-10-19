import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { billApi } from '../../api/billApi';
import { patientApi } from '../../api/patientApi';
import { appointmentApi } from '../../api/appointmentApi';
import toast from 'react-hot-toast';

export const BillForm = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [patients, setPatients] = useState([]);
    const [appointments, setAppointments] = useState([]);
    const [formData, setFormData] = useState({
        patientId: '',
        appointmentId: '',
        consultationFee: '',
        laboratoryCharges: '',
        medicationCharges: '',
        otherServices: '',
        discountAmount: '',
        notes: ''
    });

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const [patientsRes, apptsRes] = await Promise.all([
                patientApi.getAll({ page: 0, size: 100 }),
                appointmentApi.getAll({ page: 0, size: 100 })
            ]);
            setPatients(patientsRes.data.data?.content || []);
            setAppointments(apptsRes.data.data?.content || []);
        } catch (error) {
            console.error('Error fetching data:', error);
            toast.error('Failed to load data');
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            await billApi.create(formData);
            toast.success('Bill generated successfully');
            navigate('/bills');
        } catch (error) {
            toast.error(error.response?.data?.message || 'Failed to generate bill');
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const patientAppointments = appointments.filter((appointment) =>
        !formData.patientId || appointment.patient?.id === Number(formData.patientId)
    );

    return (
        <div>
            <h1 className="text-2xl font-bold mb-6">Generate Bill</h1>
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
                            <label className="block text-sm font-medium">Appointment (Optional)</label>
                            <select
                                name="appointmentId"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.appointmentId}
                                onChange={handleChange}
                            >
                                <option value="">Select Appointment</option>
                                {patientAppointments.map((appt) => (
                                    <option key={appt.id} value={appt.id}>
                                        {new Date(appt.appointmentDate).toLocaleDateString()} - Dr. {appt.doctor?.lastName || 'Unassigned'}
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
                        <div>
                            <label className="block text-sm font-medium">Laboratory Charges</label>
                            <input
                                type="number"
                                name="laboratoryCharges"
                                step="0.01"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.laboratoryCharges}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Medication Charges</label>
                            <input
                                type="number"
                                name="medicationCharges"
                                step="0.01"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.medicationCharges}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Other Services</label>
                            <input
                                type="number"
                                name="otherServices"
                                step="0.01"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.otherServices}
                                onChange={handleChange}
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium">Discount Amount</label>
                            <input
                                type="number"
                                name="discountAmount"
                                step="0.01"
                                className="mt-1 block w-full px-3 py-2 border rounded-md"
                                value={formData.discountAmount}
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
                            onClick={() => navigate('/bills')}
                            className="px-4 py-2 border rounded-lg hover:bg-gray-50"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            disabled={loading}
                            className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50"
                        >
                            {loading ? 'Generating...' : 'Generate Bill'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};
