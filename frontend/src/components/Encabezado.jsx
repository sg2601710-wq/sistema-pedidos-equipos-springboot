import { NavLink, useNavigate } from 'react-router-dom'

const ROLES_ADMINISTRATIVOS = ['admin', 'encargado']

const enlaces = [
  { to: '/', texto: 'Inicio', icono: 'bi-house' },
  { to: '/solicitudes', texto: 'Solicitudes', icono: 'bi-list-check' },
  { to: '/solicitudes/nueva', texto: 'Nueva solicitud', icono: 'bi-plus-circle' },
  {
    to: '/resumen',
    texto: 'Resumen',
    icono: 'bi-speedometer2',
    roles: ROLES_ADMINISTRATIVOS,
  },
]

function Encabezado() {
  const navigate = useNavigate()
  const usuario = JSON.parse(localStorage.getItem('usuario') ?? '{}')
  const enlacesVisibles = enlaces.filter(
    (enlace) => !enlace.roles || enlace.roles.includes(usuario.rol),
  )

  const cerrarSesion = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('usuario')
    navigate('/login')
  }

  return (
    <header className="app-header mb-4">
      <div className="container">
        <div className="d-flex flex-column flex-lg-row justify-content-between gap-3">
          <div className="text-start">
            <h1 className="h2 mb-1">
              <i className="bi bi-pc-display-horizontal me-2"></i>
              Gestión de equipos
            </h1>
            <p className="mb-0">Solicitudes, préstamos y devoluciones para laboratorio.</p>
          </div>

          <div className="d-flex flex-column align-items-lg-end gap-2">
            <nav className="d-flex flex-wrap align-items-start gap-2" aria-label="Navegacion principal">
              {enlacesVisibles.map((enlace) => (
                <NavLink
                  // key nos sirve para identificar a cada elemento
                  key={enlace.to}
                  // to define a donde navega el link
                  to={enlace.to}
                  // end hace que el enlace de inicio / solo este activo cuando esta en /
                  end={enlace.to === '/'}
                  className={({ isActive }) => `nav-chip ${isActive ? 'active' : ''}`}
                >
                  <i className={`bi ${enlace.icono} me-1`}></i>
                  {enlace.texto}
                </NavLink>
              ))}
            </nav>

            <div className="d-flex align-items-center gap-2">
              <span className="small text-white-50">{usuario.nombre ?? 'Usuario'}</span>
              <button className="btn btn-sm btn-light" type="button" onClick={cerrarSesion}>
                <i className="bi bi-box-arrow-right me-1"></i>
                Salir
              </button>
            </div>
          </div>
        </div>
      </div>
    </header>
  )
}

export default Encabezado
