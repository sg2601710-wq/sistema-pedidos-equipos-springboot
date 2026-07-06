function Equipos({ equipos = [] }) {
  const estadoClase = {
    disponible: 'text-bg-success',
    prestado: 'text-bg-primary',
    mantenimiento: 'text-bg-warning',
    baja: 'text-bg-secondary',
  }

  return (
    <section className="mb-4">
      <div className="d-flex align-items-center justify-content-between gap-3 mb-3">
        <div className="text-start">
          <h2 className="h4 mb-1">Equipos</h2>
          <p className="text-muted">Inventario disponible para solicitudes.</p>
        </div>
      </div>

      <div className="row g-3">
        {equipos.map((equipo) => (
          <div className="col-md-6 col-xl-3" key={equipo.id}>
            <article className="card h-100 equipment-card">
              <div className="card-body">
                <div className="d-flex justify-content-between gap-2 align-items-start mb-2">
                  <span className="badge text-bg-light border">{equipo.codigoInventario}</span>
                  <span className={`badge ${estadoClase[equipo.estado] ?? 'text-bg-light'}`}>
                    {equipo.estado}
                  </span>
                </div>
                {equipo.requiereAutorizacion && (
                  <span className="badge text-bg-warning mb-2">
                    <i className="bi bi-shield-lock me-1"></i>
                    Requiere autorización para ser solicitado
                  </span>
                )}
                <h3 className="h6">{equipo.nombre}</h3>
                <p className="text-muted small mb-2">{equipo.categoria}</p>
                <p className="small mb-0">
                  <i className="bi bi-geo-alt me-1"></i>
                  {equipo.ubicacion}
                </p>
              </div>
            </article>
          </div>
        ))}
      </div>
    </section>
  )
}

export default Equipos
