package cl.transporte.caul.dto;

import java.time.LocalDate;
import java.util.Date;

import jakarta.validation.constraints.Size;

public class VehiculoResponse {

    private Long id;
    private String patente;
    private String marcaModelo;
    private Integer anio;
    private Integer capacidad;
    private LocalDate decreto80Vencimiento;
    private String gpsDeviceId;
    private String gpsProveedor;
    private Boolean gpsActivo;
    private String observaciones;
    private Date createdAt;
    private Date updatedAt;
    private String tipoVehiculo; // CAMIONETA, VAN, AUTO..
    
    // --- Getters y Setters ---
    
    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }  
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
