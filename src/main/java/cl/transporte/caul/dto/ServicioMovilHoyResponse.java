package cl.transporte.caul.dto;

import java.util.Date;

public class ServicioMovilHoyResponse {

    private Long id;

    private Long clienteId;
    private Long vehiculoId;
    private Long conductorId;
    private Long rutaId;

    private Date fecha;
    private String horaInicioProgramada;
    private String horaFinProgramada;

    private String origen;
    private String destino;
    private String estado;

    // --- Getters & Setters ---

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

    public void setConductorId(Long string) {
        this.conductorId = string;
    }

    public Long getRutaId() {
        return rutaId;
    }

    public void setRutaId(Long rutaId) {
        this.rutaId = rutaId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicioProgramada() {
        return horaInicioProgramada;
    }

    public void setHoraInicioProgramada(String horaInicioProgramada) {
        this.horaInicioProgramada = horaInicioProgramada;
    }

    public String getHoraFinProgramada() {
        return horaFinProgramada;
    }

    public void setHoraFinProgramada(String horaFinProgramada) {
        this.horaFinProgramada = horaFinProgramada;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
