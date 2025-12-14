package cl.transporte.caul.dto;

import java.util.Date;

public class ServicioFinalizarRequest {

    private Integer kmFin;
    private Date fechaHoraFinReal;   // opcional, si es null usamos now
    private String observacionOperacion;

    public Integer getKmFin() {
        return kmFin;
    }

    public void setKmFin(Integer kmFin) {
        this.kmFin = kmFin;
    }

    public Date getFechaHoraFinReal() {
        return fechaHoraFinReal;
    }

    public void setFechaHoraFinReal(Date fechaHoraFinReal) {
        this.fechaHoraFinReal = fechaHoraFinReal;
    }

    public String getObservacionOperacion() {
        return observacionOperacion;
    }

    public void setObservacionOperacion(String observacionOperacion) {
        this.observacionOperacion = observacionOperacion;
    }
}
