package cl.transporte.caul.dto;

import jakarta.validation.constraints.Size;

public class RutaPuntoUpdateRequest {

    private Integer orden;

    @Size(max = 150)
    private String nombrePunto;

    @Size(max = 250)
    private String direccion;

    private Double latitud;
    private Double longitud;

    private String horaProgramada;

    @Size(max = 500)
    private String observaciones;

    // getters y setters
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
}
