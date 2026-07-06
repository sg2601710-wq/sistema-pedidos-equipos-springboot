import { Link } from 'react-router-dom'
import { formatearFecha } from '../utils/fechas.js'

const ROLES_ADMINISTRATIVOS = ['admin', 'encargado']


function getUsuarioActual() {
  try {
    return JSON.parse(localStorage.getItem('usuario')) ?? {}
  } catch {
    return {}
  }
}

function ListaSolicitudes({ solicitudes = [], equipos = [], onCambiarEstado }) {
  const usuarioActual = getUsuarioActual()
  const esRolAdministrativo = ROLES_ADMINISTRATIVOS.includes(usuarioActual.rol)
  const esSolicitudPropia = (solicitud) => Number(solicitud.usuarioId) === Number(usuarioActual.id)
  const puedeEditar = (solicitud) =>
    solicitud.estado === 'pendiente' && (esRolAdministrativo || esSolicitudPropia(solicitud))
  const puedeCancelar = (solicitud) =>
    ['pendiente', 'aprobada'].includes(solicitud.estado) &&
    (esRolAdministrativo || esSolicitudPropia(solicitud))

  const estadoClase = {
    pendiente: 'text-bg-warning',
    aprobada: 'text-bg-success',
    rechazada: 'text-bg-danger',
    cancelada: 'text-bg-secondary',
    devuelta: 'text-bg-info',
  }

  const nombreEquipo = (equipoId) =>
    // ?? significa que si lo de la izquierda no existe (null o undefined) que use lo de la derecha
    equipos.find((equipo) => equipo.id === Number(equipoId))?.nombre ?? `Equipo #${equipoId}`

  if (solicitudes.length === 0) {
    return <div className="alert alert-light border">No hay solicitudes para los filtros seleccionados.</div>
  }

  return (
    <div className="table-responsive card table-card">
      <table className="table solicitudes-table align-middle mb-0">
        <colgroup>
          <col className="solicitudes-table-equipo" />
          <col className="solicitudes-table-fechas" />
          <col className="solicitudes-table-motivo" />
          <col className="solicitudes-table-estado" />
          <col className="solicitudes-table-acciones" />
        </colgroup>
        <thead>
          <tr>
            <th>Equipo</th>
            <th>Fechas</th>
            <th>Motivo</th>
            <th>Estado</th>
            <th className="text-end">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {solicitudes.map((solicitud) => (
            <tr key={solicitud.id}>
              <td>
                <strong>{nombreEquipo(solicitud.equipoId)}</strong>
                <div className="text-muted small">Solicitud #{solicitud.id}</div>
              </td>
              <td>
                <div>{formatearFecha(solicitud.fechaRetiro)}</div>
                <div className="text-muted small">hasta {formatearFecha(solicitud.fechaDevolucion)}</div>
              </td>
              <td className="motivo-cell">{solicitud.motivo}</td>
              <td>
                <span className={`badge ${estadoClase[solicitud.estado] ?? 'text-bg-light'}`}>
                  {solicitud.estado}
                </span>
              </td>
              <td>
                <div className="acciones-solicitud d-flex justify-content-end gap-2">
                  <Link className="btn btn-sm btn-outline-primary" to={`/solicitudes/${solicitud.id}`}>
                    Ver
                  </Link>

                  {puedeEditar(solicitud) && (
                    <Link className="btn btn-sm btn-outline-secondary" to={`/solicitudes/${solicitud.id}/editar`}>
                      Editar
                    </Link>
                  )}

                  {esRolAdministrativo && solicitud.estado === 'pendiente' && (
                    <>
                      <button
                        className="btn btn-sm btn-outline-success"
                        type="button"
                        onClick={() => onCambiarEstado(solicitud.id, 'aprobada')}
                      >
                        Aprobar
                      </button>
                      <button
                        className="btn btn-sm btn-outline-danger"
                        type="button"
                        onClick={() => onCambiarEstado(solicitud.id, 'rechazada')}
                      >
                        Rechazar
                      </button>
                    </>
                  )}

                  {esRolAdministrativo && solicitud.estado === 'aprobada' && (
                    <button
                      className="btn btn-sm btn-outline-info"
                      type="button"
                      onClick={() => onCambiarEstado(solicitud.id, 'devuelta')}
                    >
                      Devolver
                    </button>
                  )}

                  {puedeCancelar(solicitud) && (
                    <button
                      className="btn btn-sm btn-outline-secondary"
                      type="button"
                      onClick={() => onCambiarEstado(solicitud.id, 'cancelada')}
                    >
                      Cancelar
                    </button>
                  )}
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default ListaSolicitudes
