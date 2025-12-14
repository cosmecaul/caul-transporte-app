package cl.transporte.caul.service;

import cl.transporte.caul.dto.*;
import cl.transporte.caul.model.Servicio;

import java.util.List;

public interface ServicioService {
	
	

	void eliminarPasajero(Long servicioId, Long pasajeroId);

    // Crear servicio “simple”
    ServicioResponse createServicio(ServicioCreateRequest req);

    // Crear servicio + guardar pasajeros asociados
    ServicioResponse crearServicioConPasajeros(ServicioCreateRequest req);

    ServicioResponse iniciarServicio(Long id, ServicioIniciarRequest request);

    ServicioResponse finalizarServicio(Long id, ServicioFinalizarRequest request);

    ServicioResponse updateServicio(Long id, ServicioUpdateRequest req);

    ServicioResponse getServicioById(Long id);
    
    ServicioEditRequest getServicioEditById(Long id);

    List<ServicioResponse> listServicios();

    List<ServicioResponse> listServiciosPorFecha(String fechaStr);

    List<ServicioResponse> listServiciosPorCliente(Long clienteId);

    List<ServicioMovilHoyResponse> listServiciosHoyPorConductor(Long conductorId);

    List<ServicioResponse> listServiciosHoy();
    
    List<ServicioDiaViewDTO> listServiciosHoyConPasajeros();
    
 
    void updateServicioConPasajeros(Long id, ServicioEditRequest request);
    void cambiarEstado(Long id, String nuevoEstado);

	void eliminarServicio(Long id);

    // ==== Métodos para la edición vía formulario Thymeleaf ====
    Servicio buscarPorId(Long id);
    
    /**
     * Actualiza SOLO los datos básicos del servicio
     * (cliente, vehículo, conductor, ruta, fechas y horas).
     * NO toca la colección de pasajeros.
     */
    void actualizarServicio(Long id, ServicioUpdateRequest request);
    

}
