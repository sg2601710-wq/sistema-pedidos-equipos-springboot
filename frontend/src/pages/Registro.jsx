import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { registrarUsuario } from '../services/authService.js'

function Registro() {
  const [form, setForm] = useState({ nombre: '', email: '', password: '' })
  const [error, setError] = useState('')
  const [erroresCampo, setErroresCampo] = useState({})
  const [mensaje, setMensaje] = useState('')
  const [cargando, setCargando] = useState(false)
  const navigate = useNavigate()

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
    setErroresCampo((erroresActuales) => ({ ...erroresActuales, [e.target.name]: '' }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setErroresCampo({})
    setMensaje('')

    const email = form.email.trim()
    const errores = {}

    if (!form.nombre.trim()) {
      errores.nombre = 'El nombre de usuario es obligatorio.'
    }else if (form.nombre.trim().length >= 20) {
      errores.nombre = `El nombre de usuario no puede superar los 20 caracteres.`
    }

    if (!email) {
      errores.email = 'El email es obligatorio.'
    } else if (form.email.trim().length >= 40) {
      errores.email = `El email no puede superar los 40 caracteres.`
    } else if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      errores.email = 'El email debe tener un formato válido.'
    }
    
    if (!form.password) {
      errores.password = 'La contraseña es obligatoria.'
    }else if (form.password.length < 6) {
      errores.password = 'La contraseña debe tener al menos 6 caracteres.'
    }else if (form.password.length > 30) {
      errores.password = 'La contraseña debe tener menos de 30 caracteres.'
    }

    // Acá vamos agregando los errores que encontramos a erroresCampos
    if (Object.keys(errores).length > 0) {
      setErroresCampo(errores)
      return
    }

    setCargando(true)

    try {
      await registrarUsuario({
        nombre: form.nombre.trim(),
        email,
        password: form.password,
      })
      setMensaje('Usuario registrado correctamente. Ya podes iniciar sesion.')
      // No se redirige automáticamente a login porque se debe mostrar una notificación de éxito
      setTimeout(() => navigate('/login'), 900)
    } catch (err) {
      const respuestaError = err.response?.data
      const detalles = respuestaError?.error?.details
      const codigo = respuestaError?.error?.code

      if (Array.isArray(detalles)) {
        const erroresBackend = detalles.reduce((acumulador, detalle) => {
          acumulador[detalle.field] = detalle.message
          return acumulador
        }, {})

        setErroresCampo(erroresBackend)
      } else if (codigo === 'USERNAME_ALREADY_EXISTS') {
        setErroresCampo({ nombre: 'El nombre de usuario ya existe.' })
      }

      const detalle = respuestaError?.message || 'No se pudo registrar el usuario.'
      setError(detalle)
    } finally {
      setCargando(false)
    }
  }

  return (
    <main className="auth-page">
      <section className="auth-card">
        <div className="text-center mb-4">
          <span className="badge text-bg-success mb-2">Nuevo usuario</span>
          <h1 className="h3 mb-1">Registro</h1>
          <p className="text-muted">Crea una cuenta para solicitar equipos.</p>
        </div>

        {error && <div className="alert alert-danger py-2">{error}</div>}
        {mensaje && <div className="alert alert-success py-2">{mensaje}</div>}

        <form onSubmit={handleSubmit} className="d-grid gap-3" noValidate>
          <div>
            <label className="form-label">Nombre de usuario</label>
            <input
              className={`form-control ${erroresCampo.nombre ? 'is-invalid' : ''}`}
              name="nombre"
              type="text"
              placeholder="nuevo.usuario"
              value={form.nombre}
              onChange={handleChange}
            />
            {erroresCampo.nombre && <div className="invalid-feedback">{erroresCampo.nombre}</div>}
          </div>

          <div>
            <label className="form-label">Email</label>
            <input
              className={`form-control ${erroresCampo.email ? 'is-invalid' : ''}`}
              name="email"
              type="email"
              placeholder="nuevo@dds.com"
              value={form.email}
              onChange={handleChange}
            />
            {erroresCampo.email && <div className="invalid-feedback">{erroresCampo.email}</div>}
          </div>

          <div>
            <label className="form-label">Contraseña</label>
            <input
              className={`form-control ${erroresCampo.password ? 'is-invalid' : ''}`}
              name="password"
              type="password"
              placeholder="********"
              value={form.password}
              onChange={handleChange}
            />
            {erroresCampo.password && (
              <div className="invalid-feedback">{erroresCampo.password}</div>
            )}
          </div>

          <button type="submit" className="btn btn-success w-100" disabled={cargando}>
            {cargando ? 'Registrando...' : 'Crear cuenta'}
          </button>
        </form>

        <p className="text-center mt-3 mb-0">
          ¿Ya tenés cuenta? <Link to="/login">Inicia sesión</Link>
        </p>
      </section>
    </main>
  )
}

export default Registro
