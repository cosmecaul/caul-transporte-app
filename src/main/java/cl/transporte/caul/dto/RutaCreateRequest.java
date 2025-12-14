package cl.transporte.caul.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RutaCreateRequest {

    @NotNull
    private Long clienteId;

    @NotBlank
    @Size(max = 150)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    private Double kmEstimados;

    private Boolean activo = Boolean.TRUE;

    @Size(max = 500)
    private String observaciones;

    // getters y setters
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
}
