import { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import Layout from '../../components/Layout'
import { getAssignedCouriers, updateCourierStatus } from '../../api/deliveryPersonApi'

const DeliveryPersonDashboard = () => {
  const { user } = useSelector((state) => state.auth)
  const [couriers, setCouriers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [showStatusModal, setShowStatusModal] = useState(false)
  const [selectedCourier, setSelectedCourier] = useState(null)
  const [statusForm, setStatusForm] = useState({
    status: '',
    deliveryMessage: '',
  })

  useEffect(() => {
    if (user?.deliveryPersonId) {
      fetchCouriers()
    }
  }, [user])

  const fetchCouriers = async () => {
    try {
      setLoading(true)
      const response = await getAssignedCouriers(user.deliveryPersonId)
      if (response.success) {
        setCouriers(response.data || [])
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch couriers')
    } finally {
      setLoading(false)
    }
  }

  const handleUpdateStatus = async (e) => {
    e.preventDefault()
    try {
      const response = await updateCourierStatus(
        user.deliveryPersonId,
        selectedCourier.courierId,
        statusForm
      )
      if (response.success) {
        setShowStatusModal(false)
        setSelectedCourier(null)
        setStatusForm({ status: '', deliveryMessage: '' })
        fetchCouriers()
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update status')
    }
  }

  if (loading) {
    return <div className="text-center py-8">Loading...</div>
  }

  return (
    <Layout title="Delivery Person Dashboard">
      <div className="bg-white rounded-lg shadow">
        <div className="p-6">
          <h2 className="text-2xl font-bold text-gray-900 mb-6">My Assigned Couriers</h2>

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
                    Type
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Weight
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Receiver
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
                    <td colSpan="6" className="px-6 py-4 text-center text-gray-500">
                      No assigned couriers found
                    </td>
                  </tr>
                ) : (
                  couriers.map((courier) => (
                    <tr key={courier.courierId}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        {courier.trackingNumber}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {courier.courierType}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {courier.weight} kg
                      </td>
                      <td className="px-6 py-4 text-sm text-gray-500">
                        {courier.receiverName}
                        <br />
                        <span className="text-xs text-gray-400">{courier.receiverAddress}</span>
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
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button
                          onClick={() => {
                            setSelectedCourier(courier)
                            setStatusForm({
                              status: courier.status,
                              deliveryMessage: '',
                            })
                            setShowStatusModal(true)
                          }}
                          className="text-indigo-600 hover:text-indigo-900"
                        >
                          Update Status
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>

      {/* Status Update Modal */}
      {showStatusModal && selectedCourier && (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full z-50">
          <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
            <div className="mt-3">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Update Courier Status</h3>
              <p className="text-sm text-gray-600 mb-4">
                Tracking: {selectedCourier.trackingNumber}
              </p>
              <form onSubmit={handleUpdateStatus} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Status</label>
                  <select
                    value={statusForm.status}
                    onChange={(e) => setStatusForm({ ...statusForm, status: e.target.value })}
                    required
                    className="w-full px-3 py-2 border border-gray-300 rounded-md"
                  >
                    <option value="BOOKED">Booked</option>
                    <option value="PROCESSING">Processing</option>
                    <option value="PROCESSED">Processed</option>
                    <option value="ASSIGNED">Assigned</option>
                    <option value="OUT_FOR_DELIVERY">Out for Delivery</option>
                    <option value="DELIVERED">Delivered</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Delivery Message (Optional)
                  </label>
                  <textarea
                    value={statusForm.deliveryMessage}
                    onChange={(e) =>
                      setStatusForm({ ...statusForm, deliveryMessage: e.target.value })}
                    rows="3"
                    className="w-full px-3 py-2 border border-gray-300 rounded-md"
                    placeholder="Add any delivery notes..."
                  />
                </div>
                <div className="flex justify-end space-x-3">
                  <button
                    type="button"
                    onClick={() => {
                      setShowStatusModal(false)
                      setSelectedCourier(null)
                      setStatusForm({ status: '', deliveryMessage: '' })
                    }}
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
                  >
                    Update
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      )}
    </Layout>
  )
}

export default DeliveryPersonDashboard
