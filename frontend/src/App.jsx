import { useMemo, useState } from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import ProtectedRoute from './routes/ProtectedRoute.jsx'
import LayoutPrincipal from './layouts/layoutPrincipal.jsx'
import Login from './pages/Login.jsx'
import PaginaInicio from './pages/PaginaInicio.jsx'
import PaginaNoEncontrada from './pages/PaginaNoEncontrada.jsx'
import PaginaDetalleSolicitud from './pages/PaginaDetalleSolicitud.jsx'
import PaginaEditarSolicitud from './pages/PaginaEditarSolicitud.jsx'
import PaginaNuevaSolicitud from './pages/PaginaNuevaSolicitud.jsx'
import PaginaResumen from './pages/PaginaResumen.jsx'
import PaginaSolicitudes from './pages/PaginaSolicitudes.jsx'
import Registro from './pages/Registro.jsx'
import './App.css'

function App() {
  const [solicitudes, setSolicitudes] = useState([])
  const [equipos, setEquipos] = useState([])

  const context = useMemo(
    () => ({ solicitudes, setSolicitudes, equipos, setEquipos }),
    [solicitudes, equipos],
  )

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/registro" element={<Registro />} />

        <Route element={<ProtectedRoute />}>
          <Route path="/" element={<LayoutPrincipal context={context} />}>
            <Route index element={<PaginaInicio />} />
            <Route element={<ProtectedRoute allowedRoles={['admin', 'encargado']} />}>
              <Route path="resumen" element={<PaginaResumen />} />
            </Route>
            <Route path="solicitudes" element={<PaginaSolicitudes />} />
            <Route path="solicitudes/nueva" element={<PaginaNuevaSolicitud />} />
            <Route path="solicitudes/:id/editar" element={<PaginaEditarSolicitud />} />
            <Route path="solicitudes/:id" element={<PaginaDetalleSolicitud />} />
            <Route path="*" element={<PaginaNoEncontrada />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
