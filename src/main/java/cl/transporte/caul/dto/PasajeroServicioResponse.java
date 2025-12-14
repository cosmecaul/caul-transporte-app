package cl.transporte.caul.dto;

import java.io.Serializable;
import java.util.Date;

public class PasajeroServicioResponse implements Serializable {

    private Long id;
    private Long servicioId;

    private String nombrePasajero;
    private String telefonoContacto;
    private String rutPasajero;
    private String puntoSubida;
    private String puntoBajada;
    private String observaciones;

    private Date createdAt;
    private Date updatedAt;

    // ===== GETTERS & SETTERS =====

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

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}
}
