# CourierSphere Frontend

A complete React + Vite frontend application for the Courier Management System.

## Features

- **Authentication**: Role-based login (Admin, Customer, Courier Company, Delivery Person)
- **Protected Routes**: React Router with role-based access control
- **State Management**: Redux Toolkit for global state
- **API Integration**: Axios with JWT token interceptor
- **UI**: Tailwind CSS for modern, responsive design

## Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Backend server running on `http://localhost:8080`

## Installation

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

## Running the Application

Start the development server:
```bash
npm run dev
```

The application will be available at `http://localhost:3000`

## Building for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

## Project Structure

```
frontend/
├── src/
│   ├── api/              # API service layer
│   │   ├── axios.js      # Axios instance with interceptors
│   │   ├── authApi.js    # Authentication APIs
│   │   ├── adminApi.js   # Admin APIs
│   │   ├── customerApi.js
│   │   ├── courierCompanyApi.js
│   │   └── deliveryPersonApi.js
│   ├── components/       # Reusable components
│   │   ├── Layout.jsx
│   │   └── ProtectedRoute.jsx
│   ├── pages/            # Page components
│   │   ├── Login.jsx
│   │   ├── admin/
│   │   ├── customer/
│   │   ├── courier-company/
│   │   └── delivery-person/
│   ├── store/            # Redux store
│   │   ├── store.js
│   │   └── slices/
│   │       └── authSlice.js
│   ├── App.jsx           # Main app component with routing
│   ├── main.jsx          # Entry point
│   └── index.css         # Global styles
├── package.json
├── vite.config.js
└── tailwind.config.js
```

## Authentication

The application uses JWT tokens for authentication. Tokens are:
- Stored in localStorage
- Automatically attached to API requests via Axios interceptor
- Cleared on logout

## Role-Based Access

- **ADMIN**: Full access to view all entities (companies, customers, delivery persons, couriers)
- **CUSTOMER**: View profile, book couriers, track own couriers
- **COURIER_COMPANY**: View company couriers, assign delivery persons, manage delivery persons
- **DELIVERY_PERSON**: View assigned couriers, update delivery status

## API Configuration

The frontend is configured to proxy API requests to `http://localhost:8080` (see `vite.config.js`).

If your backend runs on a different port, update the proxy configuration in `vite.config.js`.

## Notes

1. **JWT Token**: The backend should return a token in the login response. If the token is in a different format, update the login logic in `src/pages/Login.jsx`.

2. **Customer Couriers**: The endpoint for customers to view their own couriers (`/customer/{customerId}/couriers`) needs to be implemented in the backend. The frontend includes a placeholder for this feature.

3. **Role Names**: Ensure the backend returns role names matching: `ADMIN`, `CUSTOMER`, `COURIER_COMPANY`, `DELIVERY_PERSON`.

## Troubleshooting

- **CORS Issues**: Ensure the backend has `@CrossOrigin("*")` on controllers
- **401 Errors**: Check if the JWT token is being sent correctly in the Authorization header
- **API Errors**: Verify the backend is running on port 8080
