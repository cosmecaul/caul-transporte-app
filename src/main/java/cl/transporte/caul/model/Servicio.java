package cl.transporte.caul.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    private Long clienteId;

    @Column(name = "id_vehiculo", nullable = false)
    private Long vehiculoId;

    @Column(name = "id_conductor", nullable = false)
    private Long conductorId;

    @Column(name = "id_ruta", nullable = false)
    private Long rutaId;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "hora_inicio_programada")
    private String horaInicioProgramada; // TIME → String HH:mm:ss

    @Column(name = "hora_fin_programada")
    private String horaFinProgramada;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_hora_inicio_real")
    private Date fechaHoraInicioReal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_hora_fin_real")
    private Date fechaHoraFinReal;

    @Column(name = "km_inicio")
    private Integer kmInicio;

    @Column(name = "km_fin")
    private Integer kmFin;

    private String turno;

    private String origen;

    private String destino;

    private String estado;

    @Column(name = "codigo_publico")
    private String codigoPublico;

    @Column(name = "velocidad_max_kmh")
    private Double velocidadMaxKmh;

    private String observaciones;

    @Column(name = "observacion_operacion")
    private String observacionOperacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasajeroServicio > pasajeros = new ArrayList<>();
    
    // -------- GETTERS y SETTERS --------

    // (te los dejo completos pero sin comentar para evitar mucho texto)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Long vehiculoId) { this.vehiculoId = vehiculoId; }

    public Long getConductorId() { return conductorId; }
    public void setConductorId(Long conductorId) { this.conductorId = conductorId; }

    public Long getRutaId() { return rutaId; }
    public void setRutaId(Long rutaId) { this.rutaId = rutaId; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getHoraInicioProgramada() { return horaInicioProgramada; }
    public void setHoraInicioProgramada(String horaInicioProgramada) { this.horaInicioProgramada = horaInicioProgramada; }

    public String getHoraFinProgramada() { return horaFinProgramada; }
    public void setHoraFinProgramada(String horaFinProgramada) { this.horaFinProgramada = horaFinProgramada; }

    public Date getFechaHoraInicioReal() { return fechaHoraInicioReal; }
    public void setFechaHoraInicioReal(Date fechaHoraInicioReal) { this.fechaHoraInicioReal = fechaHoraInicioReal; }

    public Date getFechaHoraFinReal() { return fechaHoraFinReal; }
    public void setFechaHoraFinReal(Date fechaHoraFinReal) { this.fechaHoraFinReal = fechaHoraFinReal; }

    public Integer getKmInicio() { return kmInicio; }
    public void setKmInicio(Integer kmInicio) { this.kmInicio = kmInicio; }

    public Integer getKmFin() { return kmFin; }
    public void setKmFin(Integer kmFin) { this.kmFin = kmFin; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoPublico() { return codigoPublico; }
    public void setCodigoPublico(String codigoPublico) { this.codigoPublico = codigoPublico; }

    public Double getVelocidadMaxKmh() { return velocidadMaxKmh; }
    public void setVelocidadMaxKmh(Double velocidadMaxKmh) { this.velocidadMaxKmh = velocidadMaxKmh; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getObservacionOperacion() { return observacionOperacion; }
    public void setObservacionOperacion(String observacionOperacion) { this.observacionOperacion = observacionOperacion; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    
    
    public List<PasajeroServicio > getPasajeros() {
        return pasajeros;
    }
}
