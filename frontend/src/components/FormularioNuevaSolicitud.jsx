import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { crearSolicitud } from '../services/solicitudesService.js'
import {obtenerFechaLocalHoy} from '../utils/fechas.js'

const MAX_MOTIVO_CARACTERES = 150;

function FormularioNuevaSolicitud({ equipos = [], setEquipos, solicitudes = [], setSolicitudes }) {
  const [form, setForm] = useState({
    equipoId: '',
    fechaRetiro: '',
    fechaDevolucion: '',
    motivo: '',
  })
  const [error, setError] = useState('')
  const [cargando, setCargando] = useState(false)
  const navigate = useNavigate()

  const equiposSolicitables = equipos.filter(
    (equipo) => equipo.estado !== 'mantenimiento' && equipo.estado !== 'baja',
  )
  const equipoSeleccionado = equipos.find((equipo) => equipo.id === Number(form.equipoId))
  const haySuperposicion =
    form.equipoId &&
    form.fechaRetiro &&
    form.fechaDevolucion &&
    // Esta función verifica que exista alguna solicitud aprobada para el mismo equipo o que las fechas
    // no se superpongan
    solicitudes.some((solicitud) => {
      const fechaRetiroExistente = solicitud.fechaRetiro?.slice(0, 10)
      const fechaDevolucionExistente = solicitud.fechaDevolucion?.slice(0, 10)

      return (
        Number(solicitud.equipoId) === Number(form.equipoId) &&
        solicitud.estado === 'aprobada' &&
        form.fechaRetiro < fechaDevolucionExistente &&
        form.fechaDevolucion > fechaRetiroExistente
      )
    })

    // "e" es el evento de cambio
    // "e.target" representa el input
    // los corchetes [e.target.name] nos permiten usar el valor como nomrbe de la propiedad
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const normalizarSolicitud = (solicitud) => {
    return {
      ...solicitud,
      fechaRetiro: solicitud.fechaRetiro?.slice(0, 10),
      fechaDevolucion: solicitud.fechaDevolucion?.slice(0, 10),
    }
  }

  const handleSubmit = async (e) => {
    // Esto nos evita el comportamiento por defecto de los formularios. O sea, que evita que el navegador
    // recargue la página al hacer el submit
    e.preventDefault()
    setError('')

    if (!form.equipoId || !form.fechaRetiro || !form.fechaDevolucion || !form.motivo.trim()) {
      setError('Completa todos los campos para crear la solicitud.')
      return
    }

    if (form.fechaRetiro >= form.fechaDevolucion) {
      setError('La fecha de retiro debe ser anterior a la fecha de devolución.')
      return
    }
    if (form.motivo.length > MAX_MOTIVO_CARACTERES) {
      setError(`El motivo no puede superar los ${MAX_MOTIVO_CARACTERES} caracteres.`)
      return
    }

    if (form.fechaRetiro < obtenerFechaLocalHoy()) {
      setError('La fecha de retiro no puede ser una fecha pasada.')
      return
    }
    if (form.fechaDevolucion < obtenerFechaLocalHoy()) {
      setError('La fecha de devolución no puede ser una fecha pasada.')
      return
    }

    if (haySuperposicion) {
      setError('Este equipo no esta disponible para las fechas solicitadas.')
      return
    }

    setCargando(true)

    try {
      const creada = await crearSolicitud({
        equipoId: Number(form.equipoId),
        fechaRetiro: form.fechaRetiro,
        fechaDevolucion: form.fechaDevolucion,
        motivo: form.motivo.trim(),
      })
      const actualizadas = [normalizarSolicitud(creada), ...solicitudes]
      setSolicitudes(actualizadas)
      // Esto actualiza el estado del equipo
      if (creada.estado === 'aprobada' && creada.equipo) {
        setEquipos?.(
          equipos.map((equipo) =>
            equipo.id === creada.equipo.id ? { ...equipo, estado: creada.equipo.estado } : equipo,
          ),
        )
      }
      navigate('/solicitudes', {
        state: {
          mensajeExito: `Solicitud #${creada.id} creada correctamente.`,
        },
      })
    } catch (err) {
      const codigo = err.response?.data?.error?.code
      setError(
        codigo === 'SOLICITUD_OVERLAP'
          ? 'Este equipo no esta disponible para las fechas solicitadas.'
          : err.response?.data?.message || 'No se pudo crear la solicitud.',
      )
    } finally {
      setCargando(false)
    }
  }

  return (
    <form className="card form-card" onSubmit={handleSubmit}>
      <div className="card-body p-4">
        {error && <div className="alert alert-danger py-2">{error}</div>}

        <div className="row g-3">
          <div className="col-md-6">
            <label className="form-label">Equipo</label>
            <select
              className="form-select"
              name="equipoId"
              value={form.equipoId}
              onChange={handleChange}
            >
              <option value="">Seleccionar equipo</option>
              {equiposSolicitables.map((equipo) => (
                <option key={equipo.id} value={equipo.id}>
                  {equipo.nombre} - {equipo.codigoInventario}
                </option>
              ))}
            </select>
            {haySuperposicion && (
              <div className="form-text text-danger">
                <i className="bi bi-calendar-x me-1"></i>
                Este equipo no esta disponible para las fechas solicitadas.
              </div>
            )}
            {equipoSeleccionado?.requiereAutorizacion && (
              <div className="form-text text-warning">
                <i className="bi bi-shield-lock me-1"></i>
                Este equipo requiere aprobación de un encargado o administrador.
              </div>
            )}
          </div>

          <div className="col-md-3">
            <label className="form-label">Retiro</label>
            <input
              className="form-control"
              name="fechaRetiro"
              type="date"
              value={form.fechaRetiro}
              onChange={handleChange}
            />
          </div>

          <div className="col-md-3">
            <label className="form-label">Devolucion</label>
            <input
              className="form-control"
              name="fechaDevolucion"
              type="date"
              value={form.fechaDevolucion}
              onChange={handleChange}
            />
          </div>

          <div className="col-12">
            <label className="form-label">Motivo</label>
            <textarea
              className="form-control"
              name="motivo"
              rows="4"
              placeholder="Describe para que necesitas el equipo"
              value={form.motivo}
              onChange={handleChange}
            ></textarea>
          </div>
        </div>

        <div className="d-flex justify-content-end gap-2 mt-4">
          <button className="btn btn-outline-secondary" type="button" onClick={() => navigate('/solicitudes')}>
            Cancelar
          </button>
          <button className="btn btn-primary" type="submit" disabled={cargando}>
            {cargando ? 'Guardando...' : 'Crear solicitud'}
          </button>
        </div>
      </div>
    </form>
  )
}

export default FormularioNuevaSolicitud
