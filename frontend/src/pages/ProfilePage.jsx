import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { userApi } from '../api/userApi';
import toast from 'react-hot-toast';

export const ProfilePage = () => {
    const { user, logout } = useAuth();
    const [loading, setLoading] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        username: ''
    });

    useEffect(() => {
        if (user) {
            setFormData({
                firstName: user.firstName || '',
                lastName: user.lastName || '',
                email: user.email || '',
                phoneNumber: user.phoneNumber || '',
                username: user.username || ''
            });
        }
    }, [user]);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            await userApi.updateProfile(formData);
            toast.success('Profile updated successfully');
            setIsEditing(false);
            // Refresh user data
            const updatedUser = await userApi.getProfile();
            localStorage.setItem('user', JSON.stringify(updatedUser.data.data));
            window.location.reload();
        } catch (error) {
            toast.error(error.response?.data?.message || 'Failed to update profile');
        } finally {
            setLoading(false);
        }
    };

    const handleChangePassword = async (e) => {
        e.preventDefault();
        const oldPassword = prompt('Enter current password:');
        if (!oldPassword) return;
        const newPassword = prompt('Enter new password:');
        if (!newPassword) return;
        const confirmPassword = prompt('Confirm new password:');
        if (newPassword !== confirmPassword) {
            toast.error('Passwords do not match');
            return;
        }

        setLoading(true);
        try {
            await userApi.changePassword({ oldPassword, newPassword });
            toast.success('Password changed successfully');
        } catch (error) {
            toast.error(error.response?.data?.message || 'Failed to change password');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <h1 className="text-2xl font-bold mb-6">My Profile</h1>

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                {/* Profile Card */}
                <div className="lg:col-span-1">
                    <div className="bg-white rounded-lg shadow p-6 text-center">
                        <div className="w-24 h-24 bg-blue-500 rounded-full flex items-center justify-center mx-auto text-3xl text-white font-bold">
                            {user?.firstName?.[0]}{user?.lastName?.[0]}
                        </div>
                        <h2 className="text-xl font-semibold mt-4">
                            {user?.firstName} {user?.lastName}
                        </h2>
                        <p className="text-gray-500 text-sm">{user?.email}</p>
                        <div className="mt-4 flex flex-wrap justify-center gap-2">
                            {user?.roles?.map((role) => (
                                <span key={role} className="px-3 py-1 bg-blue-100 text-blue-800 text-xs rounded-full">
                                    {role}
                                </span>
                            ))}
                        </div>
                        <button
                            onClick={logout}
                            className="mt-6 px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 w-full"
                        >
                            Logout
                        </button>
                    </div>
                </div>

                {/* Profile Form */}
                <div className="lg:col-span-2">
                    <div className="bg-white rounded-lg shadow p-6">
                        <div className="flex justify-between items-center mb-6">
                            <h2 className="text-lg font-semibold">Profile Information</h2>
                            {!isEditing ? (
                                <button
                                    onClick={() => setIsEditing(true)}
                                    className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 text-sm"
                                >
                                    Edit Profile
                                </button>
                            ) : (
                                <button
                                    onClick={() => setIsEditing(false)}
                                    className="px-4 py-2 border rounded-lg hover:bg-gray-50 text-sm"
                                >
                                    Cancel
                                </button>
                            )}
                        </div>

                        <form onSubmit={handleSubmit}>
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <div>
                                    <label className="block text-sm font-medium text-gray-700">First Name</label>
                                    <input
                                        type="text"
                                        name="firstName"
                                        disabled={!isEditing}
                                        className={`mt-1 block w-full px-3 py-2 border rounded-md ${!isEditing ? 'bg-gray-50' : ''}`}
                                        value={formData.firstName}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium text-gray-700">Last Name</label>
                                    <input
                                        type="text"
                                        name="lastName"
                                        disabled={!isEditing}
                                        className={`mt-1 block w-full px-3 py-2 border rounded-md ${!isEditing ? 'bg-gray-50' : ''}`}
                                        value={formData.lastName}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium text-gray-700">Username</label>
                                    <input
                                        type="text"
                                        name="username"
                                        disabled
                                        className="mt-1 block w-full px-3 py-2 border rounded-md bg-gray-50"
                                        value={formData.username}
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium text-gray-700">Email</label>
                                    <input
                                        type="email"
                                        name="email"
                                        disabled={!isEditing}
                                        className={`mt-1 block w-full px-3 py-2 border rounded-md ${!isEditing ? 'bg-gray-50' : ''}`}
                                        value={formData.email}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div className="col-span-2">
                                    <label className="block text-sm font-medium text-gray-700">Phone Number</label>
                                    <input
                                        type="text"
                                        name="phoneNumber"
                                        disabled={!isEditing}
                                        className={`mt-1 block w-full px-3 py-2 border rounded-md ${!isEditing ? 'bg-gray-50' : ''}`}
                                        value={formData.phoneNumber}
                                        onChange={handleChange}
                                    />
                                </div>
                            </div>

                            {isEditing && (
                                <div className="mt-6 flex justify-end">
                                    <button
                                        type="submit"
                                        disabled={loading}
                                        className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50"
                                    >
                                        {loading ? 'Saving...' : 'Save Changes'}
                                    </button>
                                </div>
                            )}
                        </form>

                        <div className="mt-6 pt-6 border-t">
                            <h3 className="text-sm font-medium text-gray-700 mb-4">Security</h3>
                            <button
                                onClick={handleChangePassword}
                                disabled={loading}
                                className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 text-sm disabled:opacity-50"
                            >
                                Change Password
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};