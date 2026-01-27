import { useState } from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate, Link } from 'react-router-dom'
import { setCredentials } from '../store/slices/authSlice'
import {
  adminLogin,
  customerLogin,
  courierCompanyLogin,
  deliveryPersonLogin,
} from '../api/authApi'

const Login = () => {
  const [role, setRole] = useState('CUSTOMER')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const dispatch = useDispatch()
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      let response
      let userRole = role

      switch (role) {
        case 'ADMIN':
          response = await adminLogin(email, password)
          userRole = 'ADMIN'
          break
        case 'CUSTOMER':
          response = await customerLogin(email, password)
          userRole = 'CUSTOMER'
          break
        case 'COURIER_COMPANY':
          response = await courierCompanyLogin(email, password)
          userRole = 'COURIER_COMPANY'
          break
        case 'DELIVERY_PERSON':
          response = await deliveryPersonLogin(email, password)
          userRole = 'DELIVERY_PERSON'
          break
        default:
          throw new Error('Invalid role')
      }

      if (response.success) {
        // Extract token from response
        // Note: Backend may return token in response.data.token or response.token
        // If backend doesn't return token yet, we'll use a placeholder
        // Update this when JWT is fully implemented in backend
        const token = response.token || response.data?.token || `token-${userRole}-${Date.now()}`
        const user = response.data

        dispatch(
          setCredentials({
            token,
            role: userRole,
            user,
          })
        )

        // Navigate based on role
        const rolePath = {
          ADMIN: '/admin',
          CUSTOMER: '/customer',
          COURIER_COMPANY: '/courier-company',
          DELIVERY_PERSON: '/delivery-person',
        }

        navigate(rolePath[userRole])
      } else {
        setError(response.message || 'Login failed')
      }
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Login failed. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
      <div className="bg-white rounded-lg shadow-xl p-8 w-full max-w-md">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">CourierSphere</h1>
          <p className="text-gray-600 mt-2">Courier Management System</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-6">
          {/* Role Selector */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Login As
            </label>
            <select
              value={role}
              onChange={(e) => setRole(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            >
              <option value="ADMIN">Admin</option>
              <option value="CUSTOMER">Customer</option>
              <option value="COURIER_COMPANY">Courier Company</option>
              <option value="DELIVERY_PERSON">Delivery Person</option>
            </select>
          </div>

          {/* Email */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Email
            </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              placeholder="Enter your email"
            />
          </div>

          {/* Password */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              placeholder="Enter your password"
            />
          </div>

          {/* Error Message */}
          {error && (
            <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-md">
              {error}
            </div>
          )}

          {/* Submit Button */}
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>

          {/* Registration Link for Customers */}
          {role === 'CUSTOMER' && (
            <div className="text-center mt-4">
              <p className="text-sm text-gray-600">
                Don't have an account?{' '}
                <Link to="/register" className="text-indigo-600 hover:text-indigo-800 font-medium">
                  Register here
                </Link>
              </p>
            </div>
          )}
        </form>
      </div>
    </div>
  )
}

export default Login
