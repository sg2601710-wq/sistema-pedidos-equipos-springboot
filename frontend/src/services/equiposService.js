import api from '../api/httpClient'

export async function listarEquipos() {
  const response = await api.get('/equipos')
  return response.data.data
}
