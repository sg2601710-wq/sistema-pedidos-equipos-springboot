import api from '../api/httpClient'

export async function registrarUsuario(datos) {
  const response = await api.post('/auth/register', datos)
  return response.data.data
}

export async function iniciarSesion(datos) {
  const response = await api.post('/auth/login', datos)
  return response.data.data
}
