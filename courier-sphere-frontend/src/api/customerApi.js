import api from './axios'

// Get customer profile
export const getCustomerProfile = async (customerId) => {
  const response = await api.get(`/customer/profile/${customerId}`)
  return response.data
}

// Get courier companies (for booking)
export const getCourierCompanies = async () => {
  const response = await api.get('/customer/courier-companies')
  return response.data
}

// Book a courier
export const bookCourier = async (customerId, data) => {
  const response = await api.post(`/customer/${customerId}/book-courier`, data)
  return response.data
}

/**
 * Get all couriers for a specific customer
 * @param {number} customerId - The ID of the customer
 */
export const getCustomerCouriers = async (customerId) => {
  const response = await api.get(`/customer/${customerId}/couriers`)
  return response.data
}

/**
 * Register a new customer
 * @param {object} data - Customer registration data (firstName, lastName, email, password, contact, address)
 */
export const registerCustomer = async (data) => {
  const response = await api.post('/customer/register', data)
  return response.data
}
