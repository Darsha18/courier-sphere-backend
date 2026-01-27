import api from './axios'

// Get all customers
export const getAllCustomers = async () => {
  const response = await api.get('/admin/customers')
  return response.data
}

// Get all courier companies
export const getAllCourierCompanies = async () => {
  const response = await api.get('/admin/courier-companies')
  return response.data
}

// Get all delivery persons
export const getAllDeliveryPersons = async () => {
  const response = await api.get('/admin/delivery-persons')
  return response.data
}

// Get all couriers
export const getAllCouriers = async () => {
  const response = await api.get('/admin/couriers')
  return response.data
}

// Add courier company
export const addCourierCompany = async (data) => {
  const response = await api.post('/admin/courier-company', data)
  return response.data
}

// Delete courier company
export const deleteCourierCompany = async (companyId) => {
  const response = await api.delete(`/admin/courier-company/${companyId}`)
  return response.data
}
