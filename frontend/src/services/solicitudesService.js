import api from '../api/httpClient'
import { listarEquipos } from './equiposService.js'

function getUsuarioActual() {
  try {
    return JSON.parse(localStorage.getItem('usuario')) ?? {}
  } catch {
    return {}
  }
}

function normalizarEstado(estado) {
  if (typeof estado !== 'string') {
    return estado
  }

  return estado.toLowerCase()
}

function obtenerEstadoActual(solicitud) {
  const historial = solicitud.historialSolicitud ?? []
  const estadoActual = historial.find((item) => !item.fechaHoraFin) ?? historial[historial.length - 1]

  return normalizarEstado(solicitud.estado ?? estadoActual?.estado)
}

function normalizarHistorial(item) {
  const estadoAnterior = normalizarEstado(item.estadoAnterior)
  const estado = normalizarEstado(item.estado)

  return {
    ...item,
    estadoAnterior,
    estado,
    accion: item.accion ?? 'cambio_estado',
    usuarioId: item.usuarioId ?? item.responsableId,
    usuario: item.usuario ?? {
      id: item.responsableId,
      nombre: item.responsableNombre,
    },
    fechaHora: item.fechaHora ?? item.fechaHoraInicio,
    valorAnterior: item.valorAnterior ?? estadoAnterior,
    valorNuevo: item.valorNuevo ?? estado,
  }
}

function normalizarSolicitud(solicitud) {
  if (!solicitud) {
    return solicitud
  }

  const historialSolicitud = (solicitud.historialSolicitud ?? []).map(normalizarHistorial)
  const equipo = solicitud.equipo
  const solicitante = solicitud.solicitante ?? solicitud.usuario

  return {
    ...solicitud,
    id: solicitud.id ?? solicitud.numSolicitud,
    numSolicitud: solicitud.numSolicitud ?? solicitud.id,
    equipo,
    equipoId: solicitud.equipoId ?? equipo?.id,
    usuario: solicitante,
    solicitante,
    usuarioId: solicitud.usuarioId ?? solicitante?.id,
    estado: obtenerEstadoActual({ ...solicitud, historialSolicitud }),
    historialSolicitud,
  }
}

async function calcularResumen(solicitudes) {
  const hoy = new Date().toISOString().slice(0, 10)
  const equipos = await listarEquipos()
  const equiposDisponiblesPorCategoria = new Map()

  equipos.forEach((equipo) => {
    if (equipo.estado === 'disponible') {
      equiposDisponiblesPorCategoria.set(
        equipo.categoria,
        (equiposDisponiblesPorCategoria.get(equipo.categoria) ?? 0) + 1,
      )
    }
  })

  return {
    fechaReferencia: hoy,
    solicitudesPendientes: solicitudes.filter((solicitud) => solicitud.estado === 'pendiente').length,
    equiposPrestados: equipos.filter((equipo) => equipo.estado === 'prestado').length,
    prestamosVencidos: solicitudes.filter(
      (solicitud) => solicitud.estado === 'aprobada' && solicitud.fechaDevolucion?.slice(0, 10) < hoy,
    ).length,
    equiposDisponiblesPorCategoria: [...equiposDisponiblesPorCategoria.entries()].map(
      ([categoria, cantidad]) => ({ categoria, cantidad }),
    ),
  }
}

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
  const usuarioActual = getUsuarioActual()
  const response = await api.post('/solicitudes', {
    ...datos,
    solicitanteId: datos.solicitanteId ?? usuarioActual.id,
  })
  // Se hace un return para que el frontend pueda mostrar los datos
  return normalizarSolicitud(response.data.data)
}

export async function listarSolicitudes(filtros = {}) {
  const response = await api.get('/solicitudes', { params: limpiarFiltros(filtros) })
  return {
    data: response.data.data.map(normalizarSolicitud),
    meta: response.data.meta,
  }
}

export async function obtenerResumenSolicitudes() {
  const response = await listarSolicitudes({ page: 1, limit: 100 })
  return calcularResumen(response.data)
}

export async function listarSolicitudesPorId(id) {
  const response = await listarSolicitudes({ numSolicitud: id, page: 1, limit: 1 })

  if (!response.data.length) {
    throw new Error('Solicitud no encontrada.')
  }

  return response.data[0]
}

export async function listarSolicitudesPorIdHistorial(id) {
  const solicitud = await listarSolicitudesPorId(id)
  return solicitud.historialSolicitud
}

export async function cambiarEstadoSolicitud(id, accion) {
  const response = await api.patch(`/solicitudes/${id}/${accion}`)
  return normalizarSolicitud(response.data.data)
}

export async function actualizarSolicitud(id, datos) {
  const response = await api.put(`/solicitudes/${id}`, datos)
  return normalizarSolicitud(response.data.data)
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
