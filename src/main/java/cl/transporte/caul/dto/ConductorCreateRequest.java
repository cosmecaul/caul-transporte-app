package cl.transporte.caul.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ConductorCreateRequest {

    @NotBlank
    @Size(max = 150)
    private String nombreCompleto;

    @NotBlank
    @Size(max = 20)
    private String rut;

    @Size(max = 50)
    private String telefono;

    @Email
    @Size(max = 150)
    private String email;

    @Size(max = 20)
    private String licenciaClase;

    private LocalDate licenciaVencimiento;

    private Boolean activo = Boolean.TRUE;

    @Size(max = 500)
    private String observaciones;

    // getters y setters

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
}
