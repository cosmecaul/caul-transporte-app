package cl.transporte.caul.dto;

import java.util.Date;

public class RutaResponse {

    private Long id;
    private Long clienteId;
    private String nombre;
    private String descripcion;
    private Double kmEstimados;
    private Boolean activo;
    private String observaciones;
    private Date createdAt;
    private Date updatedAt;

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getKmEstimados() { return kmEstimados; }
    public void setKmEstimados(Double kmEstimados) { this.kmEstimados = kmEstimados; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
