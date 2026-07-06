import { Link } from 'react-router-dom'

function PaginaNoEncontrada() {
  return (
    <section className="text-center py-5">
      <span className="badge text-bg-secondary mb-3">404</span>
      <h2 className="h3">Pagina no encontrada</h2>
      <p className="text-muted mb-4">La ruta solicitada no existe en la aplicacion.</p>
      <Link to="/" className="btn btn-primary">
        Volver al inicio
      </Link>
    </section>
  )
}

export default PaginaNoEncontrada
