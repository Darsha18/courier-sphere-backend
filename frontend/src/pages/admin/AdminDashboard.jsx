import { useState, useEffect } from 'react'
import { Routes, Route, NavLink } from 'react-router-dom'
import Layout from '../../components/Layout'
import CustomersView from './CustomersView'
import CourierCompaniesView from './CourierCompaniesView'
import DeliveryPersonsView from './DeliveryPersonsView'
import CouriersView from './CouriersView'
import {
  getAllCustomers,
  getAllCourierCompanies,
  getAllDeliveryPersons,
  getAllCouriers,
} from '../../api/adminApi'

const AdminDashboard = () => {
  const [activeTab, setActiveTab] = useState('courier-companies')

  const tabs = [
    { id: 'courier-companies', label: 'Courier Companies', path: '/admin/courier-companies' },
    { id: 'customers', label: 'Customers', path: '/admin/customers' },
    { id: 'delivery-persons', label: 'Delivery Persons', path: '/admin/delivery-persons' },
    { id: 'couriers', label: 'Couriers', path: '/admin/couriers' },
  ]

  return (
    <Layout title="Admin Dashboard">
      <div className="bg-white rounded-lg shadow">
        {/* Tabs */}
        <div className="border-b border-gray-200">
          <nav className="flex space-x-8 px-6" aria-label="Tabs">
            {tabs.map((tab) => (
              <NavLink
                key={tab.id}
                to={tab.path}
                className={({ isActive }) =>
                  `py-4 px-1 border-b-2 font-medium text-sm ${
                    isActive
                      ? 'border-indigo-500 text-indigo-600'
                      : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  }`
                }
              >
                {tab.label}
              </NavLink>
            ))}
          </nav>
        </div>

        {/* Content */}
        <div className="p-6">
          <Routes>
            <Route path="/courier-companies" element={<CourierCompaniesView />} />
            <Route path="/customers" element={<CustomersView />} />
            <Route path="/delivery-persons" element={<DeliveryPersonsView />} />
            <Route path="/couriers" element={<CouriersView />} />
            <Route path="/" element={<CourierCompaniesView />} />
          </Routes>
        </div>
      </div>
    </Layout>
  )
}

export default AdminDashboard
