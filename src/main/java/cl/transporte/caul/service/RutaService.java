package cl.transporte.caul.service;

import cl.transporte.caul.dto.RutaCreateRequest;
import cl.transporte.caul.dto.RutaUpdateRequest;
import cl.transporte.caul.dto.RutaResponse;
import java.util.List;

public interface RutaService {

    RutaResponse createRuta(RutaCreateRequest request);

    RutaResponse updateRuta(Long id, RutaUpdateRequest request);

    RutaResponse getRutaById(Long id);

    List<RutaResponse> listRutas(Boolean soloActivos);

    List<RutaResponse> listRutasByCliente(Long clienteId);
}
