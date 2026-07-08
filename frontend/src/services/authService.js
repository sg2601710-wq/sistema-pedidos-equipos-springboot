import api from '../api/httpClient'

function normalizarRol(rol) {
  return typeof rol === 'string' ? rol.toLowerCase() : rol
}

function normalizarUsuario(usuario) {
  if (!usuario) {
    return usuario
  }

  return {
    ...usuario,
    rol: normalizarRol(usuario.rol),
  }
}

export async function registrarUsuario(datos) {
  const payload = {
    nombre: datos.nombre,
    email: datos.email,
    contrasena: datos.contrasena ?? datos.password,
    rol: datos.rol ?? 'USUARIO',
    activo: datos.activo ?? true,
  }

  const response = await api.post('/usuarios', payload)
  return normalizarUsuario(response.data.data)
}

export async function iniciarSesion(datos) {
  const payload = {
    email: datos.email ?? datos.nombre,
    contrasena: datos.contrasena ?? datos.password,
  }

  const response = await api.post('/usuarios/sesion', payload)
  const data = response.data.data

  return {
    ...data,
    usuario: normalizarUsuario(data.usuario ?? data.user),
  }
}
