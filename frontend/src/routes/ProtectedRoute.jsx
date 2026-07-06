import { Navigate, Outlet, useLocation } from 'react-router-dom'

function getStoredUser() {
  try {
    return JSON.parse(localStorage.getItem('usuario'))
  } catch {
    return null
  }
}

function isTokenInvalidoOExpirado(token) {
  try {
    const [, payload] = token.split('.')

    if (!payload) {
      return true
    }

    // Esto convierte la base64URL en base 64 normal
    // "g" significa global, o sea, reemplazar todas las apariciones y no solo la primera
    const base64 = payload.replace(/-/g, '+').replace(/_/g, '/')
    // Acá verifica que el largo de base64 sea múltiplo de 4
    const base64ConPadding = base64.padEnd(Math.ceil(base64.length / 4) * 4, '=')
    // Esto decodifica el texto Base64 y devuelve un JSON
    // después ese objeto json se convierte en un objeto JS
    const datos = JSON.parse(atob(base64ConPadding))

    if (!datos.exp) {
      return true
    }

    return datos.exp * 1000 <= Date.now()
  } catch {
    return true
  }
}

const ProtectedRoute = ({ allowedRoles = [] }) => {
  const location = useLocation()
  const token = localStorage.getItem('token')
  const usuario = getStoredUser()

  if (!token || !usuario || isTokenInvalidoOExpirado(token)) {
    localStorage.removeItem('token')
    localStorage.removeItem('usuario')
    return <Navigate to="/login" replace state={{ from: location }} />
  }

  const tieneRolPermitido =
    allowedRoles.length === 0 || allowedRoles.includes(usuario.rol)

  if (!tieneRolPermitido) {
    return <Navigate to="/" replace />
  }

  return <Outlet />
}

export default ProtectedRoute
