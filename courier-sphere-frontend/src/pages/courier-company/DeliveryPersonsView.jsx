import { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import {
  getDeliveryPersons,
  addDeliveryPerson,
  deleteDeliveryPerson,
  updateDeliveryPersonStatus,
} from '../../api/courierCompanyApi'

/**
 * Component to manage delivery persons for the courier company
 * Allows adding, deleting, and toggling status of delivery persons
 */
const DeliveryPersonsView = () => {
  const { user } = useSelector((state) => state.auth)
  const [deliveryPersons, setDeliveryPersons] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [showAddModal, setShowAddModal] = useState(false)
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    contact: '',
  })

  useEffect(() => {
    if (user?.id) {
      fetchDeliveryPersons()
    }
  }, [user])

  /**
   * Fetch all delivery persons for the logged-in company
   */
  const fetchDeliveryPersons = async () => {
    try {
      setLoading(true)
      const response = await getDeliveryPersons(user.id)
      if (response.success) {
        setDeliveryPersons(response.data || [])
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch delivery persons')
    } finally {
      setLoading(false)
    }
  }

  /**
   * Handle adding a new delivery person
   */
  const handleAdd = async (e) => {
    e.preventDefault()
    try {
      const response = await addDeliveryPerson(user.id, formData)
      if (response.success) {
        setShowAddModal(false)
        setFormData({
          firstName: '',
          lastName: '',
          email: '',
          password: '',
          contact: '',
        })
        fetchDeliveryPersons()
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to add delivery person')
    }
  }

  /**
   * Handle deleting a delivery person
   */
  const handleDelete = async (deliveryPersonId) => {
    if (!window.confirm('Are you sure you want to delete this delivery person?')) {
      return
    }
    try {
      const response = await deleteDeliveryPerson(user.id, deliveryPersonId)
      if (response.success) {
        fetchDeliveryPersons()
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete delivery person')
    }
  }

  /**
   * Handle toggling delivery person status
   */
  const handleToggleStatus = async (deliveryPersonId, currentStatus) => {
    try {
      const response = await updateDeliveryPersonStatus(user.id, deliveryPersonId, !currentStatus)
      if (response.success) {
        fetchDeliveryPersons()
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update status')
    }
  }

  if (loading) {
    return <div className="text-center py-8">Loading...</div>
  }

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-gray-900">Delivery Persons</h2>
        <button
          onClick={() => setShowAddModal(true)}
          className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
        >
          Add Delivery Person
        </button>
      </div>

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
                ID
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Name
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Email
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Contact
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
            {deliveryPersons.length === 0 ? (
              <tr>
                <td colSpan="6" className="px-6 py-4 text-center text-gray-500">
                  No delivery persons found
                </td>
              </tr>
            ) : (
              deliveryPersons.map((dp) => (
                <tr key={dp.id}>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {dp.id}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {dp.firstName} {dp.lastName}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {dp.email}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {dp.contact}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <button
                      onClick={() => handleToggleStatus(dp.id, dp.active)}
                      className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full cursor-pointer ${
                        dp.active
                          ? 'bg-green-100 text-green-800'
                          : 'bg-red-100 text-red-800'
                      }`}
                    >
                      {dp.active ? 'Active' : 'Inactive'}
                    </button>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <button
                      onClick={() => handleDelete(dp.id)}
                      className="text-red-600 hover:text-red-900"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {showAddModal && (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full z-50">
          <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
            <div className="mt-3">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Add Delivery Person</h3>
              <form onSubmit={handleAdd} className="space-y-4">
                <input
                  type="text"
                  placeholder="First Name"
                  value={formData.firstName}
                  onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md"
                  required
                />
                <input
                  type="text"
                  placeholder="Last Name"
                  value={formData.lastName}
                  onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md"
                  required
                />
                <input
                  type="email"
                  placeholder="Email"
                  value={formData.email}
                  onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md"
                  required
                />
                <input
                  type="password"
                  placeholder="Password"
                  value={formData.password}
                  onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md"
                  required
                />
                <input
                  type="text"
                  placeholder="Contact"
                  value={formData.contact}
                  onChange={(e) => setFormData({ ...formData, contact: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md"
                  required
                />
                <div className="flex justify-end space-x-3">
                  <button
                    type="button"
                    onClick={() => setShowAddModal(false)}
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
                  >
                    Add
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

export default DeliveryPersonsView
