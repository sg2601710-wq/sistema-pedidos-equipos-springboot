import { useEffect, useMemo, useState } from 'react'
import { Link, useLocation, useOutletContext, useSearchParams } from 'react-router-dom'
import { cambiarEstadoSolicitud, listarSolicitudes } from '../services/solicitudesService.js'
import ListaSolicitudes from '../components/ListaSolicitudes.jsx'
import {obtenerFechaLocalHoy} from '../utils/fechas.js'

const MAX_BUSQUEDA_CARACTERES = 150
const hoy = obtenerFechaLocalHoy()
const añoMaximo = new Date();
añoMaximo.setFullYear(añoMaximo.getFullYear() + 2);
const maxFecha = añoMaximo.toISOString().split('T')[0];

const normalizarSolicitud = (solicitud) => ({
  ...solicitud,
  fechaRetiro: solicitud.fechaRetiro?.slice(0, 10),
  fechaDevolucion: solicitud.fechaDevolucion?.slice(0, 10),
})

const mensajesConfirmacion = {
  aprobar: '¿Confirmás que querés aprobar esta solicitud?',
  rechazar: '¿Confirmás que querés rechazar esta solicitud?',
  cancelar: '¿Confirmás que querés cancelar esta solicitud?',
  devolver: '¿Confirmás que querés marcar esta solicitud como devuelta?',
}

function limitarBusqueda(valor) {
  return valor.slice(0, MAX_BUSQUEDA_CARACTERES)
}

function PaginaSolicitudes() {
  const { solicitudes, setSolicitudes, equipos } = useOutletContext()
  // este es un hook que nos sirve para leer y modificar las query params de la URL
  const [searchParams, setSearchParams] = useSearchParams()
  const location = useLocation()
  const [estado, setEstado] = useState(() => searchParams.get('estado') || 'todas')
  const [equipoId, setEquipoId] = useState(() => searchParams.get('equipoId') || 'todos')
  const [categoria, setCategoria] = useState(() => searchParams.get('categoria') || 'todas')
  const [fechaDesde, setFechaDesde] = useState(() => searchParams.get('desde') || '')
  const [fechaHasta, setFechaHasta] = useState(() => searchParams.get('hasta') || '')
  const [busqueda, setBusqueda] = useState(() => limitarBusqueda(searchParams.get('buscar') || ''))
  const [page, setPage] = useState(() => Number(searchParams.get('page')) || 1)
  const [limit, setLimit] = useState(() => Number(searchParams.get('limit')) || 5)
  const [sortBy, setSortBy] = useState(() => searchParams.get('sortBy') || 'fechaRetiro')
  const [order, setOrder] = useState(() => searchParams.get('order') || 'asc')
  const [metaListado, setMetaListado] = useState({
    total: 0,
    page: 1,
    limit: 5,
    totalPages: 0,
    sortBy: 'fechaRetiro',
    order: 'asc',
  })
  const [errorAccion, setErrorAccion] = useState('')
  const [errorListado, setErrorListado] = useState('')
  const [mensajeExitoOculto, setMensajeExitoOculto] = useState(false)
  const [cargandoListado, setCargandoListado] = useState(false)
  const mensajeExito = mensajeExitoOculto ? '' : location.state?.mensajeExito

  // useMemo se usa para calcular los valores de un resultado y almacenarlo, entonces no se debe calcular
  // de vuelta cada vez que se renderice el componente/página. Este solo se va a calcular de vuelta si 
  // hay un cambio en uno de sus atributos o dependencias
  const categorias = useMemo(() => {
    return [...new Set(equipos.map((equipo) => equipo.categoria))].sort()
  }, [equipos])

  useEffect(() => {
    const params = {}

    if (estado !== 'todas') params.estado = estado
    if (equipoId !== 'todos') params.equipoId = equipoId
    if (categoria !== 'todas') params.categoria = categoria
    if (fechaDesde) params.desde = fechaDesde
    if (fechaHasta) params.hasta = fechaHasta
    if (busqueda.trim()) params.buscar = busqueda.trim()
    if (page > 1) params.page = String(page)
    if (limit !== 5) params.limit = String(limit)
    if (sortBy !== 'fechaRetiro') params.sortBy = sortBy
    if (order !== 'asc') params.order = order

    setSearchParams(params, { replace: true })
  }, [estado, equipoId, categoria, fechaDesde, fechaHasta, busqueda, page, limit, sortBy, order, setSearchParams])

  useEffect(() => {
    let activo = true

    async function cargarSolicitudesFiltradas() {
      setCargandoListado(true)
      setErrorListado('')

      try {
        const resultado = await listarSolicitudes({
          estado,
          equipoId,
          categoria,
          buscar: busqueda,
          desde: fechaDesde,
          hasta: fechaHasta,
          page,
          limit,
          sortBy,
          order,
        })

        if (activo) {
          setSolicitudes(resultado.data.map(normalizarSolicitud))
          setMetaListado(resultado.meta)
        }
      } catch (err) {
        if (activo) {
          setErrorListado(err.response?.data?.message || 'No se pudieron cargar las solicitudes.')
        }
      } finally {
        if (activo) {
          setCargandoListado(false)
        }
      }
    }

    cargarSolicitudesFiltradas()

    return () => {
      activo = false
    }
  }, [estado, equipoId, categoria, fechaDesde, fechaHasta, busqueda, page, limit, sortBy, order, setSolicitudes])

  // Permite cambiar los valores de los filtros y volver a la primera página. Es una función auxiliar
  const actualizarFiltro = (setter, value) => {
    setter(value)
    setPage(1)
  }

  const actualizarBusqueda = (value) => {
    actualizarFiltro(setBusqueda, limitarBusqueda(value))
  }

  // Esto permite que se actualice en tiempo real un cambio de estado de una solicitud
  const reemplazarSolicitud = (solicitudActualizada) => {
    const actualizadaNormalizada = normalizarSolicitud(solicitudActualizada)
    const actualizadas = solicitudes.map((solicitud) =>
      solicitud.id === actualizadaNormalizada.id ? actualizadaNormalizada : solicitud,
    )

    setSolicitudes(actualizadas)
  }

  const handleCambiarEstado = async (id, nuevoEstado) => {
    const accion = {
      aprobada: 'aprobar',
      rechazada: 'rechazar',
      cancelada: 'cancelar',
      devuelta: 'devolver',
    }[nuevoEstado]

    if (!accion) {
      setErrorAccion('Accion no valida para la solicitud.')
      return
    }

    setErrorAccion('')

    const confirmado = window.confirm(
      `${mensajesConfirmacion[accion] ?? '¿Confirmás el cambio de estado?'}\nSolicitud #${id}`,
    )

    if (!confirmado) {
      return
    }

    try {
      const solicitudActualizada = await cambiarEstadoSolicitud(id, accion)
      reemplazarSolicitud(solicitudActualizada)
    } catch (err) {
      setErrorAccion(err.response?.data?.message || 'No se pudo cambiar el estado de la solicitud.')
    }
  }

  return (
    <>
      <div className="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-3">
        <div className="text-start">
          <span className="badge text-bg-primary mb-2">Solicitudes</span>
          <h2 className="h3 mb-1">Listado de solicitudes</h2>
          <p className="text-muted">Filtra por estado, revisa pedidos y cambia su estado.</p>
        </div>
        <Link to="/solicitudes/nueva" className="btn btn-primary">
          <i className="bi bi-plus-circle me-2"></i>
          Nueva solicitud
        </Link>
      </div>

      <section className="card filter-card mb-3">
        <div className="card-body">
          <div className="row g-3">
            <div className="col-md-4">
              <label className="form-label">Estado</label>
              <select
                className="form-select"
                value={estado}
                onChange={(e) => actualizarFiltro(setEstado, e.target.value)}
              >
                <option value="todas">Todas</option>
                <option value="pendiente">Pendientes</option>
                <option value="aprobada">Aprobadas</option>
                <option value="rechazada">Rechazadas</option>
                <option value="cancelada">Canceladas</option>
                <option value="devuelta">Devueltas</option>
              </select>
            </div>

            <div className="col-md-4">
              <label className="form-label">Equipo</label>
              <select
                className="form-select"
                value={equipoId}
                onChange={(e) => actualizarFiltro(setEquipoId, e.target.value)}
              >
                <option value="todos">Todos</option>
                {equipos.map((equipo) => (
                  <option value={equipo.id} key={equipo.id}>
                    {equipo.nombre}
                  </option>
                ))}
              </select>
            </div>

            <div className="col-md-4">
              <label className="form-label">Categoria</label>
              <select
                className="form-select"
                value={categoria}
                onChange={(e) => actualizarFiltro(setCategoria, e.target.value)}
              >
                <option value="todas">Todas</option>
                {categorias.map((item) => (
                  <option value={item} key={item}>
                    {item}
                  </option>
                ))}
              </select>
            </div>

            <div className="col-md-3">
              <label className="form-label">Desde</label>
              <input
                className="form-control"
                type="date"
                value={fechaDesde}
                min={hoy}
                max={maxFecha}
                onChange={(e) => actualizarFiltro(setFechaDesde, e.target.value)}
              />
            </div>

            <div className="col-md-3">
              <label className="form-label">Hasta</label>
              <input
                className="form-control"
                type="date"
                value={fechaHasta}
                min={hoy}
                max={maxFecha}
                onChange={(e) => actualizarFiltro(setFechaHasta, e.target.value)}
              />
            </div>

            <div className="col-md-6">
              <label className="form-label">Buscar</label>
              <input
                className="form-control"
                type="search"
                placeholder="Buscar por equipo o motivo"
                value={busqueda}
                maxLength={MAX_BUSQUEDA_CARACTERES}
                onChange={(e) => actualizarBusqueda(e.target.value)}
              />
            </div>

            <div className="col-md-4">
              <label className="form-label">Ordenar por</label>
              <div className="input-group">
                <select
                  className="form-select"
                  style={{ flex: '2' }}
                  value={sortBy}
                  onChange={(e) => actualizarFiltro(setSortBy, e.target.value)}
                >
                  <option value="fechaRetiro">Fecha de retiro</option>
                  <option value="fechaDevolucion">Fecha de devolución</option>
                  <option value="id">Número de solicitud</option>
                  <option value="estado">Estado</option>
                  <option value="motivo">Motivo</option>
                  <option value="equipoNombre">Equipo</option>
                  <option value="categoria">Categoría</option>
                </select>
                <select
                  className="form-select"
                  value={order}
                  onChange={(e) => actualizarFiltro(setOrder, e.target.value)}
                >
                  <option value="asc">Asc ↑</option>
                  <option value="desc">Desc ↓</option>
                </select>
              </div>
            </div>

            <div className="col-md-2">
              <label className="form-label">Por pagina</label>
              <select
                className="form-select"
                value={limit}
                onChange={(e) => actualizarFiltro(setLimit, Number(e.target.value))}
              >
                <option value={5}>5</option>
                <option value={10}>10</option>
                <option value={20}>20</option>
              </select>
            </div>

            <div className="col-md-3 d-flex align-items-end">
              <button
                className="btn btn-outline-secondary w-100"
                type="button"
                onClick={() => {
                  setEstado('todas')
                  setEquipoId('todos')
                  setCategoria('todas')
                  setFechaDesde('')
                  setFechaHasta('')
                  setBusqueda('')
                  setPage(1)
                  setLimit(5)
                  setSortBy('fechaRetiro')
                  setOrder('asc')
                }}
              >
                Limpiar filtros
              </button>
            </div>
          </div>
        </div>
      </section>

      {cargandoListado && <div className="alert alert-info">Cargando solicitudes...</div>}
      {mensajeExito && (
        <div className="alert alert-success alert-dismissible fade show" role="alert">
          {mensajeExito}
          <button
            type="button"
            className="btn-close"
            aria-label="Cerrar"
            onClick={() => setMensajeExitoOculto(true)}
          ></button>
        </div>
      )}
      {errorListado && <div className="alert alert-warning">{errorListado}</div>}
      {errorAccion && <div className="alert alert-danger">{errorAccion}</div>}

      <ListaSolicitudes
        solicitudes={solicitudes}
        equipos={equipos}
        onCambiarEstado={handleCambiarEstado}
      />

      <div className="d-flex flex-wrap align-items-center justify-content-between gap-3 mt-3">
        <p className="text-muted mb-0">
          Mostrando {solicitudes.length} de {metaListado.total} solicitudes
        </p>
        <div className="btn-group" role="group" aria-label="Paginacion de solicitudes">
          <button
            className="btn btn-outline-secondary"
            type="button"
            disabled={page <= 1 || cargandoListado}
            onClick={() => setPage((actual) => Math.max(actual - 1, 1))}
          >
            Anterior
          </button>
          <span className="btn btn-outline-secondary disabled">
            Pagina {metaListado.page} de {Math.max(metaListado.totalPages, 1)}
          </span>
          <button
            className="btn btn-outline-secondary"
            type="button"
            disabled={page >= metaListado.totalPages || cargandoListado}
            onClick={() => setPage((actual) => actual + 1)}
          >
            Siguiente
          </button>
        </div>
      </div>
    </>
  )
}

export default PaginaSolicitudes
