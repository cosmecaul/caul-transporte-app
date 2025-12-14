package cl.transporte.caul.dto;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


public class ServicioHistoricoItemDTO {

    private Date fecha;
    private String hora;
    private String estado;
    private String origen;
    private String destino;

    public ServicioHistoricoItemDTO(Date date,
                                    String hora,
                                    String estado,
                                    String origen,
                                    String destino) {
        this.setFecha(date);
        this.hora = hora;
        this.estado = estado;
        this.origen = origen;
        this.destino = destino;
    }

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
