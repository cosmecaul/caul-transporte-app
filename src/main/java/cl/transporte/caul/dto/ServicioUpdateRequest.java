package cl.transporte.caul.dto;

import java.util.Date;

import cl.transporte.caul.model.Servicio;

public class ServicioUpdateRequest {
	
	
	
    private Long id;
    private Long clienteId;
    private Long vehiculoId;
    private Long conductorId;
    private Long rutaId;
	private Servicio servicio;

    private Date fecha;
    private String horaInicioProgramada;
    private String horaFinProgramada;

    private Date fechaHoraInicioReal;
    private Date fechaHoraFinReal;

    private Integer kmInicio;
    private Integer kmFin;

    private String turno;

    private String origen;
    private String destino;

    private String estado;

    private String codigoPublico;
    private Double velocidadMaxKmh;

    private String observaciones;
    private String observacionOperacion;

    // GETTERS & SETTERS

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
	
    public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public Long getVehiculoId() {
		return vehiculoId;
	}
	public void setVehiculoId(Long vehiculoId) {
		this.vehiculoId = vehiculoId;
	}
	public Long getConductorId() {
		return conductorId;
	}
	public void setConductorId(Long conductorId) {
		this.conductorId = conductorId;
	}
	public Long getRutaId() {
		return rutaId;
	}
	public void setRutaId(Long rutaId) {
		this.rutaId = rutaId;
	}
}
