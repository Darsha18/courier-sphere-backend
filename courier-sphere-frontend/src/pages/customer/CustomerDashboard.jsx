import { Routes, Route, NavLink } from 'react-router-dom'
import Layout from '../../components/Layout'
import ProfileView from './ProfileView'
import BookCourierView from './BookCourierView'
import MyCouriersView from './MyCouriersView'

const CustomerDashboard = () => {
  const tabs = [
    { id: 'profile', label: 'Profile', path: '/customer/profile' },
    { id: 'book-courier', label: 'Book Courier', path: '/customer/book-courier' },
    { id: 'my-couriers', label: 'My Couriers', path: '/customer/my-couriers' },
  ]

  return (
    <Layout title="Customer Dashboard">
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
            <Route path="/profile" element={<ProfileView />} />
            <Route path="/book-courier" element={<BookCourierView />} />
            <Route path="/my-couriers" element={<MyCouriersView />} />
            <Route path="/" element={<ProfileView />} />
          </Routes>
        </div>
      </div>
    </Layout>
  )
}

export default CustomerDashboard
