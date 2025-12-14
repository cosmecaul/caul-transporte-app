package cl.transporte.caul.service;

import java.util.List;
import cl.transporte.caul.dto.PasajeroServicioRequest;
import cl.transporte.caul.dto.PasajeroServicioResponse;

public interface PasajeroServicioService {

    List<PasajeroServicioResponse> listarPorServicio(Long servicioId);

    PasajeroServicioResponse crear(Long servicioId, PasajeroServicioRequest request);

    void eliminar(Long id);

    void eliminarPorServicio(Long servicioId);
}
