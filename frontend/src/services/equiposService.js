import api from '../api/httpClient'

function normalizarEstadoEquipo(estado) {
  if (typeof estado !== 'string') {
    return estado
  }

  const estadoNormalizado = estado.toLowerCase()
  return ['de_baja', 'debaja'].includes(estadoNormalizado) ? 'baja' : estadoNormalizado
}

function normalizarEquipo(equipo) {
  return {
    ...equipo,
    estado: normalizarEstadoEquipo(equipo.estado),
  }
}

export async function listarEquipos() {
  const response = await api.get('/equipos', { params: { size: 100 } })
  return response.data.data.map(normalizarEquipo)
}
