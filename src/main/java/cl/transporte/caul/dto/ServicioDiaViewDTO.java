package cl.transporte.caul.dto;

import java.util.Date;
import java.util.List;

public class ServicioDiaViewDTO {

    private Long id;

    private Date fecha;
    private String HoraInicio;
    private String HoraFin;

    private String origen;
    private String destino;
    private String estado;

    private Long clienteId;
    private Long vehiculoId;
    private Long conductorId;

    private String clienteNombre;
    private String conductorNombre;
    private String patente;     // Ej: "ABCD11 — Hyundai Staria"
    private String tipoVehiculo;

    private Integer cantidadPasajeros;
    private List<PasajeroServicioViewDTO> pasajeros;

    // ========== GETTERS & SETTERS ==========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getHoraInicio() { return HoraInicio; }
    public void setHoraInicio(String HoraInicio) { this.HoraInicio = HoraInicio; }

    public String getHoraFin() { return HoraFin; }
    public void setFin(String HoraFin) { this.HoraFin = HoraFin; }
   
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Long vehiculoId) { this.vehiculoId = vehiculoId; }

    public Long getConductorId() { return conductorId; }
    public void setConductorId(Long conductorId) { this.conductorId = conductorId; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getConductorNombre() { return conductorNombre; }
    public void setConductorNombre(String conductorNombre) { this.conductorNombre = conductorNombre; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente =patente; }

    public Integer getCantidadPasajeros() { return cantidadPasajeros; }
    public void setCantidadPasajeros(Integer cantidadPasajeros) { this.cantidadPasajeros = cantidadPasajeros; }

    public List<PasajeroServicioViewDTO> getPasajeros() { return pasajeros; }
    public void setPasajeros(List<PasajeroServicioViewDTO> pasajeros) { this.pasajeros = pasajeros; }
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
}
