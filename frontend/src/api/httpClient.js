import axios from 'axios'

export const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:3000/api'

/* Esto crea un cliente HTTP llamado api.

Cliente HTTP: programa o aplicación que inicia una solicitud a un servidor web mediante el protocolo HTTP

*/

const api = axios.create({
  // Esto nos define que cada vez que hagamos una petición http, la url sea siempre http://localhost:3000/api...
  baseURL: API_BASE_URL,
  // Esto nos indica que por defecto las request van a enviar datos en formato JSON
  headers: {
    'Content-Type': 'application/json',
  },
})

let redirigiendoPorSesionInvalida = false

// Config vendría a ser un objeto que representa la configuración de una petición Axios antes de enviarse
// Esto se ejecuta antes de que el backend responda a una request
api.interceptors.request.use((config) => {
  // Esto busca el token guardado en el navegador
  const token = localStorage.getItem('token')

  if (token) {
    // Si el token existe, esto agrega el token al header de la petición
    config.headers.Authorization = `Bearer ${token}`
  }

  return config
})

// Esto se ejecuta después de que el backend responda a una request
// Si se ejecuta con éxito, nos devuelve la respuesta de la solicitud que hicimos, sino, nos devuelve un error
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const codigo = error.response?.data?.error?.code

    if (
      codigo === 'INVALID_TOKEN' &&
      !redirigiendoPorSesionInvalida &&
      window.location.pathname !== '/login'
    ) {
      redirigiendoPorSesionInvalida = true
      localStorage.removeItem('token')
      localStorage.removeItem('usuario')
      window.location.assign('/login')
    }

    return Promise.reject(error)
  },
)

export default api
