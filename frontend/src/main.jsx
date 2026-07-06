import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'

// StrictMode es una herramienta de desarrollo, no renderiza nada.
/* 
StrictMode es un componente de React usado en desarrollo para detectar patrones problemáticos y 
advertir sobre posibles errores. Envuelve la aplicación y ayuda a encontrar efectos secundarios mal 
manejados, código obsoleto o problemas de limpieza en componentes. No afecta la interfaz visual y 
sus verificaciones adicionales no se aplican de la misma forma en producción.
*/
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
