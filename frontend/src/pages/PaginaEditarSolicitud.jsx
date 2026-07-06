import { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { actualizarSolicitud, listarSolicitudesPorId } from '../services/solicitudesService.js'
const MAX_MOTIVO_CARACTERES = 150;



function normalizarSolicitud(solicitud) {
  return {
    ...solicitud,
    fechaRetiro: solicitud.fechaRetiro?.slice(0, 10),
    fechaDevolucion: solicitud.fechaDevolucion?.slice(0, 10),
  }
}

function PaginaEditarSolicitud() {
  // useParams nos sirve para leer los parámetros dinámicos de la URL
  const { id } = useParams()
  const navigate = useNavigate()
  const [form, setForm] = useState({
    fechaRetiro: '',
    fechaDevolucion: '',
    motivo: '',
  })
  const [solicitud, setSolicitud] = useState(null)
  const [cargando, setCargando] = useState(true)
  const [guardando, setGuardando] = useState(false)
  const [error, setError] = useState('')

  // useEffect se usa para ejecutar código después de que el componente se renderiza
  useEffect(() => {
    let activo = true

    async function cargarSolicitud() {
      setCargando(true)
      setError('')

      try {
        const solicitudEncontrada = normalizarSolicitud(await listarSolicitudesPorId(id))

        if (activo) {
          setSolicitud(solicitudEncontrada)
          setForm({
            fechaRetiro: solicitudEncontrada.fechaRetiro ?? '',
            fechaDevolucion: solicitudEncontrada.fechaDevolucion ?? '',
            motivo: solicitudEncontrada.motivo ?? '',
          })
        }
      } catch (err) {
        if (activo) {
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
    // Este [id] indica que esto se tiene que ejecutar cada vez que se carga la página o cada vez que se modifica el id
  }, [id])

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')

    if (!form.fechaRetiro || !form.fechaDevolucion || !form.motivo.trim()) {
      setError('Completa todos los campos para actualizar la solicitud.')
      return
    }

    if (form.motivo.length > MAX_MOTIVO_CARACTERES) {
      setError(`El motivo no puede superar los ${MAX_MOTIVO_CARACTERES} caracteres.`)
      return
    }

    if (form.fechaRetiro >= form.fechaDevolucion) {
      setError('La fecha de retiro debe ser anterior a la fecha de devolución.')
      return
    }

    setGuardando(true)

    try {
      await actualizarSolicitud(id, {
        fechaRetiro: form.fechaRetiro,
        fechaDevolucion: form.fechaDevolucion,
        motivo: form.motivo.trim(),
      })
      navigate(`/solicitudes/${id}`)
    } catch (err) {
      setError(err.response?.data?.message || 'No se pudo actualizar la solicitud.')
    } finally {
      setGuardando(false)
    }
  }

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

  return (
    <>
      <div className="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-3">
        <div className="text-start">
          <span className="badge text-bg-primary mb-2">Edición</span>
          <h2 className="h3 mb-1">Editar solicitud #{solicitud.id}</h2>
          <p className="text-muted">Modifica fechas y motivo mientras la solicitud esté pendiente.</p>
        </div>
        <Link to={`/solicitudes/${id}`} className="btn btn-outline-secondary">
          <i className="bi bi-arrow-left me-2"></i>
          Volver
        </Link>
      </div>

      <form className="card form-card" onSubmit={handleSubmit}>
        <div className="card-body p-4">
          {error && <div className="alert alert-danger py-2">{error}</div>}

          {solicitud.estado !== 'pendiente' && (
            <div className="alert alert-warning">
              Solo se pueden editar solicitudes pendientes. Estado actual: {solicitud.estado}.
            </div>
          )}

          <div className="row g-3">
            <div className="col-md-6">
              <label className="form-label">Equipo</label>
              <input
                className="form-control"
                type="text"
                value={solicitud.equipo?.nombre ?? `Equipo #${solicitud.equipoId}`}
                disabled
                readOnly
              />
            </div>

            <div className="col-md-3">
              <label className="form-label">Retiro</label>
              <input
                className="form-control"
                name="fechaRetiro"
                type="date"
                value={form.fechaRetiro}
                onChange={handleChange}
                disabled={solicitud.estado !== 'pendiente'}
              />
            </div>

            <div className="col-md-3">
              <label className="form-label">Devolución</label>
              <input
                className="form-control"
                name="fechaDevolucion"
                type="date"
                value={form.fechaDevolucion}
                onChange={handleChange}
                disabled={solicitud.estado !== 'pendiente'}
              />
            </div>

            <div className="col-12">
              <label className="form-label">Motivo</label>
              <textarea
                className="form-control"
                name="motivo"
                rows="4"
                value={form.motivo}
                onChange={handleChange}
                disabled={solicitud.estado !== 'pendiente'}
              ></textarea>
            </div>
          </div>

          <div className="d-flex justify-content-end gap-2 mt-4">
            <button className="btn btn-outline-secondary" type="button" onClick={() => navigate(`/solicitudes/${id}`)}>
              Cancelar
            </button>
            <button className="btn btn-primary" type="submit" disabled={guardando || solicitud.estado !== 'pendiente'}>
              {guardando ? 'Guardando...' : 'Guardar cambios'}
            </button>
          </div>
        </div>
      </form>
    </>
  )
}

export default PaginaEditarSolicitud
