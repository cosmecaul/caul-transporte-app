package cl.transporte.caul.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ruta_punto")
public class RutaPunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ruta_id", nullable = false)
    private Long rutaId;

    @Column(nullable = false)
    private Integer orden;

    @Column(name = "nombre_punto", length = 150, nullable = false)
    private String nombrePunto;

    @Column(length = 250)
    private String direccion;

    private Double latitud;
    private Double longitud;

    @Column(name = "hora_programada")
    private String horaProgramada; // formato HH:mm

    @Column(length = 500)
    private String observaciones;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    // ----- Getters y Setters -----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRutaId() { return rutaId; }
    public void setRutaId(Long rutaId) { this.rutaId = rutaId; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public String getNombrePunto() { return nombrePunto; }
    public void setNombrePunto(String nombrePunto) { this.nombrePunto = nombrePunto; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public String getHoraProgramada() { return horaProgramada; }
    public void setHoraProgramada(String horaProgramada) { this.horaProgramada = horaProgramada; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
