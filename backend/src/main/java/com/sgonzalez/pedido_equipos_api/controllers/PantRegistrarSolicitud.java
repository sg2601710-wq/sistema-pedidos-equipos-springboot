package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.CrearSolicitudRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorRegistrarSolicitud;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(origins = "*")
public class PantRegistrarSolicitud {

    private final GestorRegistrarSolicitud gestorRegistrarSolicitud;

    public PantRegistrarSolicitud(GestorRegistrarSolicitud gestorRegistrarSolicitud) {
        this.gestorRegistrarSolicitud = gestorRegistrarSolicitud;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> registrarSolicitud(@Valid @RequestBody CrearSolicitudRequestDto request) {
        SolicitudResponseDto solicitud = gestorRegistrarSolicitud.registrarSolicitud(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Solicitud registrada correctamente", solicitud));
    }


}
