import api from './axios'

// Get assigned couriers
export const getAssignedCouriers = async (deliveryPersonId) => {
  const response = await api.get(`/delivery-person/${deliveryPersonId}/couriers`)
  return response.data
}

// Update courier status
export const updateCourierStatus = async (deliveryPersonId, courierId, data) => {
  const response = await api.put(
    `/delivery-person/${deliveryPersonId}/courier/${courierId}/status`,
    data
  )
  return response.data
}
