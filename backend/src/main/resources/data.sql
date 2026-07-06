INSERT OR IGNORE INTO estados (nombre, descripcion, ambito, esFinal) VALUES
('DISPONIBLE', 'Equipo disponible', 'EQUIPO', false),
('PRESTADO', 'Equipo prestado', 'EQUIPO', false),
('MANTENIMIENTO', 'Equipo en mantenimiento', 'EQUIPO', false),
('DE_BAJA', 'Equipo dado de baja', 'EQUIPO', true),
('CREADA', 'Solicitud creada', 'SOLICITUD', false),
('APROBADA', 'Solicitud aprobada', 'SOLICITUD', false),
('DEVUELTA', 'Equipo devuelto', 'SOLICITUD', true),
('RECHAZADA', 'Solicitud rechazada', 'SOLICITUD', true),
('CANCELADA', 'Solicitud cancelada', 'SOLICITUD', true);

INSERT OR IGNORE INTO roles (nombre, descripcion) VALUES
('ADMIN', 'Administrador del sistema'),
('ENCARGADO', 'Encargado de gestionar equipos y solicitudes'),
('USUARIO', 'Usuario normal del sistema');

INSERT OR IGNORE INTO usuarios (id, nombre, email, contrasenaHash, rol, activo) VALUES
(1, 'Admin Sistema', 'admin@pedidos.local', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhiX7Jw8A8L4iZJ0.MJ.YFqVRs.R7U9O', 'ADMIN', true),
(2, 'Encargado Equipos', 'encargado@pedidos.local', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhiX7Jw8A8L4iZJ0.MJ.YFqVRs.R7U9O', 'ENCARGADO', true),
(3, 'Usuario Demo', 'usuario@pedidos.local', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhiX7Jw8A8L4iZJ0.MJ.YFqVRs.R7U9O', 'USUARIO', true),
(4, 'Usuario Inactivo', 'inactivo@pedidos.local', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhiX7Jw8A8L4iZJ0.MJ.YFqVRs.R7U9O', 'USUARIO', false);

INSERT OR IGNORE INTO equipos (id, codigoInventario, nombre, categoria, estado, ubicacion, requiereAutorizacion) VALUES
(1, 'EQ-0001', 'Notebook Dell Latitude 5420', 'Notebook', 'DISPONIBLE', 'Laboratorio 1', true),
(2, 'EQ-0002', 'Proyector Epson X39', 'Proyector', 'PRESTADO', 'Aula 3', true),
(3, 'EQ-0003', 'Camara Logitech C920', 'Camara', 'MANTENIMIENTO', 'Deposito tecnico', false),
(4, 'EQ-0004', 'Router TP-Link Archer C6', 'Redes', 'DE_BAJA', 'Deposito baja', false),
(5, 'EQ-0005', 'Tablet Samsung Galaxy Tab A', 'Tablet', 'DISPONIBLE', 'Biblioteca', true);

INSERT OR IGNORE INTO solicitudes (numSolicitud, equipoId, solicitanteId, fechaRetiro, fechaDevolucion, motivo) VALUES
(1, 2, 3, '2026-07-01', '2026-07-05', 'Uso del proyector para exposicion final'),
(2, 1, 3, '2026-07-08', '2026-07-10', 'Prestamo de notebook para practica de laboratorio'),
(3, 5, 4, '2026-07-12', '2026-07-15', 'Uso de tablet para relevamiento de inventario');

INSERT OR IGNORE INTO historial_solicitudes (
	id,
	solicitudId,
	fechaHoraInicio,
	fechaHoraFin,
	responsableId,
	estadoAnterior,
	estado
) VALUES
(1, 1, '2026-07-01 09:00:00', '2026-07-01 10:15:00', 3, NULL, 'CREADA'),
(2, 1, '2026-07-01 10:15:00', NULL, 2, 'CREADA', 'APROBADA'),
(3, 2, '2026-07-02 14:30:00', NULL, 3, NULL, 'CREADA'),
(4, 3, '2026-07-03 11:00:00', '2026-07-03 12:00:00', 4, NULL, 'CREADA'),
(5, 3, '2026-07-03 12:00:00', NULL, 2, 'CREADA', 'RECHAZADA');
