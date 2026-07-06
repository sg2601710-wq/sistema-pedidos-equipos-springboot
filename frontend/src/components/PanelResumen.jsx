const resumenConfig = [
  { estado: 'total', titulo: 'Total', clase: 'text-primary', icono: 'bi-collection' },
  { estado: 'aprobada', titulo: 'Aprobadas', clase: 'text-success', icono: 'bi-check-circle' },
  { estado: 'pendiente', titulo: 'Pendientes', clase: 'text-warning', icono: 'bi-hourglass-split' },
  { estado: 'cancelada', titulo: 'Canceladas', clase: 'text-secondary', icono: 'bi-x-circle' },
  { estado: 'rechazada', titulo: 'Rechazadas', clase: 'text-danger', icono: 'bi-slash-circle' },
  { estado: 'devuelta', titulo: 'Devueltas', clase: 'text-info', icono: 'bi-arrow-return-left' },
]

const resumenAdministrativoConfig = [
  {
    clave: 'solicitudesPendientes',
    titulo: 'Pendientes de aprobación',
    descripcion: 'Solicitudes que todavía esperan revisión de un encargado o administrador.',
    clase: 'text-warning',
    icono: 'bi-hourglass-split',
  },
  {
    clave: 'equiposPrestados',
    titulo: 'Equipos prestados',
    descripcion: 'Equipos que actualmente figuran como prestados en el inventario.',
    clase: 'text-primary',
    icono: 'bi-pc-display-horizontal',
  },
  {
    clave: 'prestamosVencidos',
    titulo: 'Préstamos vencidos',
    descripcion: 'Solicitudes aprobadas cuya fecha de devolución ya pasó.',
    clase: 'text-danger',
    icono: 'bi-exclamation-triangle',
  },
]

function PanelResumen({ solicitudes = [], resumenAdministrativo = null }) {
  const contar = (estado) => {
    if (estado === 'total') {
      return solicitudes.length
    }

    return solicitudes.filter((solicitud) => solicitud.estado === estado).length
  }

  // Esto es lo que se devuelve en la parte de resumen, resumen administrativo
  if (resumenAdministrativo) {
    return (
      <>
        <section className="row g-3 mb-4" aria-label="Resumen administrativo">
          {resumenAdministrativoConfig.map((item) => (
            <div className="col-6 col-lg-4" key={item.clave}>
              <article className="card summary-card h-100">
                <div className="card-body">
                  <div className="d-flex align-items-start justify-content-between gap-3">
                    <div className="summary-icon">
                      <i className={`bi ${item.icono}`}></i>
                    </div>
                    <h3 className={`mb-0 ${item.clase}`}>{resumenAdministrativo[item.clave] ?? 0}</h3>
                  </div>
                  <p className="text-muted mb-1">{item.titulo}</p>
                  <p className="small text-muted mb-0">{item.descripcion}</p>
                </div>
              </article>
            </div>
          ))}
        </section>

        <section className="card summary-card mb-4">
          <div className="card-body">
            <div className="d-flex flex-wrap align-items-start justify-content-between gap-2 mb-3">
              <div>
                <h3 className="h5 mb-1">Equipos disponibles por categoría</h3>
                <p className="text-muted mb-0">
                  Cantidad de equipos en estado disponible agrupados por tipo de equipamiento.
                </p>
              </div>
              <span className="badge text-bg-light border">
                Total disponible:{' '}
                {resumenAdministrativo.equiposDisponiblesPorCategoria?.reduce(
                  (total, item) => total + item.cantidad,
                  0,
                ) ?? 0}
              </span>
            </div>

            {resumenAdministrativo.equiposDisponiblesPorCategoria?.length > 0 ? (
              <div className="row g-2">
                {resumenAdministrativo.equiposDisponiblesPorCategoria.map((item) => (
                  <div className="col-sm-6 col-lg-4" key={item.categoria}>
                    <div className="summary-category">
                      <span className="text-capitalize">{item.categoria}</span>
                      <strong>{item.cantidad}</strong>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="alert alert-light border mb-0">
                No hay equipos disponibles en este momento.
              </div>
            )}
          </div>
        </section>
      </>
    )
  }

  // Esto es lo que se devuelve en el inicio, resumen de solicitudes
  return (
    <section className="row g-3 mb-4" aria-label="Resumen de solicitudes">
      {resumenConfig.map((item) => (
        <div className="col-6 col-lg-2" key={item.estado}>
          <article className="card summary-card h-100">
            <div className="card-body">
              <div className="summary-icon">
                <i className={`bi ${item.icono}`}></i>
              </div>
              <p className="text-muted mb-1">{item.titulo}</p>
              <h3 className={`mb-0 ${item.clase}`}>{contar(item.estado)}</h3>
            </div>
          </article>
        </div>
      ))}
    </section>
  )
}

export default PanelResumen
