package cl.transporte.caul.dto;

import java.io.Serializable;

public class PasajeroServicioRequest implements Serializable {

    private String nombrePasajero;
    private String rutPasajero;
    private String puntoSubida;
    private String puntoBajada;
    private String observaciones;
    private String telefonoContacto;

    // ===== GETTERS & SETTERS =====

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

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}
}
