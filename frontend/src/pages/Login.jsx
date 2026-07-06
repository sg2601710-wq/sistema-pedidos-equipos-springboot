import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { iniciarSesion } from '../services/authService.js'

function Login() {
  const [usuario, setUsuario] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [cargando, setCargando] = useState(false)
  const navigate = useNavigate()

  const guardarSesion = (usuario, token) => {
    localStorage.setItem('token', token)
    localStorage.setItem('usuario', JSON.stringify(usuario))
    navigate('/')
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    // Se vuelve a definir el error como "''" porque puede que ocurra otro error antes de llegar acá
    setError('')

    if (!usuario.trim() || !password) {
      setError('Por favor, completa todos los campos.')
      return
    }

    setCargando(true)

    try {
      const data = await iniciarSesion({ nombre: usuario.trim(), password })
      guardarSesion(data.usuario ?? data.user ?? data, data.token)
    } catch (err) {
      setError(err.response?.data?.message || 'No se pudo iniciar sesion.')
    } finally {
      setCargando(false)
    }
  }

  return (
    <main className="auth-page">
      <section className="auth-card">
        <div className="text-center mb-4">
          <span className="badge text-bg-primary mb-2">Solicitudes de equipos</span>
          <h1 className="h3 mb-1">Iniciar sesión</h1>
          <p className="text-muted">Ingresa con tu cuenta para gestionar préstamos.</p>
        </div>

        {error && <div className="alert alert-danger py-2">{error}</div>}

        <form onSubmit={handleSubmit} className="d-grid gap-3">
          <div>
            <label className="form-label">Usuario</label>
            <input
              className="form-control"
              type="text"
              placeholder="Nombre de Usuario"
              value={usuario}
              onChange={(e) => setUsuario(e.target.value)}
            />
          </div>

          <div>
            <label className="form-label">Contraseña</label>
            <input
              className="form-control"
              type="password"
              placeholder=""
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <button type="submit" className="btn btn-primary w-100" disabled={cargando}>
            {cargando ? 'Ingresando...' : 'Entrar'}
          </button>
        </form>

        <p className="text-center mt-3 mb-0">
          ¿No tenés cuenta? <Link to="/registro">Registrate</Link>
        </p>
      </section>
    </main>
  )
}

export default Login
