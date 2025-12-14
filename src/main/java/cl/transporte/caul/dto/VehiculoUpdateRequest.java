package cl.transporte.caul.dto;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class VehiculoUpdateRequest {

    @Size(max = 20)
    private String patente;

    @Size(max = 150)
    private String marcaModelo;

    private Integer anio;

    private Integer capacidad;

    private LocalDate decreto80Vencimiento;

    @Size(max = 100)
    private String gpsDeviceId;

    @Size(max = 100)
    private String gpsProveedor;

    private Boolean gpsActivo;

    @Size(max = 500)
    private String observaciones;

    @Size(max = 150)
    private String tipoVehiculo; // CAMIONETA, VAN, AUTO..
    
    // --- Getters y Setters ---
    
    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
    
        public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarcaModelo() {
        return marcaModelo;
    }

    public void setMarcaModelo(String marcaModelo) {
        this.marcaModelo = marcaModelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public LocalDate getDecreto80Vencimiento() {
        return decreto80Vencimiento;
    }

    public void setDecreto80Vencimiento(LocalDate decreto80Vencimiento) {
        this.decreto80Vencimiento = decreto80Vencimiento;
    }

    public String getGpsDeviceId() {
        return gpsDeviceId;
    }

    public void setGpsDeviceId(String gpsDeviceId) {
        this.gpsDeviceId = gpsDeviceId;
    }

    public String getGpsProveedor() {
        return gpsProveedor;
    }

    public void setGpsProveedor(String gpsProveedor) {
        this.gpsProveedor = gpsProveedor;
    }

    public Boolean getGpsActivo() {
        return gpsActivo;
    }

    public void setGpsActivo(Boolean gpsActivo) {
        this.gpsActivo = gpsActivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
