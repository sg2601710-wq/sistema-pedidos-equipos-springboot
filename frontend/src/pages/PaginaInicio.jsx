import { Link, useOutletContext } from 'react-router-dom'
import Equipos from '../components/Equipos.jsx'
import PanelResumen from '../components/PanelResumen.jsx'

function PaginaInicio() {
  const { solicitudes, equipos } = useOutletContext()
  const disponibles = equipos.filter((equipo) => equipo.estado === 'disponible').length

  return (
    <>
      <section className="page-hero mb-4">
        <div>
          <span className="badge text-bg-primary mb-3">Inicio</span>
          <h2 className="h3 mb-2">Panel general de solicitudes</h2>
          <p className="text-muted mb-4">
            Gestiona préstamos de equipos, revisa estados y carga nuevas solicitudes desde un mismo lugar.
          </p>
          <div className="d-flex flex-wrap gap-2 justify-content-center">
            <Link to="/solicitudes" className="btn btn-primary">
              <i className="bi bi-list-check me-2"></i>
              Ver solicitudes
            </Link>
            <Link to="/solicitudes/nueva" className="btn btn-outline-primary">
              <i className="bi bi-plus-circle me-2"></i>
              Crear solicitud
            </Link>
          </div>
        </div>
        <div className="hero-stat">
          <span className="text-muted">Equipos disponibles</span>
          <strong>{disponibles}</strong>
        </div>
      </section>

      <PanelResumen solicitudes={solicitudes} />
      <Equipos equipos={equipos} />
    </>
  )
}

export default PaginaInicio
