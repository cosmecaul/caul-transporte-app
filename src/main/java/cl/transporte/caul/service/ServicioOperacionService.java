package cl.transporte.caul.service;



public interface ServicioOperacionService {

    void iniciarServicio(Long servicioId, Long conductorId);

    void finalizarServicio(Long servicioId, Long conductorId);
}
