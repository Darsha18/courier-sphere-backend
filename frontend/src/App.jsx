import { Routes, Route, Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import Login from './pages/Login'
import Register from './pages/Register'
import AdminDashboard from './pages/admin/AdminDashboard'
import CustomerDashboard from './pages/customer/CustomerDashboard'
import CourierCompanyDashboard from './pages/courier-company/CourierCompanyDashboard'
import DeliveryPersonDashboard from './pages/delivery-person/DeliveryPersonDashboard'
import ProtectedRoute from './components/ProtectedRoute'

function App() {
  const { isAuthenticated, role } = useSelector((state) => state.auth)

  return (
    <Routes>
      <Route
        path="/login"
        element={isAuthenticated ? <Navigate to={`/${role === 'ADMIN' ? 'admin' : role === 'CUSTOMER' ? 'customer' : role === 'COURIER_COMPANY' ? 'courier-company' : 'delivery-person'}`} replace /> : <Login />}
      />
      <Route
        path="/register"
        element={isAuthenticated ? <Navigate to="/customer" replace /> : <Register />}
      />
      <Route
        path="/admin/*"
        element={
          <ProtectedRoute allowedRoles={['ADMIN']}>
            <AdminDashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/customer/*"
        element={
          <ProtectedRoute allowedRoles={['CUSTOMER']}>
            <CustomerDashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/courier-company/*"
        element={
          <ProtectedRoute allowedRoles={['COURIER_COMPANY']}>
            <CourierCompanyDashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/delivery-person/*"
        element={
          <ProtectedRoute allowedRoles={['DELIVERY_PERSON']}>
            <DeliveryPersonDashboard />
          </ProtectedRoute>
        }
      />
      <Route path="/" element={<Navigate to="/login" replace />} />
    </Routes>
  )
}

export default App
