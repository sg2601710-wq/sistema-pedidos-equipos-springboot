import { useEffect, useState } from 'react'
import { Outlet } from 'react-router-dom'
import { listarEquipos } from '../services/equiposService.js'
import { listarSolicitudes } from '../services/solicitudesService.js'
import Encabezado from '../components/Encabezado.jsx'
import PiePagina from '../components/PiePagina.jsx'

function LayoutPrincipal({ context }) {
  const [erroresCarga, setErroresCarga] = useState([])
  const [cargandoDatos, setCargandoDatos] = useState(true)
  const { setEquipos, setSolicitudes } = context

  useEffect(() => {
    let activo = true

    async function cargarDatosIniciales() {
      setCargandoDatos(true)
      const errores = []

      try {
        const equipos = await listarEquipos()
        if (activo) {
          setEquipos(equipos)
        }
      } catch (err) {
        errores.push(err.response?.data?.message || 'No se pudieron cargar los equipos desde el backend.')
      }

      try {
        const { data: solicitudes } = await listarSolicitudes()
        if (activo) {
          setSolicitudes(solicitudes)
        }
      } catch (err) {
        errores.push(
          err.response?.data?.message || 'No se pudieron cargar las solicitudes desde el backend.',
        )
      }

      if (activo) {
        setErroresCarga(errores)
        setCargandoDatos(false)
      }
    }

    cargarDatosIniciales()

    return () => {
      activo = false
    }
    // ponemos [setEquipos, setSolicitudes] para indicar que cada vez que cambie algo en el layout esos 2
    // se vuelvan a ejecutar. En este caso no es necesario ponerlo.
  }, [setEquipos, setSolicitudes])

  return (
    <div className="app-shell">
      <Encabezado />
      <main className="container pb-4">
        {cargandoDatos && <div className="alert alert-info">Cargando datos desde el backend...</div>}
        {erroresCarga.map((error) => (
          <div className="alert alert-warning" key={error}>
            {error}
          </div>
        ))}
        <Outlet context={context} />
      </main>
      <PiePagina />
    </div>
  )
}

export default LayoutPrincipal
