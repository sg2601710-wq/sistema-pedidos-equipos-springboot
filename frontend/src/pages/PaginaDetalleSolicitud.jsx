import { useEffect, useState } from 'react'
import { Link, useOutletContext, useParams } from 'react-router-dom'
import {
  listarSolicitudesPorId,
  listarSolicitudesPorIdHistorial,
} from '../services/solicitudesService.js'
import { formatearFecha, formatearFechaHora } from '../utils/fechas.js'

const estadoClase = {
  pendiente: 'text-bg-warning',
  aprobada: 'text-bg-success',
  rechazada: 'text-bg-danger',
  cancelada: 'text-bg-secondary',
  devuelta: 'text-bg-info',
}

const ROLES_ADMINISTRATIVOS = ['admin', 'encargado']

function getUsuarioActual() {
  try {
    return JSON.parse(localStorage.getItem('usuario')) ?? null
  } catch {
    return null
  }
}

// Esta función le da formato al valor que le pasemos en caso de que sea un objeto como string,
// si no se puede, solamente nos devuelve el valor y fue
function formatearValorHistorial(valor) {
  if (!valor) {
    return '-'
  }

  try {
    const parsed = JSON.parse(valor)
    return parsed.estado ?? JSON.stringify(parsed)
  } catch {
    return valor
  }
}

function parsearValorHistorial(valor) {
  if (!valor) {
    return null
  }

  try {
    return JSON.parse(valor)
  } catch {
    return null
  }
}

function formatearFechaSolicitud(valor) {
  return formatearFecha(valor)
}

function formatearAccion(accion) {
  const acciones = {
    creacion: 'Creación',
    edicion: 'Edición',
    aprobacion: 'Aprobación',
    rechazo: 'Rechazo',
    cancelacion: 'Cancelación',
    devolucion: 'Devolución',
  }

  return acciones[accion] ?? accion
}

function obtenerCambiosEdicion(item) {
  const valorAnterior = parsearValorHistorial(item.valorAnterior)
  const valorNuevo = parsearValorHistorial(item.valorNuevo)

  if (!valorAnterior || !valorNuevo) {
    return []
  }

  return [
    {
      campo: 'Fecha de retiro',
      anterior: formatearFechaSolicitud(valorAnterior.fechaRetiro),
      nuevo: formatearFechaSolicitud(valorNuevo.fechaRetiro),
    },
    {
      campo: 'Fecha de devolución',
      anterior: formatearFechaSolicitud(valorAnterior.fechaDevolucion),
      nuevo: formatearFechaSolicitud(valorNuevo.fechaDevolucion),
    },
    {
      campo: 'Motivo',
      anterior: valorAnterior.motivo ?? '-',
      nuevo: valorNuevo.motivo ?? '-',
    },
  ].filter((cambio) => cambio.anterior !== cambio.nuevo)
}

function PaginaDetalleSolicitud() {
  const { id } = useParams()
  const { equipos } = useOutletContext()
  const [solicitud, setSolicitud] = useState(null)
  const [historial, setHistorial] = useState([])
  const [cargando, setCargando] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    let activo = true

    async function cargarSolicitud() {
      setCargando(true)
      setError('')

      try {
        const [solicitudEncontrada, historialEncontrado] = await Promise.all([
          listarSolicitudesPorId(id),
          listarSolicitudesPorIdHistorial(id),
        ])

        if (activo) {
          setSolicitud({
            ...solicitudEncontrada,
            fechaRetiro: solicitudEncontrada.fechaRetiro?.slice(0, 10),
            fechaDevolucion: solicitudEncontrada.fechaDevolucion?.slice(0, 10),
          })
          setHistorial(historialEncontrado)
        }
      } catch (err) {
        if (activo) {
          setSolicitud(null)
          setHistorial([])
          setError(err.response?.data?.message || 'No se pudo cargar la solicitud.')
        }
      } finally {
        if (activo) {
          setCargando(false)
        }
      }
    }

    cargarSolicitud()

    return () => {
      activo = false
    }
  }, [id])

  if (cargando) {
    return <div className="alert alert-info">Cargando solicitud...</div>
  }

  if (!solicitud) {
    return (
      <section className="text-center py-5">
        <span className="badge text-bg-secondary mb-3">Solicitud #{id}</span>
        <h2 className="h3">Solicitud no encontrada</h2>
        <p className="text-muted mb-4">{error || 'No existe una solicitud con ese identificador.'}</p>
        <Link to="/solicitudes" className="btn btn-primary">
          Volver al listado
        </Link>
      </section>
    )
  }

  const equipo = solicitud.equipo ?? equipos.find((item) => item.id === Number(solicitud.equipoId))
  const usuarioActual = getUsuarioActual()
  const solicitante =
    solicitud.usuario ??
    (Number(usuarioActual?.id) === Number(solicitud.usuarioId) ? usuarioActual : null)
  const autorizador = solicitud.autorizador ?? null
  const puedeEditar =
    solicitud.estado === 'pendiente' &&
    (ROLES_ADMINISTRATIVOS.includes(usuarioActual?.rol) ||
      Number(usuarioActual?.id) === Number(solicitud.usuarioId))

  return (
    <>
      <div className="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-3">
        <div className="text-start">
          <span className="badge text-bg-primary mb-2">Detalle</span>
          <h2 className="h3 mb-1">Solicitud #{solicitud.id}</h2>
          <p className="text-muted">Informacion completa del pedido y su historial de cambios.</p>
        </div>
        <div className="d-flex flex-wrap gap-2">
          {puedeEditar && (
            <Link to={`/solicitudes/${id}/editar`} className="btn btn-primary">
              <i className="bi bi-pencil-square me-2"></i>
              Editar
            </Link>
          )}
          <Link to="/solicitudes" className="btn btn-outline-secondary">
            <i className="bi bi-arrow-left me-2"></i>
            Volver
          </Link>
        </div>
      </div>

      <section className="row g-3 mb-4">
        <div className="col-lg-8">
          <article className="card detail-card h-100">
            <div className="card-body p-4">
              <div className="d-flex flex-wrap justify-content-between gap-2 mb-3">
                <div>
                  <h3 className="h5 mb-1">{equipo?.nombre ?? `Equipo #${solicitud.equipoId}`}</h3>
                  <p className="text-muted mb-0">{equipo?.codigoInventario ?? 'Sin codigo de inventario'}</p>
                </div>
                <span className={`badge align-self-start ${estadoClase[solicitud.estado] ?? 'text-bg-light'}`}>
                  {solicitud.estado}
                </span>
              </div>

              <dl className="row mb-0 detail-list">
                <dt className="col-sm-4">Solicitante</dt>
                <dd className="col-sm-8">{solicitante?.nombre ?? `Usuario #${solicitud.usuarioId}`}</dd>

                <dt className="col-sm-4">Email</dt>
                <dd className="col-sm-8">{solicitante?.email ?? '-'}</dd>

                <dt className="col-sm-4">Categoria</dt>
                <dd className="col-sm-8">{equipo?.categoria ?? '-'}</dd>

                <dt className="col-sm-4">Ubicacion</dt>
                <dd className="col-sm-8">{equipo?.ubicacion ?? '-'}</dd>

                <dt className="col-sm-4">Retiro</dt>
                <dd className="col-sm-8">{formatearFecha(solicitud.fechaRetiro)}</dd>

                <dt className="col-sm-4">Devolucion</dt>
                <dd className="col-sm-8">{formatearFecha(solicitud.fechaDevolucion)}</dd>

                <dt className="col-sm-4">Autorizado por</dt>
                <dd className="col-sm-8">{autorizador?.nombre ?? 'Pendiente'}</dd>

                <dt className="col-sm-4">Motivo</dt>
                <dd className="col-sm-8">{solicitud.motivo}</dd>
              </dl>
            </div>
          </article>
        </div>

        <div className="col-lg-4">
          <article className="card detail-card h-100">
            <div className="card-body p-4">
              <h3 className="h5 mb-3">Equipo</h3>
              <p className="mb-2">
                <strong>Estado:</strong> {equipo?.estado ?? '-'}
              </p>
              <p className="mb-2">
                <strong>Requiere autorizacion:</strong>{' '}
                {equipo?.requiereAutorizacion ? 'Si' : 'No'}
              </p>
              <p className="mb-0">
                <strong>Inventario:</strong> {equipo?.codigoInventario ?? '-'}
              </p>
            </div>
          </article>
        </div>
      </section>

      <section className="card detail-card">
        <div className="card-body p-4">
          <h3 className="h5 mb-3">Historial</h3>
          <div className="timeline">
            {historial.length === 0 && (
              <div className="alert alert-light border mb-0">No hay movimientos registrados.</div>
            )}
            {historial.map((item, index) => (
              <div className="timeline-item" key={`${item.accion}-${index}`}>
                <div className="timeline-dot"></div>
                <div>
                  <strong>{formatearAccion(item.accion)}</strong>
                  <p className="text-muted mb-1">
                    {item.usuario?.nombre ?? `Usuario #${item.usuarioId}`} - {formatearFechaHora(item.fechaHora)}
                  </p>
                  {item.accion === 'edicion' ? (
                    <div className="small d-grid gap-1">
                      {obtenerCambiosEdicion(item).map((cambio) => (
                        <p className="mb-0" key={cambio.campo}>
                          <span className="fw-semibold">{cambio.campo}:</span>{' '}
                          <span className="badge text-bg-light border">{cambio.anterior}</span>{' '}
                          a <span className="badge text-bg-light border">{cambio.nuevo}</span>
                        </p>
                      ))}
                    </div>
                  ) : (
                    <p className="mb-0 small">
                      Cambio de{' '}
                      <span className="badge text-bg-light border">
                        {formatearValorHistorial(item.valorAnterior)}
                      </span>{' '}
                      a{' '}
                      <span
                        className={`badge ${estadoClase[formatearValorHistorial(item.valorNuevo)] ?? 'text-bg-light'}`}
                      >
                        {formatearValorHistorial(item.valorNuevo)}
                      </span>
                    </p>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>
    </>
  )
}

export default PaginaDetalleSolicitud
