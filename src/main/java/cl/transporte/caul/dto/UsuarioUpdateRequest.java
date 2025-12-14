package cl.transporte.caul.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateRequest {

    @Size(max = 200)
    private String nombreCompleto;

    @Email
    @Size(max = 150)
    private String email;

    @Size(max = 12)
    private String rut;

    private String rol;

    private Long idCliente;

    private Boolean activo;

    // Si viene passwordNuevo, se vuelve a encriptar
    @Size(min = 6, max = 255)
    private String passwordNuevo;

    // ==== getters / setters ====

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getPasswordNuevo() {
        return passwordNuevo;
    }

    public void setPasswordNuevo(String passwordNuevo) {
        this.passwordNuevo = passwordNuevo;
    }
}
