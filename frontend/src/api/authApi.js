import api from './axios'

// Admin Login
export const adminLogin = async (email, password) => {
  const response = await api.post('/admin/login', { email, password })
  return response.data
}

// Customer Login
export const customerLogin = async (email, password) => {
  const response = await api.post('/customer/login', { email, password })
  return response.data
}

// Courier Company Login
export const courierCompanyLogin = async (email, password) => {
  const response = await api.post('/courier-company/login', { email, password })
  return response.data
}

// Delivery Person Login
export const deliveryPersonLogin = async (email, password) => {
  const response = await api.post('/delivery-person/login', { email, password })
  return response.data
}
