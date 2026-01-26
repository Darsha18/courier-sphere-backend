import api from './axios'

/**
 * Get all couriers for the logged-in courier company
 * @param {number} companyId - The ID of the courier company
 */
export const getCompanyCouriers = async (companyId) => {
  const response = await api.get(`/courier-company/couriers?companyId=${companyId}`)
  return response.data
}

/**
 * Get all delivery persons for the logged-in courier company
 * @param {number} companyId - The ID of the courier company
 */
export const getDeliveryPersons = async (companyId) => {
  const response = await api.get(`/courier-company/delivery-persons?companyId=${companyId}`)
  return response.data
}

/**
 * Add a new delivery person to the courier company
 * @param {number} companyId - The ID of the courier company
 * @param {object} data - Delivery person data (firstName, lastName, email, password, contact)
 */
export const addDeliveryPerson = async (companyId, data) => {
  const response = await api.post(`/courier-company/delivery-person?companyId=${companyId}`, data)
  return response.data
}

/**
 * Delete a delivery person from the courier company
 * @param {number} companyId - The ID of the courier company
 * @param {number} deliveryPersonId - The ID of the delivery person to delete
 */
export const deleteDeliveryPerson = async (companyId, deliveryPersonId) => {
  const response = await api.delete(`/courier-company/delivery-person/${deliveryPersonId}?companyId=${companyId}`)
  return response.data
}

/**
 * Update delivery person status
 * @param {number} companyId - The ID of the courier company
 * @param {number} deliveryPersonId - The ID of the delivery person
 * @param {boolean} active - The new status
 */
export const updateDeliveryPersonStatus = async (companyId, deliveryPersonId, active) => {
  const response = await api.put(`/courier-company/delivery-person/${deliveryPersonId}/status?companyId=${companyId}&active=${active}`)
  return response.data
}

/**
 * Assign a delivery person to a courier
 * @param {object} data - Assignment data (courierId, deliveryPersonId)
 */
export const assignDelivery = async (data) => {
  const response = await api.post('/courier-company/assign-delivery', data)
  return response.data
}

/**
 * Logout the courier company
 */
export const logout = async () => {
  const response = await api.post('/courier-company/logout')
  return response.data
}
