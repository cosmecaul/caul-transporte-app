package cl.transporte.caul.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class PasajeroServicioEditRequest implements Serializable {

    /**
     * ID del registro pasajero_servicio en la BD.
     * Obligatorio para actualizar un pasajero existente.
     */
    @NotNull(message = "El identificador del pasajero es obligatorio para la edición")
    private Long id;

    /**
     * ID del servicio al que pertenece este pasajero.
     */
    @NotNull(message = "El servicio asociado es obligatorio")
    private Long servicioId;

    @NotNull(message = "El Telefono asociado es obligatorio")
    private String telefonoContacto;
   
    
    @NotNull(message = "El nombre del pasajero es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre del pasajero debe tener entre 2 y 200 caracteres")
    private String nombrePasajero;

    @Size(max = 20, message = "El RUT del pasajero no debe exceder 20 caracteres")
    private String rutPasajero;

    @Size(max = 200, message = "El punto de subida no debe exceder 200 caracteres")
    private String puntoSubida;

    @Size(max = 200, message = "El punto de bajada no debe exceder 200 caracteres")
    private String puntoBajada;

    @Size(max = 500, message = "Las observaciones no deben exceder 500 caracteres")
    private String observaciones;

    /**
     * Flag para marcar este pasajero para eliminación desde el formulario
     * (ej: checkbox "eliminar" en la fila del pasajero).
     */
    private boolean marcadoParaEliminar = Boolean.FALSE;
    private Boolean eliminar = Boolean.FALSE;

    // =========================
    //      GETTERS & SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }
    
    
    public String getRutPasajero() {
        return rutPasajero;
    }

    public void setRutPasajero(String rutPasajero) {
        this.rutPasajero = rutPasajero;
    }

    public String getPuntoSubida() {
        return puntoSubida;
    }

    public void setPuntoSubida(String puntoSubida) {
        this.puntoSubida = puntoSubida;
    }

    public String getPuntoBajada() {
        return puntoBajada;
    }

    public void setPuntoBajada(String puntoBajada) {
        this.puntoBajada = puntoBajada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isMarcadoParaEliminar() {
        return marcadoParaEliminar;
    }

    public void setMarcadoParaEliminar(boolean marcadoParaEliminar) {
        this.marcadoParaEliminar = marcadoParaEliminar;
    }
    
    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }
    
    
}

