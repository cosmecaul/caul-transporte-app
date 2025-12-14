package cl.transporte.caul.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConductorResponse {

    private Long id;
    private String nombreCompleto;
    private String rut;
    private String telefono;
    private String email;
    private String licenciaClase;
    private LocalDate licenciaVencimiento;
    private Boolean activo;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicenciaClase() {
        return licenciaClase;
    }

    public void setLicenciaClase(String licenciaClase) {
        this.licenciaClase = licenciaClase;
    }

    public LocalDate getLicenciaVencimiento() {
        return licenciaVencimiento;
    }

    public void setLicenciaVencimiento(LocalDate licenciaVencimiento) {
        this.licenciaVencimiento = licenciaVencimiento;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
