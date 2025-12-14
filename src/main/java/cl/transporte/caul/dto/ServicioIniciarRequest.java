package cl.transporte.caul.dto;

import java.util.Date;

public class ServicioIniciarRequest {

    
    private Date fechaHoraInicioReal;   // opcional, si es null usamos now
   
    private String observacionOperacion;

  

    public Date getFechaHoraInicioReal() {
        return fechaHoraInicioReal;
    }

    public void setFechaHoraInicioReal(Date fechaHoraInicioReal) {
        this.fechaHoraInicioReal = fechaHoraInicioReal;
    }

    public String getObservacionOperacion() {
        return observacionOperacion;
    }

    public void setObservacionOperacion(String observacionOperacion) {
        this.observacionOperacion = observacionOperacion;
    }
}
