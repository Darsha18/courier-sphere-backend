import { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { getCourierCompanies, bookCourier, verifyPayment } from '../../api/customerApi'

const BookCourierView = () => {
  const { user } = useSelector((state) => state.auth)
  const [companies, setCompanies] = useState([])
  const [loading, setLoading] = useState(true)
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const [formData, setFormData] = useState({
    courierCompanyId: '',
    courierType: '',
    weight: '',
    receiverName: '',
    receiverAddress: '',
  })

  useEffect(() => {
    fetchCompanies()
  }, [])

  const fetchCompanies = async () => {
    try {
      setLoading(true)
      const response = await getCourierCompanies()
      if (response.success) {
        setCompanies(response.data || [])
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch companies')
    } finally {
      setLoading(false)
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    setSubmitting(true)

    try {
      // 1. Create a Razorpay Order via Backend
      const response = await bookCourier(user.id, {
        ...formData,
        weight: parseFloat(formData.weight),
      })

      if (response.success) {
        const orderId = response.data;

        // 2. Configure Razorpay Checkout options
        const options = {
          key: "rzp_test_BLuYvjyR58WQhz", 
          amount: parseFloat(formData.weight) * 100 * 100, 
          currency: "INR",
          name: "CourierSphere",
          description: "Courier Booking Payment",
          order_id: orderId,
          handler: async function (paymentResponse) {
            try {
              // 3. VERIFY PAYMENT (Step 3)
              // This calls the backend to verify the signature and update status to SUCCESS
              const verifyRes = await verifyPayment({
                orderId: paymentResponse.razorpay_order_id,
                paymentId: paymentResponse.razorpay_payment_id,
                signature: paymentResponse.razorpay_signature
              });

              if (verifyRes.success) {
                setSuccess('Payment Verified and Courier Booked Successfully!');
                // Reset form
                setFormData({
                  courierCompanyId: '',
                  courierType: '',
                  weight: '',
                  receiverName: '',
                  receiverAddress: '',
                });
              } else {
                setError('Payment succeeded but verification failed. Please contact support.');
              }
            } catch (err) {
              setError('Error during payment verification.');
            }
          },
          prefill: {
            name: `${user.firstName} ${user.lastName}`,
            email: user.email,
            contact: user.contact || "",
          },
          theme: {
            color: "#4F46E5",
          },
          modal: {
            ondismiss: function() {
              setSubmitting(false);
            }
          }
        };

        const rzp = new window.Razorpay(options);
        rzp.open();
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to initiate booking/payment');
      setSubmitting(false);
    }
  }

  if (loading) {
    return <div className="text-center py-8">Loading companies...</div>
  }

  return (
    <div>
      <h2 className="text-2xl font-bold text-gray-900 mb-6">Book a Courier</h2>

      {error && (
        <div className="mb-4 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-md">
          {error}
        </div>
      )}

      {success && (
        <div className="mb-4 bg-green-50 border border-green-200 text-green-700 px-4 py-3 rounded-md">
          {success}
        </div>
      )}

      <form onSubmit={handleSubmit} className="bg-gray-50 rounded-lg p-6 max-w-2xl">
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Courier Company *
            </label>
            <select
              value={formData.courierCompanyId}
              onChange={(e) => setFormData({ ...formData, courierCompanyId: e.target.value })}
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            >
              <option value="">Select a company</option>
              {companies.map((company) => (
                <option key={company.id} value={company.id}>
                  {company.companyName} - {company.city}, {company.state}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Courier Category *
            </label>
            <select
              value={formData.courierType}
              onChange={(e) => setFormData({ ...formData, courierType: e.target.value })}
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            >
              <option value="">Select a category</option>
              <option value="DOCUMENT">Document</option>
              <option value="PARCEL">Parcel</option>
              <option value="GIFT">Gift</option>
              <option value="LUGGAGE">Luggage</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Weight (kg) *
            </label>
            <input
              type="number"
              step="0.01"
              min="0"
              value={formData.weight}
              onChange={(e) => setFormData({ ...formData, weight: e.target.value })}
              required
              placeholder="0.00"
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Receiver Name *
            </label>
            <input
              type="text"
              value={formData.receiverName}
              onChange={(e) => setFormData({ ...formData, receiverName: e.target.value })}
              required
              placeholder="Receiver's full name"
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Receiver Address *
            </label>
            <textarea
              value={formData.receiverAddress}
              onChange={(e) => setFormData({ ...formData, receiverAddress: e.target.value })}
              required
              rows="3"
              placeholder="Complete receiver address"
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            />
          </div>

          <button
            type="submit"
            disabled={submitting}
            className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {submitting ? 'Processing...' : 'Book Courier & Pay'}
          </button>
        </div>
      </form>
    </div>
  )
}

export default BookCourierView