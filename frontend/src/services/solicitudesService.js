import api from '../api/httpClient'

// Esta función limpia todos los filtros que no usamos/estan por defecto
function limpiarFiltros(filtros) {
  // Esto lo vuelve a convertir en un objeto para poder mandarlo al backend
  return Object.fromEntries(
    // Esto convierte a los objetos en una lista del tipo par clave valor
    Object.entries(filtros).filter(
      // Este [, value] hace que se ignore la clave y solo se centre en el valor
      ([, value]) =>
        value !== undefined && value !== null && value !== '' && value !== 'todas' && value !== 'todos',
    ),
  )
}

export async function crearSolicitud(datos) {
  const response = await api.post('/solicitudes', datos)
  // Se hace un return para que el frontend pueda mostrar los datos
  return response.data.data
}

export async function listarSolicitudes(filtros = {}) {
  const response = await api.get('/solicitudes', { params: limpiarFiltros(filtros) })
  return {
    data: response.data.data,
    meta: response.data.meta,
  }
}

export async function obtenerResumenSolicitudes() {
  const response = await api.get('/solicitudes/resumen')
  return response.data.data
}

export async function listarSolicitudesPorId(id) {
  const response = await api.get(`/solicitudes/${id}`)
  return response.data.data
}

export async function listarSolicitudesPorIdHistorial(id) {
  const response = await api.get(`/solicitudes/${id}/historial`)
  return response.data.data
}

export async function cambiarEstadoSolicitud(id, accion) {
  const response = await api.patch(`/solicitudes/${id}/${accion}`)
  return response.data.data
}

export async function actualizarSolicitud(id, datos) {
  const response = await api.put(`/solicitudes/${id}`, datos)
  return response.data.data
}

export async function listarSolicitudesPorEstadoIdEquipoCategoriaRangoFecha(
  id,
  estado,
  categoria,
  fechaDesde,
  fechaHasta,
) {
  const response = await listarSolicitudes({
    estado,
    equipoId: id,
    categoria,
    desde: fechaDesde,
    hasta: fechaHasta,
  })

  return response.data
}
