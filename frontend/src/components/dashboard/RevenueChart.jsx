import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
} from 'chart.js';
import { billApi } from '../../api/billApi';

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

export const RevenueChart = () => {
    const [bills, setBills] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchBills();
    }, []);

    const fetchBills = async () => {
        try {
            const response = await billApi.getAll({ page: 0, size: 100 });
            setBills(response.data.data?.content || []);
        } catch (error) {
            console.error('Error fetching bills:', error);
        } finally {
            setLoading(false);
        }
    };

    const getLast6Months = () => {
        const months = [];
        for (let i = 5; i >= 0; i--) {
            const date = new Date();
            date.setMonth(date.getMonth() - i);
            months.push(date.toLocaleDateString('en-US', { month: 'short' }));
        }
        return months;
    };

    const getRevenueByMonth = () => {
        const months = getLast6Months();
        const revenues = months.map(month => {
            return bills
                .filter(b => {
                    if (b.status !== 'PAID') return false;
                    const billDate = new Date(b.paymentDate || b.createdAt);
                    return billDate.toLocaleDateString('en-US', { month: 'short' }) === month;
                })
                .reduce((sum, b) => sum + (b.finalAmount || 0), 0);
        });
        return revenues;
    };

    const data = {
        labels: getLast6Months(),
        datasets: [
            {
                label: 'Revenue',
                data: getRevenueByMonth(),
                backgroundColor: 'rgba(16, 185, 129, 0.8)',
                borderColor: 'rgb(16, 185, 129)',
                borderWidth: 2,
                borderRadius: 8,
            }
        ]
    };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: 'Revenue (Last 6 Months)',
                font: {
                    size: 16,
                    weight: 'bold'
                }
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    callback: function(value) {
                        return '$' + value;
                    }
                }
            }
        }
    };

    if (loading) {
        return (
            <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            </div>
        );
    }

    return (
        <div className="bg-white rounded-lg shadow p-6">
            <Bar data={data} options={options} />
        </div>
    );
};
