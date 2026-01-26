# Setup Instructions

## Quick Start

1. **Install Dependencies**
   ```bash
   cd frontend
   npm install
   ```

2. **Start Backend**
   - Ensure your Spring Boot backend is running on `http://localhost:8080`
   - Backend should have CORS enabled (`@CrossOrigin("*")`)

3. **Start Frontend**
   ```bash
   npm run dev
   ```

4. **Access Application**
   - Open `http://localhost:3000` in your browser
   - Use the login page with appropriate credentials

## Default Credentials

Based on `application.properties`:
- **Admin**: `admin@couriersphere.com` / `admin123`

For other roles, you'll need to register or use existing accounts in your database.

## Important Notes

### JWT Token Handling

The frontend is configured to handle JWT tokens, but if your backend doesn't return tokens yet:

1. The login will create a placeholder token
2. Update the login logic in `src/pages/Login.jsx` when JWT is implemented
3. The token is stored in localStorage and sent via `Authorization: Bearer <token>` header

### Role Mapping

The frontend expects these role names:
- `ADMIN`
- `CUSTOMER`
- `COURIER_COMPANY`
- `DELIVERY_PERSON`

If your backend uses different role names, update the role mapping in:
- `src/pages/Login.jsx`
- `src/App.jsx`
- `src/components/ProtectedRoute.jsx`

### Missing Backend Endpoints

The following endpoint needs to be implemented in the backend:
- `GET /api/customer/{customerId}/couriers` - For customers to view their own couriers

The frontend includes a placeholder for this feature with a user-friendly message.

### API Base URL

The frontend proxies API requests to `http://localhost:8080` via Vite proxy (see `vite.config.js`).

To change the backend URL:
1. Update `vite.config.js` proxy target
2. Or update `src/api/axios.js` baseURL directly

## Troubleshooting

### CORS Errors
- Ensure backend controllers have `@CrossOrigin("*")`
- Check backend is running on port 8080

### 401 Unauthorized
- Check if token is being sent in Authorization header
- Verify token format matches backend expectations
- Check browser console for token storage

### API Connection Issues
- Verify backend is running
- Check network tab in browser DevTools
- Verify proxy configuration in `vite.config.js`

### Role Access Issues
- Verify role names match between frontend and backend
- Check Redux store for stored role (use Redux DevTools)
- Verify ProtectedRoute component logic
