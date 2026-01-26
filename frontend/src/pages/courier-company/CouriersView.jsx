import { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { getCompanyCouriers, assignDelivery, getDeliveryPersons } from '../../api/courierCompanyApi'

/**
 * Component to display and manage company couriers
 * Allows assigning delivery persons to couriers
 */
const CouriersView = () => {
  const { user } = useSelector((state) => state.auth)
  const [couriers, setCouriers] = useState([])
  const [deliveryPersons, setDeliveryPersons] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [showAssignModal, setShowAssignModal] = useState(false)
  const [selectedCourier, setSelectedCourier] = useState(null)
  const [assignForm, setAssignForm] = useState({ deliveryPersonId: '' })

  useEffect(() => {
    if (user?.id) {
      fetchCouriers()
      fetchDeliveryPersons()
    }
  }, [user])

  /**
   * Fetch all couriers for the logged-in company
   */
  const fetchCouriers = async () => {
    try {
      setLoading(true)
      const response = await getCompanyCouriers(user.id)
      if (response.success) {
        setCouriers(response.data || [])
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch couriers')
    } finally {
      setLoading(false)
    }
  }

  /**
   * Fetch all delivery persons for the logged-in company
   */
  const fetchDeliveryPersons = async () => {
    try {
      const response = await getDeliveryPersons(user.id)
      if (response.success) {
        setDeliveryPersons(response.data || [])
      }
    } catch (err) {
      console.error('Failed to fetch delivery persons:', err)
    }
  }

  const handleAssign = async (e) => {
    e.preventDefault()
    try {
      const response = await assignDelivery({
        courierId: selectedCourier.courierId,
        deliveryPersonId: parseInt(assignForm.deliveryPersonId),
      })
      if (response.success) {
        setShowAssignModal(false)
        setSelectedCourier(null)
        setAssignForm({ deliveryPersonId: '' })
        fetchCouriers()
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to assign delivery person')
    }
  }

  if (loading) {
    return <div className="text-center py-8">Loading...</div>
  }

  return (
    <div>
      <h2 className="text-2xl font-bold text-gray-900 mb-6">Company Couriers</h2>

      {error && (
        <div className="mb-4 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-md">
          {error}
        </div>
      )}

      <div className="overflow-x-auto">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Tracking #
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Cust ID
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Type
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Weight
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Customer
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Receiver
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Delivery Person
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Status
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Actions
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {couriers.length === 0 ? (
              <tr>
                <td colSpan="9" className="px-6 py-4 text-center text-gray-500">
                  No couriers found
                </td>
              </tr>
            ) : (
              couriers.map((courier) => (
                <tr key={courier.courierId}>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                    {courier.trackingNumber}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {courier.customerId}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {courier.courierType}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {courier.weight} kg
                  </td>
                  <td className="px-6 py-4 text-sm text-gray-500">
                    {courier.customerName}
                    <br />
                    <span className="text-xs text-gray-400">{courier.customerContact}</span>
                  </td>
                  <td className="px-6 py-4 text-sm text-gray-500">
                    {courier.receiverName}
                    <br />
                    <span className="text-xs text-gray-400">{courier.receiverAddress}</span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {courier.deliveryPersonName || 'Not Assigned'}
                    {courier.deliveryPersonContact && (
                      <>
                        <br />
                        <span className="text-xs text-gray-400">{courier.deliveryPersonContact}</span>
                      </>
                    )}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span
                      className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                        courier.status === 'DELIVERED'
                          ? 'bg-green-100 text-green-800'
                          : courier.status === 'IN_PROGRESS' || courier.status === 'PICKED_UP'
                          ? 'bg-yellow-100 text-yellow-800'
                          : 'bg-gray-100 text-gray-800'
                      }`}
                    >
                      {courier.status}
                    </span>
                    {courier.deliveryMessage && (
                      <p className="text-xs text-gray-500 mt-1">{courier.deliveryMessage}</p>
                    )}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <button
                      onClick={() => {
                        setSelectedCourier(courier)
                        setShowAssignModal(true)
                      }}
                      className="text-indigo-600 hover:text-indigo-900"
                    >
                      {courier.deliveryPersonName ? 'Reassign' : 'Assign'}
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {showAssignModal && selectedCourier && (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full z-50">
          <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
            <div className="mt-3">
              <h3 className="text-lg font-medium text-gray-900 mb-4">
                Assign Delivery Person
              </h3>
              <p className="text-sm text-gray-600 mb-4">
                Courier: {selectedCourier.trackingNumber}
              </p>
              <form onSubmit={handleAssign} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Delivery Person
                  </label>
                  <select
                    value={assignForm.deliveryPersonId}
                    onChange={(e) => setAssignForm({ deliveryPersonId: e.target.value })}
                    required
                    className="w-full px-3 py-2 border border-gray-300 rounded-md"
                  >
                    <option value="">Select delivery person</option>
                    {deliveryPersons
                      .filter((dp) => dp.active)
                      .map((dp) => (
                        <option key={dp.id} value={dp.id}>
                          {dp.firstName} {dp.lastName} - {dp.contact}
                        </option>
                      ))}
                  </select>
                </div>
                <div className="flex justify-end space-x-3">
                  <button
                    type="button"
                    onClick={() => {
                      setShowAssignModal(false)
                      setSelectedCourier(null)
                      setAssignForm({ deliveryPersonId: '' })
                    }}
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
                  >
                    Assign
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

export default CouriersView
