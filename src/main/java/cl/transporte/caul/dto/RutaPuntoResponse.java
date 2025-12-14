package cl.transporte.caul.dto;

import java.util.Date;

public class RutaPuntoResponse {

    private Long id;
    private Long rutaId;
    private Integer orden;
    private String nombrePunto;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private String horaProgramada;
    private String observaciones;
    private Date createdAt;
    private Date updatedAt;

    // getters y setters
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
