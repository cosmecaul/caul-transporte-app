package cl.transporte.caul.dto;

import java.util.Date;

public class ServicioCardDTO {
    private Long id;
    private Date fecha;     // "11-12-2025"
    private String hora;      // "16:00"
    private String origen;
    private String destino;
    private String estado;
    private String cliente;
    private String vehiculo;
    private String conductor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }

    public String getConductor() { return conductor; }
    public void setConductor(String conductor) { this.conductor = conductor; }
}
