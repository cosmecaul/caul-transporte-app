package cl.transporte.caul.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicioCreateRequest {

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El vehículo es obligatorio")
    private Long vehiculoId;

    @NotNull(message = "El conductor es obligatorio")
    private Long conductorId;

    // Puede ser opcional si no siempre usas rutas predefinidas
    private Long rutaId;

    @NotNull(message = "La fecha es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    @NotNull(message = "La hora de inicio programada es obligatoria")
    @Size(max = 10, message = "La hora de inicio programada es muy larga")
    private String horaInicioProgramada;   // HH:mm

    @NotNull(message = "La hora de término programada es obligatoria")
    @Size(max = 10, message = "La hora de término programada es muy larga")
    private String horaFinProgramada;      // HH:mm

    @Size(max = 20, message = "El turno es muy largo")
    private String turno;                  // Ej: "AM", "PM", "NOCHE"

    @NotNull(message = "El origen es obligatorio")
    @Size(min = 2, max = 200, message = "El origen debe tener entre 2 y 200 caracteres")
    private String origen;

    @NotNull(message = "El destino es obligatorio")
    @Size(min = 2, max = 200, message = "El destino debe tener entre 2 y 200 caracteres")
    private String destino;

    // km al inicio del servicio (opcional al crear, obligatorio al iniciar)
    private Integer kmInicio;

    @Size(max = 50, message = "El código público es muy largo")
    private String codigoPublico;

    private Double velocidadMaxKmh;

    @Size(max = 1000, message = "Las observaciones son muy largas")
    private String observaciones;

    // cantidad de pasajeros (se setea en base a la lista)
    private Integer cantidadPasajeros;

    // 🔥 lista de pasajeros del servicio
    private List<PasajeroServicioRequest> pasajeros = new ArrayList<>();

    // ===== GETTERS & SETTERS =====

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

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public Integer getKmInicio() { return kmInicio; }
    public void setKmInicio(Integer kmInicio) { this.kmInicio = kmInicio; }

    public String getCodigoPublico() { return codigoPublico; }
    public void setCodigoPublico(String codigoPublico) { this.codigoPublico = codigoPublico; }

    public Double getVelocidadMaxKmh() { return velocidadMaxKmh; }
    public void setVelocidadMaxKmh(Double velocidadMaxKmh) { this.velocidadMaxKmh = velocidadMaxKmh; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Integer getCantidadPasajeros() { return cantidadPasajeros; }
    public void setCantidadPasajeros(Integer cantidadPasajeros) { this.cantidadPasajeros = cantidadPasajeros; }

            
    public List<PasajeroServicioRequest> getPasajeros() { return pasajeros; }
        public void setPasajeros(List<PasajeroServicioRequest> pasajeros) { this.pasajeros = pasajeros; }
    
    
    
    
}
