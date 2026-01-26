import { Routes, Route, NavLink } from 'react-router-dom'
import Layout from '../../components/Layout'
import CouriersView from './CouriersView'
import DeliveryPersonsView from './DeliveryPersonsView'

const CourierCompanyDashboard = () => {
  const tabs = [
    { id: 'couriers', label: 'Couriers', path: '/courier-company/couriers' },
    { id: 'delivery-persons', label: 'Delivery Persons', path: '/courier-company/delivery-persons' },
  ]

  return (
    <Layout title="Courier Company Dashboard">
      <div className="bg-white rounded-lg shadow">
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

        <div className="p-6">
          <Routes>
            <Route path="/couriers" element={<CouriersView />} />
            <Route path="/delivery-persons" element={<DeliveryPersonsView />} />
            <Route path="/" element={<CouriersView />} />
          </Routes>
        </div>
      </div>
    </Layout>
  )
}

export default CourierCompanyDashboard
