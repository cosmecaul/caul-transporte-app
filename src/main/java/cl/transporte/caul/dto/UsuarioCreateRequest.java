package cl.transporte.caul.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioCreateRequest {

    @NotBlank
    @Size(max = 100)
    private String nombreUsuario;      // username

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;          // en texto plano → se encripta en el service

    @NotBlank
    @Size(max = 200)
    private String nombreCompleto;

    @Email
    @Size(max = 150)
    private String email;

    @Size(max = 12)
    private String rut;               // opcional, formato lo puedes validar después

    @NotBlank
    private String rol;               // ADMIN, OPERADOR, CONDUCTOR, CLIENTE_CORP

    private Long idCliente;           // null si no aplica

    @NotNull
    private Boolean activo;

    // ==== getters / setters ====

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
}
