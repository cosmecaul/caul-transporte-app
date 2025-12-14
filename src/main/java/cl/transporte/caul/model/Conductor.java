package cl.transporte.caul.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "conductor")   // nombre real de la tabla en tu BD
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", length = 150, nullable = false)
    private String nombreCompleto;

    @Column(name = "rut", length = 20, nullable = false, unique = true)
    private String rut;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "licencia_clase", length = 20)
    private String licenciaClase;

    @Column(name = "licencia_vencimiento")
    private LocalDate licenciaVencimiento;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Conductor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

