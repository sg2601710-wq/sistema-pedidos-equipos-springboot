export function formatearFecha(valor) {
  const fechaISO = valor?.slice(0, 10)

  if (!fechaISO) {
    return '-'
  }

  const [anio, mes, dia] = fechaISO.split('-')

  if (!anio || !mes || !dia) {
    return valor
  }

  return `${dia}/${mes}/${anio}`
}

export function formatearFechaHora(valor) {
  if (!valor) {
    return '-'
  }

  const fecha = formatearFecha(valor)
  const hora = valor.slice(11, 16)

  return hora ? `${fecha} ${hora}` : fecha
}


export function obtenerFechaLocalHoy() {
  //Exporta fecha YYYY-MM-DD
  const fechaActual = new Date();
  const anio = fechaActual.getFullYear();
  const mes = String(fechaActual.getMonth() + 1).padStart(2, '0'); 
  const dia = String(fechaActual.getDate()).padStart(2, '0');
  
  return `${anio}-${mes}-${dia}`;
}