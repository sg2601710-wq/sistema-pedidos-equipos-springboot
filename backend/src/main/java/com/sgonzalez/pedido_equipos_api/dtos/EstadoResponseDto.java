package com.sgonzalez.pedido_equipos_api.dtos;

public class EstadoResponseDto {

    private String nombre;
    private String descripcion;
    private String ambito;
    private Boolean esFinal;

    public EstadoResponseDto() {}

    public EstadoResponseDto(
            String nombre,
            String descripcion,
            String ambito,
            Boolean esFinal) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ambito = ambito;
        this.esFinal = esFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public Boolean getEsFinal() {
        return esFinal;
    }

    public void setEsFinal(Boolean esFinal) {
        this.esFinal = esFinal;
    }

}
