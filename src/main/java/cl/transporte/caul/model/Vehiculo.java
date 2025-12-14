package cl.transporte.caul.model;

import jakarta.persistence.*;
import java.util.Date;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("unused")
@Entity
@Table(name = "vehiculo")   // <-- Usa el nombre real de la tabla
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patente", nullable = false, length = 20, unique = true)
    private String patente;

    @Column(name = "marca_modelo", length = 150)
    private String marcaModelo;

    private Integer anio;
    private Integer capacidad;

   
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "decreto80_vencimiento")
    private LocalDate decreto80Vencimiento;
   
        
    @Column(name = "gps_device_id", length = 100)
    private String gpsDeviceId;

    @Column(name = "gps_proveedor", length = 100)
    private String gpsProveedor;

    private Boolean gpsActivo;

    @Column(length = 500)
    private String observaciones;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "tipo_vehiculo", length = 150)
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
