import { Link, useOutletContext } from 'react-router-dom'
import FormularioNuevaSolicitud from '../components/FormularioNuevaSolicitud.jsx'

function PaginaNuevaSolicitud() {
  const { equipos, setEquipos, solicitudes, setSolicitudes } = useOutletContext()

  return (
    <>
      <div className="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-3">
        <div className="text-start">
          <span className="badge text-bg-primary mb-2">Nueva solicitud</span>
          <h2 className="h3 mb-1">Solicitar equipo</h2>
          <p className="text-muted">Carga el equipo, rango de fechas y motivo del prestamo.</p>
        </div>
        <Link to="/solicitudes" className="btn btn-outline-secondary">
          <i className="bi bi-arrow-left me-2"></i>
          Volver
        </Link>
      </div>

      <FormularioNuevaSolicitud
        equipos={equipos}
        setEquipos={setEquipos}
        solicitudes={solicitudes}
        setSolicitudes={setSolicitudes}
      />
    </>
  )
}

export default PaginaNuevaSolicitud
