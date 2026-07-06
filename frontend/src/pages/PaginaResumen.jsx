import { useEffect, useState } from 'react'
import PanelResumen from '../components/PanelResumen.jsx'
import { obtenerResumenSolicitudes } from '../services/solicitudesService.js'
import { formatearFecha } from '../utils/fechas.js'

function PaginaResumen() {
  const [resumen, setResumen] = useState(null)
  const [cargando, setCargando] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    let activo = true

    async function cargarResumen() {
      setCargando(true)
      setError('')

      try {
        const data = await obtenerResumenSolicitudes()

        if (activo) {
          setResumen(data)
        }
      } catch (err) {
        if (activo) {
          setResumen(null)
          setError(err.response?.data?.message || 'No se pudo cargar el resumen administrativo.')
        }
      } finally {
        if (activo) {
          setCargando(false)
        }
      }
    }

    cargarResumen()

    return () => {
      activo = false
    }
  }, [])

  return (
    <>
      <div className="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-3">
        <div className="text-start">
          <span className="badge text-bg-primary mb-2">Administración</span>
          <h2 className="h3 mb-1">Resumen administrativo</h2>
          <p className="text-muted">
            Indicadores generales de disponibilidad, pendientes y préstamos vencidos.
          </p>
        </div>
        {resumen?.fechaReferencia && (
          <span className="badge text-bg-light border">
            Referencia: {formatearFecha(resumen.fechaReferencia)}
          </span>
        )}
      </div>

      {cargando && <div className="alert alert-info">Cargando resumen...</div>}
      {error && <div className="alert alert-warning">{error}</div>}
      {!cargando && !error && <PanelResumen resumenAdministrativo={resumen} />}
    </>
  )
}

export default PaginaResumen
